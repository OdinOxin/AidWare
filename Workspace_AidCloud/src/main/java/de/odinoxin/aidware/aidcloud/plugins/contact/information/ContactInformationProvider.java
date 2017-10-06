package de.odinoxin.aidware.aidcloud.plugins.contact.information;

import de.odinoxin.aidware.aidcloud.recordable.RecordHandler;
import de.odinoxin.aidware.aidcloud.plugins.contact.type.ContactType;
import de.odinoxin.aidware.aidcloud.plugins.contact.type.ContactType_;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import javax.ws.rs.Path;
import java.util.ArrayList;
import java.util.List;

@Path("ContactInformation")
public class ContactInformationProvider extends RecordHandler<ContactInformation> {

    @Override
    protected Expression<Integer> getIdExpression(Root<ContactInformation> root) {
        return root.get(ContactInformation_.id);
    }

    @Override
    protected List<Expression<String>> getSearchExpressions(Root<ContactInformation> root) {
        List<Expression<String>> expressions = new ArrayList<>();
        Join<ContactInformation, ContactType> joinContactType = root.join(ContactInformation_.contactType, JoinType.LEFT);
        expressions.add(root.get(ContactInformation_.information));
        expressions.add(joinContactType.get(ContactType_.name));
        expressions.add(joinContactType.get(ContactType_.code));
        return expressions;
    }
}
