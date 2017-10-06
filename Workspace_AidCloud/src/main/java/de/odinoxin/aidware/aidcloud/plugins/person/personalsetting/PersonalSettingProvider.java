package de.odinoxin.aidware.aidcloud.plugins.person.personalsetting;

import de.odinoxin.aidware.aidcloud.recordable.RecordHandler;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import javax.ws.rs.Path;
import java.util.ArrayList;
import java.util.List;

@Path("PersonalSetting")
public class PersonalSettingProvider extends RecordHandler<PersonalSetting> {
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
