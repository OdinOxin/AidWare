package de.odinoxin.aidcloud.plugins.people;

import de.odinoxin.aidcloud.ConcurrentFault;
import de.odinoxin.aidcloud.DB;
import de.odinoxin.aidcloud.plugins.RecordHandler;
import de.odinoxin.aidcloud.plugins.addresses.Address;
import de.odinoxin.aidcloud.plugins.addresses.Address_;
import de.odinoxin.aidcloud.plugins.countries.Country;
import de.odinoxin.aidcloud.plugins.countries.Country_;
import org.hibernate.Session;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.persistence.Query;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import javax.xml.ws.WebServiceContext;
import java.util.ArrayList;
import java.util.List;

@WebService
public class PersonProvider extends RecordHandler<Person> {

    @Resource
    WebServiceContext wsCtx;

    @WebMethod
    public Person getPerson(@WebParam(name = "id") int id) {
        Person p = super.get(id, this.wsCtx);
        if (p != null)
            p.setPwd(null);
        return p;
    }

    @WebMethod
    public Person savePerson(@WebParam(name = "entity") Person entity, @WebParam(name = "original") Person original) throws ConcurrentFault {
        Person current = this.get(entity.getId(), this.wsCtx);
        if (current != null)
            entity.setPwd(current.getPwd());
        return this.getPerson(super.save(entity, original, this.wsCtx));
    }

    @WebMethod
    public boolean deletePerson(@WebParam(name = "id") int id) {
        return super.delete(id, this.wsCtx);
    }

    @WebMethod
    public List<Person> searchPerson(@WebParam(name = "expr") String[] expr, @WebParam(name = "max") int max) {
        return super.search(expr, max, this.wsCtx);
    }

    @WebMethod
    public boolean changePwd(@WebParam(name = "id") int id, @WebParam(name = "currentPwd") String currentPwd, @WebParam(name = "newPwd") String newPwd) {
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
    protected void setFetchMode(Session session) {
        if (session != null)
            session.enableFetchProfile("JOIN_ContactInformation");
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
