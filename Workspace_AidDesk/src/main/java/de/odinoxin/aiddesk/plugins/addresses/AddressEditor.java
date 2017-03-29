package de.odinoxin.aiddesk.plugins.addresses;

import de.odinoxin.aidcloud.provider.AddressProvider;
import de.odinoxin.aidcloud.provider.Provider;
import de.odinoxin.aidcloud.service.ConcurrentFault_Exception;
import de.odinoxin.aiddesk.plugins.RecordEditor;
import de.odinoxin.aiddesk.plugins.RecordView;

public class AddressEditor extends RecordEditor<Address> {

    public AddressEditor(Address address) {
        super("Addresses");
        this.attemptLoadRecord(address);
        if (address == null)
            this.onNew();
    }

    @Override
    protected Address onSave() throws ConcurrentFault_Exception {
        return this.getProvider().save(this.getRecordItem(), this.getOriginalRecordItem());
    }

    @Override
    protected boolean onDelete() {
        return this.getProvider().delete(this.getRecordItem().getId());
    }

    @Override
    protected void setRecord(Address address) {
        if (address == null)
            this.setRecordItem(new Address());
        else
            this.setRecordItem(address);
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
