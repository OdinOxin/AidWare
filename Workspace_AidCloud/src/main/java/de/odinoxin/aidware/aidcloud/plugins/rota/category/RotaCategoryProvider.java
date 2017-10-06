package de.odinoxin.aidware.aidcloud.plugins.rota.category;

import de.odinoxin.aidware.aidcloud.recordable.RecordHandler;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import javax.ws.rs.Path;
import java.util.ArrayList;
import java.util.List;

@Path("RotaCategory")
public class RotaCategoryProvider extends RecordHandler<RotaCategory> {

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
