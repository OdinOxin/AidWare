package de.odinoxin.aidware.aidcloud.plugins.rota.shift;

import de.odinoxin.aidware.aidcloud.plugins.RecordHandler;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import javax.ws.rs.Path;
import java.util.ArrayList;
import java.util.List;

@Path("RotaShift")
public class RotaShiftProvider extends RecordHandler<RotaShift> {
    @Override
    protected Expression<Integer> getIdExpression(Root<RotaShift> root) {
        return root.get(RotaShift_.id);
    }

    @Override
    protected List<Expression<String>> getSearchExpressions(Root<RotaShift> root) {
        List<Expression<String>> expressions = new ArrayList<>();
        expressions.add(root.get(RotaShift_.text));
        return expressions;
    }
}
