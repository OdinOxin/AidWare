package de.odinoxin.aidware.aidcloud.plugins;

import de.odinoxin.aidware.aidcloud.*;
import de.odinoxin.aidware.aidcloud.structures.Tuple;
import org.hibernate.Session;
import org.hibernate.annotations.FetchProfile;
import org.hibernate.annotations.FetchProfiles;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import javax.ws.rs.*;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public abstract class RecordHandler<T extends Recordable> extends Provider {

    public RecordHandler() {
        generateDefaults();
    }

    @GET
    @Path("{id}")
    public T get(@PathParam("id") int id) {
        T entity;
        try (Session session = DB.open()) {
            this.setFetchMode(session);
            entity = session.get(getParameterizedType(), id);
        }
        return entity;
    }

    protected final int persist(T entity, T original, int userId) throws ConcurrentFault {
        if (entity == null)
            throw new IllegalArgumentException("Entity cannot be null!");
        if (entity.getId() != 0) {
            if (original == null)
                throw new IllegalArgumentException("Original entity cannot be null on update!");
            if (entity.getId() != original.getId())
                throw new IllegalArgumentException("Entity is different from original entity!");
            T current = this.get(original.getId());
            if (current == null)
                throw new ConcurrentFault("Entity does not exist (anymore)!");
            if (!original.equals(current))
                throw new ConcurrentFault("Entity was edited in the meantime!");
        }
        int id = entity.getId();
        boolean isNew = entity.getId() == 0;
        try (Session session = DB.open()) {
            session.beginTransaction();
            if (isNew)
                id = (int) session.save(entity);
            else
                session.update(entity);
            session.getTransaction().commit();
        }

        if (!(entity.getClass() == TrackedChange.class)) {
            Field[] fields = entity.getClass().getDeclaredFields();
            Method[] methods = entity.getClass().getMethods();
            for (Field f : fields) {
                if (f.isAnnotationPresent(EntityProperty.class)) {
                    Method getter = null;
                    for (Method m : methods)
                        if (m.getName().equalsIgnoreCase(String.format("get%s", f.getName()))) {
                            getter = m;
                            break;
                        }
                    if (getter != null)
                        try {
                            String valueBefore = null;
                            if (!isNew)
                                valueBefore = makeString(getter.invoke(original));
                            String valueAfter = makeString(getter.invoke(entity));
                            if (!(valueAfter == null && valueBefore == null) && (valueAfter != null && !valueAfter.equals(valueBefore)))
                                TrackedChangeProvider.getInstance().generate(new TrackedChange(0, getParameterizedType().getSimpleName(), entity.getId(), f.getName(), new Date(), userId, valueBefore, valueAfter));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                }
            }
        }

        return id;
    }

    private String makeString(Object obj) {
        if (obj == null)
            return null;
        if (obj instanceof Recordable)
            return String.valueOf(((Recordable) obj).getId());
        else
            return obj.toString();
    }

    @PUT
    public T update(Tuple<T, T> set) throws ConcurrentFault {
        return this.get(persist(set.x, set.y, 0));
    }

    @POST
    public T insert(T entity) throws ConcurrentFault {
        return this.get(persist(entity, null, 0));
    }

    protected void generate(T entity) {
        try {
            persist(entity, null, 0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @DELETE
    @Path("{id}")
    public boolean delete(@PathParam("id") int id) {
        T entity = this.get(id);
        if (entity != null)
            try (Session session = DB.open()) {
                session.beginTransaction();
                session.delete(entity);
                session.getTransaction().commit();
            } catch (Exception ex) {
                return false;
            }
        return true;
    }

    @GET
    public List<T> search(@QueryParam("expr") List<String> expressions, @DefaultValue("0") @QueryParam("max") int max, @QueryParam("exceptedIds") List<Integer> exceptIds) {
        Session session = DB.open();
        CriteriaBuilder builder = session.getEntityManagerFactory().getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(getParameterizedType());
        Predicate predicates = builder.conjunction();
        Root<T> root = criteria.from(getParameterizedType());
        Expression<Integer> dbIdExpression = getIdExpression(root);
        if (expressions != null && expressions.size() > 0) {
            Predicate exprPredicates = builder.disjunction();
            List<Expression<String>> dbExpressions = getSearchExpressions(root);
            for (String expr : expressions) {
                if (dbIdExpression != null && expr.matches("-?\\d+"))
                    exprPredicates = builder.or(exprPredicates, builder.equal(dbIdExpression, Integer.parseInt(expr)));
                if (dbExpressions != null)
                    for (Expression<String> dbExpr : dbExpressions) {
                        exprPredicates = builder.or(exprPredicates, builder.like(builder.lower(dbExpr), "%" + expr.toLowerCase() + "%"));
                    }
            }
            predicates.getExpressions().add(exprPredicates);
        }
        if (exceptIds != null && exceptIds.size() > 0) {
            Predicate exceptedIdsPredicates = builder.conjunction();
            for (int exceptedId : exceptIds)
                exceptedIdsPredicates = builder.and(exceptedIdsPredicates, builder.equal(dbIdExpression, exceptedId).not());
            predicates.getExpressions().add(exceptedIdsPredicates);
        }
        criteria.where(predicates);
        EntityManager em = session.getEntityManagerFactory().createEntityManager();
        List<T> tmpList = em.createQuery(criteria).setMaxResults(Math.max(0, max)).getResultList();
        List<T> result = new ArrayList<>();
        for (T tmp : tmpList)
            result.add((T) tmp.clone());
        em.close();
        session.close();
        return result;
    }

    protected Expression<Integer> getIdExpression(Root<T> root) {
        return null;
    }

    protected List<Expression<String>> getSearchExpressions(Root<T> root) {
        return null;
    }

    private void setFetchMode(Session session) {
        Class<T> clazz = getParameterizedType();
        if (session == null || clazz == null)
            return;
        FetchProfiles annotation = clazz.getAnnotation(FetchProfiles.class);
        if (annotation != null) {
            for (FetchProfile profile : annotation.value()) {
                session.enableFetchProfile(profile.name());
            }
        }
    }

    protected long countRecords() {
        try (Session session = DB.open()) {
            CriteriaBuilder builder = session.getEntityManagerFactory().getCriteriaBuilder();
            CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
            criteria.select(builder.count(criteria.from(getParameterizedType())));
            return session.getEntityManagerFactory().createEntityManager().createQuery(criteria).getResultList().get(0);
        }
    }

    private Class<T> getParameterizedType() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
}