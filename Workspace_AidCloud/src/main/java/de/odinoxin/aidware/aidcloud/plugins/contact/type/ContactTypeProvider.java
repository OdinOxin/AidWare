package de.odinoxin.aidware.aidcloud.plugins.contact.type;

import de.odinoxin.aidware.aidcloud.plugins.RecordHandler;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import javax.ws.rs.Path;
import java.util.ArrayList;
import java.util.List;

@Path("ContactType")
public class ContactTypeProvider extends RecordHandler<ContactType> {

    @Override
    protected Expression<Integer> getIdExpression(Root<ContactType> root) {
        return root.get(ContactType_.id);
    }

    @Override
    protected List<Expression<String>> getSearchExpressions(Root<ContactType> root) {
        List<Expression<String>> expressions = new ArrayList<>();

        expressions.add(root.get(ContactType_.name));
        expressions.add(root.get(ContactType_.code));
        return expressions;
    }

    @Override
    public void generateDefaults() {
        if (this.countRecords() <= 0) {
            ContactType tel = new ContactType();
            tel.setCode("Tel");
            tel.setName("Telephone");
            this.generate(tel);
            ContactType mail = new ContactType();
            mail.setCode("Mail");
            mail.setName("E-Mail");
            this.generate(mail);
        }
    }
}
