package de.odinoxin.aidware.aiddesk.plugins.contact.information;

import de.odinoxin.aidware.aidcloud.provider.ContactInformationProvider;
import de.odinoxin.aidware.aidcloud.provider.Provider;
import de.odinoxin.aidware.aidcloud.service.ConcurrentFault_Exception;
import de.odinoxin.aidware.aiddesk.plugins.RecordEditor;
import de.odinoxin.aidware.aiddesk.plugins.RecordView;

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
