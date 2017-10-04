package de.odinoxin.aidware.aidcloud.plugins;

import de.odinoxin.aidware.aidcloud.*;
import org.hibernate.Session;
import org.hibernate.annotations.FetchProfile;
import org.hibernate.annotations.FetchProfiles;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import javax.ws.rs.NotAuthorizedException;
import javax.xml.ws.WebServiceContext;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class RecordHandler<T extends Recordable> extends Provider {

    public RecordHandler() {
        generateDefaults();
    }

    protected T get(int id) {
        T entity;
        try (Session session = DB.open()) {
            this.setFetchMode(session, getParameterizedTypeClass());
            entity = session.get(getParameterizedTypeClass(), id);
        }
        return entity;
    }

    protected T get(int id, WebServiceContext wsCtx) {
        if (!Login.checkSession(wsCtx))
            throw new NotAuthorizedException(AidCloud.INVALID_SESSION);
        return get(id);
    }

    protected int save(T entity, T original, int userId) throws ConcurrentFault {
        if (entity != null && entity.getId() != 0) {
            if (original == null)
                throw new IllegalArgumentException("Original entity cannot be null on update!");
            if (entity.getId() != original.getId())
                throw new IllegalArgumentException("Entity is different from original entity!");
            T current = this.get(original.getId());
            if (!original.equals(current))
                throw new ConcurrentFault("Entity was edited in the meantime!");
        }
        if (entity == null)
            throw new IllegalArgumentException("Entity cannot be null!");
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
                                TrackedChangeProvider.getInstance().save(new TrackedChange(0, getParameterizedTypeClass().getSimpleName(), entity.getId(), f.getName(), new Date(), userId, valueBefore, valueAfter), null);
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

    protected int save(T entity, T original) throws ConcurrentFault {
        return save(entity, original, 0);
    }

    protected int save(T entity, T original, WebServiceContext wsCtx) throws ConcurrentFault {
        if (!Login.checkSession(wsCtx))
            throw new NotAuthorizedException(AidCloud.INVALID_SESSION);
        return save(entity, original, Login.getUserIdFromContext(wsCtx));
    }

    protected void generate(T entity) {
        try {
            save(entity, null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    protected boolean delete(int id, WebServiceContext wsCtx) {
        if (!Login.checkSession(wsCtx))
            throw new NotAuthorizedException(AidCloud.INVALID_SESSION);
        T entity = this.get(id, wsCtx);
        try (Session session = DB.open()) {
            session.beginTransaction();
            session.delete(entity);
            session.getTransaction().commit();
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    protected List<T> search(String[] expressions, int max, int[] exceptIds) {
        Session session = DB.open();
        CriteriaBuilder builder = session.getEntityManagerFactory().getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(getParameterizedTypeClass());
        Predicate predicates = builder.conjunction();
        Root<T> root = criteria.from(getParameterizedTypeClass());
        Expression<Integer> dbIdExpression = getIdExpression(root);
        if (expressions != null && expressions.length > 0) {
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
        if (exceptIds != null && exceptIds.length > 0) {
            Predicate exceptedIdsPredicates = builder.conjunction();
            for (int i = 0; i < exceptIds.length; i++) {
                exceptedIdsPredicates = builder.and(exceptedIdsPredicates, builder.equal(dbIdExpression, exceptIds[i]).not());
            }
            predicates.getExpressions().add(exceptedIdsPredicates);
        }
        criteria.where(predicates);
        EntityManager em = session.getEntityManagerFactory().createEntityManager();
        List<T> tmpList = em.createQuery(criteria).setMaxResults(Math.max(0, max) + 1).getResultList();
        List<T> result = new ArrayList<>();
        for (T tmp : tmpList)
            result.add((T) tmp.clone());
        em.close();
        session.close();
        return result;
    }

    protected List<T> search(String[] expressions, int max, int[] exceptIds, WebServiceContext wsCtx) {
        if (!Login.checkSession(wsCtx))
            throw new NotAuthorizedException(AidCloud.INVALID_SESSION);
        return search(expressions, max, exceptIds);
    }

    protected Expression<Integer> getIdExpression(Root<T> root) {
        return null;
    }

    protected List<Expression<String>> getSearchExpressions(Root<T> root) {
        return null;
    }

    private void setFetchMode(Session session, Class<T> clazz) {
        if (session == null || clazz == null)
            return;
        FetchProfiles annotation = (FetchProfiles) clazz.getAnnotation(FetchProfiles.class);
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
            criteria.select(builder.count(criteria.from(getParameterizedTypeClass())));
            return session.getEntityManagerFactory().createEntityManager().createQuery(criteria).getResultList().get(0);
        }
    }

    private Class<T> getParameterizedTypeClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
}