package de.odinoxin.aidware.aiddesk.plugins.contact.types;

import de.odinoxin.aidware.aidcloud.provider.ContactTypeProvider;
import de.odinoxin.aidware.aidcloud.provider.Provider;
import de.odinoxin.aidware.aiddesk.plugins.RecordEditor;
import de.odinoxin.aidware.aiddesk.plugins.RecordView;

public class ContactTypeEditor extends RecordEditor<ContactType> {

    public ContactTypeEditor() {
        this(null);
    }

    public ContactTypeEditor(ContactType record) {
        super("Contact type", record);
    }

    @Override
    protected void setRecord(ContactType contactType) {
        if (contactType == null)
            super.setRecord(new ContactType());
        else
            super.setRecord(contactType);
    }

    @Override
    protected Provider<ContactType> initProvider() {
        return new ContactTypeProvider();
    }

    @Override
    public RecordView<ContactType> newView(ContactType record) {
        return new ContactTypeView(record);
    }
}
