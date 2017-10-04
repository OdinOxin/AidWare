package de.odinoxin.aidware.aidcloud.plugins.rota;

import de.odinoxin.aidware.aidcloud.plugins.RecordHandler;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import javax.ws.rs.Path;
import java.util.ArrayList;
import java.util.List;

@Path("Rota")
public class RotaProvider extends RecordHandler<Rota> {
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
