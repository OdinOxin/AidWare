package de.odinoxin.aiddesk.plugins.addresses;

import de.odinoxin.aidcloud.provider.AddressProvider;
import de.odinoxin.aidcloud.provider.Provider;
import de.odinoxin.aidcloud.service.ConcurrentFault_Exception;
import de.odinoxin.aiddesk.plugins.RecordEditor;

public class AddressEditor extends RecordEditor<Address> {

    public AddressEditor(Address address) {
        super("Addresses");
        setView(new AddressView());
        getView().bind(address);
        this.attemptLoadRecord(address);
        if (address == null)
            this.onNew();
    }

    @Override
    protected Address onSave() throws ConcurrentFault_Exception {
        return this.getProvider().save(this.getRecordItem(), this.getOriginalItem());
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
}
