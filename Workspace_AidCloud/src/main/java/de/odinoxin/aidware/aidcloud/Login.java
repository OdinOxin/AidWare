package de.odinoxin.aidware.aidcloud;

import de.odinoxin.aidware.aidcloud.plugins.person.Person;
import de.odinoxin.aidware.aidcloud.plugins.person.Person_;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("Login")
public class Login {

    @GET
    @Path("{id}")
    public boolean checkLogin(@PathParam("id") int id, @QueryParam("pwd") String pwd) {
        boolean access = false;
        try (Session session = DB.open()) {
            CriteriaBuilder builder = session.getEntityManagerFactory().getCriteriaBuilder();
            CriteriaQuery<Person> criteria = builder.createQuery(Person.class);
            Predicate predicates = builder.conjunction();
            Root<Person> root = criteria.from(Person.class);
            predicates = builder.and(predicates, builder.equal(root.get(Person_.id), id));
            Predicate pwdPredicates = builder.disjunction();
            pwdPredicates = builder.or(pwdPredicates, builder.equal(root.get(Person_.pwd), pwd));
            if (pwd == null || pwd.isEmpty())
                pwdPredicates = builder.or(pwdPredicates, builder.isNull(root.get(Person_.pwd)));
            predicates = builder.and(predicates, pwdPredicates);
            criteria.where(predicates);
            List<Person> tmpList = session.getEntityManagerFactory().createEntityManager().createQuery(criteria).getResultList();
            if (tmpList != null && tmpList.size() == 1)
                access = true;
        }
        return access;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Person> searchLogin(@QueryParam("expr") List<String> expr, @QueryParam("max") int max) {
        List<Person> result = new ArrayList<>();
        try (Session session = DB.open()) {
            CriteriaBuilder builder = session.getEntityManagerFactory().getCriteriaBuilder();
            CriteriaQuery<Person> criteria = builder.createQuery(Person.class);
            Predicate predicates = builder.conjunction();
            Root<Person> root = criteria.from(Person.class);
            if (expr != null && expr.size() > 0) {
                for (int i = 0; i < expr.size(); i++)
                    expr.set(i, "%" + expr.get(i).toLowerCase() + "%");
                predicates = builder.disjunction();
                for (int i = 0; i < expr.size(); i++) {
                    predicates = builder.or(predicates, builder.like(builder.lower(root.get(Person_.forename)), expr.get(i)));
                    predicates = builder.or(predicates, builder.like(builder.lower(root.get(Person_.name)), expr.get(i)));
                    predicates = builder.or(predicates, builder.like(builder.lower(root.get(Person_.code)), expr.get(i)));
                }
            }
            criteria.where(predicates);
            List<Person> tmpList = session.getEntityManagerFactory().createEntityManager().createQuery(criteria).setMaxResults(Math.max(0, max) + 1).getResultList();
            if (tmpList != null)
                for (Person tmp : tmpList)
                    result.add(new Person(tmp.getId(), tmp.getName(), tmp.getForename(), tmp.getCode(), null, null, null, null, null));
        }
        return result;
    }
}
