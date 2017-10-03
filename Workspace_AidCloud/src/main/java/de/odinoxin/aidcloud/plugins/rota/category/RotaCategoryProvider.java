package de.odinoxin.aidcloud.plugins.rota.category;

import de.odinoxin.aidcloud.ConcurrentFault;
import de.odinoxin.aidcloud.plugins.RecordHandler;

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
public class RotaCategoryProvider extends RecordHandler<RotaCategory> {

    @Resource
    WebServiceContext wsCtx;

    @WebMethod
    public RotaCategory getRotaCategory(@WebParam(name = "id") int id) {
        return super.get(id, this.wsCtx);
    }

    @WebMethod
    public RotaCategory saveRotaCategory(@WebParam(name = "entity") RotaCategory entity, @WebParam(name = "original") RotaCategory original) throws ConcurrentFault {
        return this.getRotaCategory(super.save(entity, original, this.wsCtx));
    }

    @WebMethod
    public boolean deleteRotaCategory(@WebParam(name = "id") int id) {
        return super.delete(id, this.wsCtx);
    }

    @WebMethod
    public List<RotaCategory> searchRotaCategory(@WebParam(name = "expr") String[] expr, @WebParam(name = "max") int max, @WebParam(name = "exceptIds") int[] exceptIds) {
        return super.search(expr, max, exceptIds, this.wsCtx);
    }

    @Override
    protected Expression<Integer> getIdExpression(Root<RotaCategory> root) {
        return root.get(RotaCategory_.id);
    }

    @Override
    protected List<Expression<String>> getSearchExpressions(Root<RotaCategory> root) {
        List<Expression<String>> expressions = new ArrayList<>();
        expressions.add(root.get(RotaCategory_.name));
        return expressions;
    }
}
