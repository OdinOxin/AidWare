package de.odinoxin.aidware.aidcloud.plugins.country;

import de.odinoxin.aidware.aidcloud.ConcurrentFault;
import de.odinoxin.aidware.aidcloud.plugins.RecordHandler;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import javax.xml.ws.WebServiceContext;
import java.util.ArrayList;
import java.util.List;

@WebService
public class CountryProvider extends RecordHandler<Country> {

    @Resource
    WebServiceContext wsCtx;

    @WebMethod
    public Country getCountry(@WebParam(name = "id") int id) {
        return super.get(id, this.wsCtx);
    }

    @WebMethod
    public Country saveCountry(@WebParam(name = "entity") Country entity, @WebParam(name = "original") Country original) throws ConcurrentFault {
        return this.getCountry(super.save(entity, original, this.wsCtx));
    }

    @WebMethod
    public boolean deleteCountry(@WebParam(name = "id") int id) {
        return super.delete(id, this.wsCtx);
    }

    @WebMethod
    public List<Country> searchCountry(@WebParam(name = "expr") String[] expr, @WebParam(name = "max") int max, @WebParam(name = "exceptIds") int[] exceptIds) {
        return super.search(expr, max, exceptIds, this.wsCtx);
    }

    @Override
    protected Expression<Integer> getIdExpression(Root<Country> root) {
        return root.get(Country_.id);
    }

    @Override
    protected List<Expression<String>> getSearchExpressions(Root<Country> root) {
        List<Expression<String>> expressions = new ArrayList<>();
        expressions.add(root.get(Country_.alpha2));
        expressions.add(root.get(Country_.alpha3));
        expressions.add(root.get(Country_.name));
        expressions.add(root.get(Country_.areaCode));
        return expressions;
    }
}
