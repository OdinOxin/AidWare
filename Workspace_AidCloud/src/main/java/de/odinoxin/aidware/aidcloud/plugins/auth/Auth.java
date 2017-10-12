package de.odinoxin.aidware.aidcloud.plugins.auth;

import de.odinoxin.aidware.aidcloud.DB;
import de.odinoxin.aidware.aidcloud.plugins.person.Person;
import de.odinoxin.aidware.aidcloud.plugins.person.Person_;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.*;

@Path("Auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class Auth {

    private static Map<String, Date> knownTokens = new Hashtable<>();
    private static final long TOKEN_EXPIRATION = 5 * 60 * 1000; // 5min // TODO: Make this configurable.

    @GET
    @Path("CheckConnection")
    public Response checkConnection() {
        return Response.ok().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Person> search(@QueryParam("expr") List<String> expr, @DefaultValue("0") @QueryParam("max") int max) {
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
            List<Person> tmpList = session.getEntityManagerFactory().createEntityManager().createQuery(criteria).setMaxResults(Math.max(0, max)).getResultList();
            if (tmpList != null)
                for (Person tmp : tmpList)
                    result.add(new Person(tmp.getId(), tmp.getName(), tmp.getForename(), tmp.getCode(), null, null, null, null, null));
        }
        return result;
    }

    @POST
    @Path("{id}")
    public Response authenticate(@PathParam("id") int id, Credentials credentials) {
        try (Session session = DB.open()) {
            CriteriaBuilder builder = session.getEntityManagerFactory().getCriteriaBuilder();
            CriteriaQuery<Person> criteria = builder.createQuery(Person.class);
            Predicate predicates = builder.conjunction();
            Root<Person> root = criteria.from(Person.class);
            predicates = builder.and(predicates, builder.equal(root.get(Person_.id), id));
            predicates = builder.and(predicates, builder.equal(root.get(Person_.pwd), credentials.getPwd()));
            criteria.where(predicates);
            List<Person> tmpList = session.getEntityManagerFactory().createEntityManager().createQuery(criteria).getResultList();
            if (tmpList != null && tmpList.size() == 1)
                return Response.ok(newToken()).build();
        }
        throw new ForbiddenException();
    }

    private static String newToken() {
        Date expire = new Date();
        expire.setTime(expire.getTime() + TOKEN_EXPIRATION);
        Random random = new SecureRandom();
        String token = new BigInteger(130, random).toString(32);
        Auth.knownTokens.put(token, expire);
        return token;
    }

    static boolean isValidToken(String token) {
        return Auth.knownTokens.containsKey(token) && Auth.knownTokens.get(token).after(new Date());
    }
}
