package de.odinoxin.aidcloud.plugins.rota.shift;

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
public class RotaShiftProvider extends RecordHandler<RotaShift> {

    @Resource
    WebServiceContext wsCtx;

    @WebMethod
    public RotaShift getRotaShift(@WebParam(name = "id") int id) {
        return super.get(id, this.wsCtx);
    }

    @WebMethod
    public RotaShift saveRotaShift(@WebParam(name = "entity") RotaShift entity, @WebParam(name = "original") RotaShift original) throws ConcurrentFault {
        return this.getRotaShift(super.save(entity, original, this.wsCtx));
    }

    @WebMethod
    public boolean deleteRotaShift(@WebParam(name = "id") int id) {
        return super.delete(id, this.wsCtx);
    }

    @WebMethod
    public List<RotaShift> searchRotaShift(@WebParam(name = "expr") String[] expr, @WebParam(name = "max") int max, @WebParam(name = "exceptIds") int[] exceptIds) {
        return super.search(expr, max, exceptIds, this.wsCtx);
    }

    @Override
    protected Expression<Integer> getIdExpression(Root<RotaShift> root) {
        return root.get(RotaShift_.id);
    }

    @Override
    protected List<Expression<String>> getSearchExpressions(Root<RotaShift> root) {
        List<Expression<String>> expressions = new ArrayList<>();
        //expressions.add(root.get(RotaShift_.text));
        return expressions;
    }
}
