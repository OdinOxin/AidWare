package de.odinoxin.aiddesk.plugins.contact.types;

import de.odinoxin.aidcloud.provider.ContactTypeProvider;
import de.odinoxin.aidcloud.provider.Provider;
import de.odinoxin.aidcloud.service.ConcurrentFault_Exception;
import de.odinoxin.aiddesk.plugins.RecordEditor;

public class ContactTypeEditor extends RecordEditor<ContactType> {

    public ContactTypeEditor(ContactType contactType) {
        super("Contact type");
        setView(new ContactTypeView());
        getView().bind(contactType);
        this.attemptLoadRecord(contactType);
        if (contactType == null)
            this.onNew();
    }

    @Override
    protected ContactType onSave() throws ConcurrentFault_Exception {
        return this.getProvider().save(this.getRecordItem(), this.getOriginalItem());
    }

    @Override
    protected boolean onDelete() {
        return this.getProvider().delete(this.getRecordItem().getId());
    }

    @Override
    protected void setRecord(ContactType contactType) {
        if (contactType == null)
            this.setRecordItem(new ContactType());
        else
            this.setRecordItem(contactType);
    }

    @Override
    protected Provider<ContactType> initProvider() {
        return new ContactTypeProvider();
    }
}
