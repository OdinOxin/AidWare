package de.odinoxin.aiddesk.plugins.addresses;

import de.odinoxin.aidcloud.provider.AddressProvider;
import de.odinoxin.aidcloud.provider.Provider;
import de.odinoxin.aidcloud.service.ConcurrentFault_Exception;
import de.odinoxin.aiddesk.plugins.RecordEditor;
import de.odinoxin.aiddesk.plugins.RecordView;

public class AddressEditor extends RecordEditor<Address> {

    public AddressEditor() {
        this(null);
    }

    public AddressEditor(Address record) {
        super("Addresses", record);
    }

    @Override
    protected Address onSave() throws ConcurrentFault_Exception {
        return this.getProvider().save(this.getRecord(), this.getOriginalRecord());
    }

    @Override
    protected boolean onDelete() {
        return this.getProvider().delete(this.getRecord().getId());
    }

    @Override
    protected void setRecord(Address address) {
        if (address == null)
            super.setRecord(new Address());
        else
            super.setRecord(address);
    }

    @Override
    protected Provider<Address> initProvider() {
        return new AddressProvider();
    }

    @Override
    public RecordView<Address> newView(Address record) {
        return new AddressView(record);
    }
}
