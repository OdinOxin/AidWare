package de.odinoxin.aidcloud.plugins.nutritiontype;

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
public class NutritionTypeProvider extends RecordHandler<NutritionType> {

    @Resource
    WebServiceContext wsCtx;

    @WebMethod
    public NutritionType getNutritionType(@WebParam(name = "id") int id) {
        return super.get(id, this.wsCtx);
    }

    @WebMethod
    public NutritionType saveNutritionType(@WebParam(name = "entity") NutritionType entity, @WebParam(name = "original") NutritionType original) throws ConcurrentFault {
        return this.getNutritionType(super.save(entity, original, this.wsCtx));
    }

    @WebMethod
    public boolean deleteNutritionType(@WebParam(name = "id") int id) {
        return super.delete(id, this.wsCtx);
    }

    @WebMethod
    public List<NutritionType> searchNutritionType(@WebParam(name = "expr") String[] expr, @WebParam(name = "max") int max) {
        return super.search(expr, max, this.wsCtx);
    }

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
