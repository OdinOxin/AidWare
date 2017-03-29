package de.odinoxin.aiddesk.plugins.contact.information;

import de.odinoxin.aidcloud.provider.ContactInformationProvider;
import de.odinoxin.aidcloud.provider.Provider;
import de.odinoxin.aidcloud.service.ConcurrentFault_Exception;
import de.odinoxin.aiddesk.plugins.RecordEditor;
import de.odinoxin.aiddesk.plugins.RecordView;

public class ContactInformationEditor extends RecordEditor<ContactInformation> {

    public ContactInformationEditor(ContactInformation contactInformation) {
        super("Contact information");
        this.attemptLoadRecord(contactInformation);
        if (contactInformation == null)
            this.onNew();
    }

    @Override
    protected ContactInformation onSave() throws ConcurrentFault_Exception {
        return this.getProvider().save(this.getRecordItem(), this.getOriginalRecordItem());
    }

    @Override
    protected boolean onDelete() {
        return this.getProvider().delete(this.getRecordItem().getId());
    }

    @Override
    protected void setRecord(ContactInformation contactInformation) {
        if (contactInformation == null)
            this.setRecordItem(new ContactInformation());
        else
            this.setRecordItem(contactInformation);
    }

    @Override
    protected Provider<ContactInformation> initProvider() {
        return new ContactInformationProvider();
    }

    @Override
    public RecordView<ContactInformation> newView(ContactInformation record) {
        return new ContactInformationView(record);
    }
}
