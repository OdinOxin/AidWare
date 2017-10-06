package de.odinoxin.aidware.aidcloud.plugins.person;

import de.odinoxin.aidware.aidcloud.structures.ConcurrentFault;
import de.odinoxin.aidware.aidcloud.DB;
import de.odinoxin.aidware.aidcloud.recordable.RecordHandler;
import de.odinoxin.aidware.aidcloud.plugins.address.Address;
import de.odinoxin.aidware.aidcloud.plugins.address.Address_;
import de.odinoxin.aidware.aidcloud.plugins.country.Country;
import de.odinoxin.aidware.aidcloud.plugins.country.Country_;
import de.odinoxin.aidware.aidcloud.structures.Tuple;
import org.hibernate.Session;

import javax.persistence.Query;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import javax.ws.rs.*;
import java.util.ArrayList;
import java.util.List;

@Path("Person")
public class PersonProvider extends RecordHandler<Person> {

    @GET
    @Path("{id}")
    @Override
    public Person get(@PathParam("id") int id) {
        Person p = super.get(id);
        if (p != null)
            p.setPwd(null);
        return p;
    }

    @PUT
    @Override
    public Person update(Tuple<Person, Person> set) throws ConcurrentFault {
        if (set == null || set.x == null)
            throw new IllegalArgumentException("Entity cannot be null!");
        Person current = super.get(set.x.getId()); // Get WITH pwd
        if (current != null)
            set.x.setPwd(current.getPwd());
        Person p = super.update(set);
        if (p != null)
            p.setPwd(null);
        return p;
    }

    @PUT
    @Path("{id}/{currentPwd}/{newPwd}")
    public boolean changePwd(@PathParam("id") int id, @PathParam("currentPwd") String currentPwd, @PathParam("newPwd") String newPwd) {
        if (id == 0)
            return false;
        Session session = DB.open();
        session.beginTransaction();
        Query q = session.createQuery("UPDATE Person SET Pwd = :NewPwd WHERE ID = :ID AND (Pwd LIKE :CurrentPwd" + (currentPwd == null || currentPwd.isEmpty() ? " OR Pwd IS NULL" : "") + ")");
        q.setParameter("ID", id);
        q.setParameter("CurrentPwd", currentPwd);
        q.setParameter("NewPwd", newPwd);
        boolean success = q.executeUpdate() == 1;
        session.getTransaction().commit();
        session.close();
        return success;
    }

    @Override
    protected Expression<Integer> getIdExpression(Root<Person> root) {
        return root.get(Person_.id);
    }

    @Override
    protected List<Expression<String>> getSearchExpressions(Root<Person> root) {
        List<Expression<String>> expressions = new ArrayList<>();
        Join<Person, Address> joinAddress = root.join(Person_.address, JoinType.LEFT);
        Join<Address, Country> joinCountry = joinAddress.join(Address_.country, JoinType.LEFT);
        expressions.add(root.get(Person_.forename));
        expressions.add(root.get(Person_.name));
        expressions.add(root.get(Person_.code));
        expressions.add(joinAddress.get(Address_.street));
        expressions.add(joinAddress.get(Address_.hsNo));
        expressions.add(joinAddress.get(Address_.zip));
        expressions.add(joinCountry.get(Country_.name));
        return expressions;
    }

    @Override
    public void generateDefaults() {
        if (this.countRecords() <= 0) {
            Person admin = new Person();
            admin.setForename("AidDesk");
            admin.setName("Admin");
            admin.setPwd("AidDesk");
            this.generate(admin);
        }
    }
}
