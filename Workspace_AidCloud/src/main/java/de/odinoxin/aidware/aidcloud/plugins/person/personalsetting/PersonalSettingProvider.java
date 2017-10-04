package de.odinoxin.aidware.aidcloud.plugins.person.personalsetting;

import de.odinoxin.aidware.aidcloud.ConcurrentFault;
import de.odinoxin.aidware.aidcloud.plugins.RecordHandler;

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
public class PersonalSettingProvider extends RecordHandler<PersonalSetting> {

    @Resource
    WebServiceContext wsCtx;

    @WebMethod
    public PersonalSetting getPersonalSetting(@WebParam(name = "id") int id) {
        return super.get(id, this.wsCtx);
    }

    @WebMethod
    public PersonalSetting savePersonalSetting(@WebParam(name = "entity") PersonalSetting entity, @WebParam(name = "original") PersonalSetting original) throws ConcurrentFault {
        return this.getPersonalSetting(super.save(entity, original, this.wsCtx));
    }

    @WebMethod
    public boolean deletePersonalSetting(@WebParam(name = "id") int id) {
        return super.delete(id, this.wsCtx);
    }

    @WebMethod
    public List<PersonalSetting> searchPersonalSetting(@WebParam(name = "expr") String[] expr, @WebParam(name = "max") int max, @WebParam(name = "exceptIds") int[] exceptIds) {
        return super.search(expr, max, exceptIds, this.wsCtx);
    }

    @Override
    protected Expression<Integer> getIdExpression(Root<PersonalSetting> root) {
        return root.get(PersonalSetting_.id);
    }

    @Override
    protected List<Expression<String>> getSearchExpressions(Root<PersonalSetting> root) {
        List<Expression<String>> expressions = new ArrayList<>();
        return expressions;
    }
}
