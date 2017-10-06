package de.odinoxin.aidware.aidcloud.plugins.country;

import de.odinoxin.aidware.aidcloud.recordable.RecordHandler;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import javax.ws.rs.Path;
import java.util.ArrayList;
import java.util.List;

@Path("Country")
public class CountryProvider extends RecordHandler<Country> {

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
