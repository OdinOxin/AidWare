package de.odinoxin.aidware.aidcloud.plugins.address;

import de.odinoxin.aidware.aidcloud.plugins.RecordHandler;
import de.odinoxin.aidware.aidcloud.plugins.country.Country;
import de.odinoxin.aidware.aidcloud.plugins.country.Country_;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import javax.ws.rs.Path;
import java.util.ArrayList;
import java.util.List;

@Path("Address")
public class AddressProvider extends RecordHandler<Address> {

    @Override
    protected Expression<Integer> getIdExpression(Root<Address> root) {
        return root.get(Address_.id);
    }

    @Override
    protected List<Expression<String>> getSearchExpressions(Root<Address> root) {
        List<Expression<String>> expressions = new ArrayList<>();
        Join<Address, Country> joinCountry = root.join(Address_.country, JoinType.LEFT);
        expressions.add(root.get(Address_.street));
        expressions.add(root.get(Address_.hsNo));
        expressions.add(root.get(Address_.zip));
        expressions.add(root.get(Address_.city));
        expressions.add(joinCountry.get(Country_.name));
        return expressions;
    }
}
