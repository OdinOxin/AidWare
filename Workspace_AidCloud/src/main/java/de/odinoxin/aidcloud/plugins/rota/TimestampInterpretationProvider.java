package de.odinoxin.aidcloud.plugins.rota;

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
public class TimestampInterpretationProvider extends RecordHandler<TimestampInterpretation> {

    @Resource
    WebServiceContext wsCtx;

    @WebMethod
    public TimestampInterpretation getTimestampInterpretation(@WebParam(name = "id") int id) {
        return super.get(id, this.wsCtx);
    }

    @WebMethod
    public TimestampInterpretation saveTimestampInterpretation(@WebParam(name = "entity") TimestampInterpretation entity, @WebParam(name = "original") TimestampInterpretation original) throws ConcurrentFault {
        return this.getTimestampInterpretation(super.save(entity, original, this.wsCtx));
    }

    @WebMethod
    public boolean deleteTimestampInterpretation(@WebParam(name = "id") int id) {
        return super.delete(id, this.wsCtx);
    }

    @WebMethod
    public List<TimestampInterpretation> searchTimestampInterpretation(@WebParam(name = "expr") String[] expr, @WebParam(name = "max") int max) {
        return super.search(expr, max, this.wsCtx);
    }

    @Override
    protected Expression<Integer> getIdExpression(Root<TimestampInterpretation> root) {
        return root.get(TimestampInterpretation_.id);
    }

    @Override
    protected List<Expression<String>> getSearchExpressions(Root<TimestampInterpretation> root) {
        List<Expression<String>> expressions = new ArrayList<>();
        expressions.add(root.get(TimestampInterpretation_.name));
        return expressions;
    }
}
