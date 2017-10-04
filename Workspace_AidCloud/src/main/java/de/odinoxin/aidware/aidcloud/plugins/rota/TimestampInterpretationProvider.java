package de.odinoxin.aidware.aidcloud.plugins.rota;

import de.odinoxin.aidware.aidcloud.plugins.RecordHandler;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import javax.ws.rs.Path;
import java.util.ArrayList;
import java.util.List;

@Path("TimestampInterpretation")
public class TimestampInterpretationProvider extends RecordHandler<TimestampInterpretation> {
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
