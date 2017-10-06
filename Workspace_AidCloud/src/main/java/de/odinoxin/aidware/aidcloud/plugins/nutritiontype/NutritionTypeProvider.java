package de.odinoxin.aidware.aidcloud.plugins.nutritiontype;

import de.odinoxin.aidware.aidcloud.recordable.RecordHandler;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import javax.ws.rs.Path;
import java.util.ArrayList;
import java.util.List;

@Path("NutritionType")
public class NutritionTypeProvider extends RecordHandler<NutritionType> {

    @Override
    protected Expression<Integer> getIdExpression(Root<NutritionType> root) {
        return root.get(NutritionType_.id);
    }

    @Override
    protected List<Expression<String>> getSearchExpressions(Root<NutritionType> root) {
        List<Expression<String>> expressions = new ArrayList<>();
        expressions.add(root.get(NutritionType_.name));
        return expressions;
    }

    @Override
    public void generateDefaults() {
        if (this.countRecords() <= 0) {
            NutritionType vegan = new NutritionType();
            vegan.setName("Vegan");
            this.generate(vegan);
            NutritionType vegetarian = new NutritionType();
            vegetarian.setName("Vegetarian");
            this.generate(vegetarian);
        }
    }
}
