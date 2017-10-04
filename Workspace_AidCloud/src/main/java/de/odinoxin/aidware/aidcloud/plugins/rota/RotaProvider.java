package de.odinoxin.aidware.aidcloud.plugins.rota;

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
public class RotaProvider extends RecordHandler<Rota> {

    @Resource
    WebServiceContext wsCtx;

    @WebMethod
    public Rota getRota(@WebParam(name = "id") int id) {
        return super.get(id, this.wsCtx);
    }

    @WebMethod
    public Rota saveRota(@WebParam(name = "entity") Rota entity, @WebParam(name = "original") Rota original) throws ConcurrentFault {
        return this.getRota(super.save(entity, original, this.wsCtx));
    }

    @WebMethod
    public boolean deleteRota(@WebParam(name = "id") int id) {
        return super.delete(id, this.wsCtx);
    }

    @WebMethod
    public List<Rota> searchRota(@WebParam(name = "expr") String[] expr, @WebParam(name = "max") int max, @WebParam(name = "exceptIds") int[] exceptIds) {
        return super.search(expr, max, exceptIds, this.wsCtx);
    }

    @Override
    protected Expression<Integer> getIdExpression(Root<Rota> root) {
        return root.get(Rota_.id);
    }

    @Override
    protected List<Expression<String>> getSearchExpressions(Root<Rota> root) {
        List<Expression<String>> expressions = new ArrayList<>();
        expressions.add(root.get(Rota_.title));
        return expressions;
    }
}
