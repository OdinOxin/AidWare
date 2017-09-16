package de.odinoxin.aidcloud.plugins;

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
public class TrackedChangeProvider extends RecordHandler<TrackedChange> {

    private static TrackedChangeProvider instance = new TrackedChangeProvider();

    @Resource
    WebServiceContext wsCtx;

    public static TrackedChangeProvider getInstance() {
        return instance;
    }

    @WebMethod
    public TrackedChange getTrackedChange(@WebParam(name = "id") int id) {
        return super.get(id, this.wsCtx);
    }

    @WebMethod
    public List<TrackedChange> searchTrackedChange(@WebParam(name = "expr") String[] expr, @WebParam(name = "max") int max) {
        return super.search(expr, max, this.wsCtx);
    }

    @Override
    protected Expression<Integer> getIdExpression(Root<TrackedChange> root) {
        return root.get(TrackedChange_.id);
    }

    @Override
    protected List<Expression<String>> getSearchExpressions(Root<TrackedChange> root) {
        List<Expression<String>> expressions = new ArrayList<>();
        expressions.add(root.get(TrackedChange_.entityName));
        return expressions;
    }
}
