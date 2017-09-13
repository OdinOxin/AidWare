package de.odinoxin.aidcloud.plugins.dietform;

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
public class DietFormProvider extends RecordHandler<DietForm> {

    @Resource
    WebServiceContext wsCtx;

    @WebMethod
    public DietForm getDietForm(@WebParam(name = "id") int id) {
        return super.get(id, this.wsCtx);
    }

    @WebMethod
    public DietForm saveDietForm(@WebParam(name = "entity") DietForm entity, @WebParam(name = "original") DietForm original) throws ConcurrentFault {
        return this.getDietForm(super.save(entity, original, this.wsCtx));
    }

    @WebMethod
    public boolean deleteDietForm(@WebParam(name = "id") int id) {
        return super.delete(id, this.wsCtx);
    }

    @WebMethod
    public List<DietForm> searchDietForm(@WebParam(name = "expr") String[] expr, @WebParam(name = "max") int max) {
        return super.search(expr, max, this.wsCtx);
    }

    @Override
    protected Expression<Integer> getIdExpression(Root<DietForm> root) {
        return root.get(DietForm_.id);
    }

    @Override
    protected List<Expression<String>> getSearchExpressions(Root<DietForm> root) {
        List<Expression<String>> expressions = new ArrayList<>();
        expressions.add(root.get(DietForm_.name));
        return expressions;
    }

    @Override
    public void generateDefaults() {
        if (this.countRecords() <= 0) {
            DietForm vegan = new DietForm();
            vegan.setName("Vegan");
            this.generate(vegan);
            DietForm vegetarian = new DietForm();
            vegetarian.setName("Vegetarian");
            this.generate(vegetarian);
        }
    }
}
