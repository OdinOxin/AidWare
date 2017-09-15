package de.odinoxin.aiddesk.plugins.contact.information;

import de.odinoxin.aidcloud.provider.ContactInformationProvider;
import de.odinoxin.aidcloud.provider.Provider;
import de.odinoxin.aidcloud.service.ConcurrentFault_Exception;
import de.odinoxin.aiddesk.plugins.RecordEditor;
import de.odinoxin.aiddesk.plugins.RecordView;

public class ContactInformationEditor extends RecordEditor<ContactInformation> {

    public ContactInformationEditor() {
        this(null);
    }

    public ContactInformationEditor(ContactInformation record) {
        super("Contact information", record);
    }

    @Override
    protected ContactInformation onSave() throws ConcurrentFault_Exception {
        return this.getProvider().save(this.getRecord(), this.getOriginalRecord());
    }

    @Override
    protected boolean onDelete() {
        return this.getProvider().delete(this.getRecord().getId());
    }

    @Override
    protected void setRecord(ContactInformation contactInformation) {
        if (contactInformation == null)
            super.setRecord(new ContactInformation());
        else
            super.setRecord(contactInformation);
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
