package de.odinoxin.aidware.aiddesk.plugins.addresses;

import de.odinoxin.aidware.aidcloud.provider.AddressProvider;
import de.odinoxin.aidware.aidcloud.provider.Provider;
import de.odinoxin.aidware.aiddesk.plugins.RecordEditor;
import de.odinoxin.aidware.aiddesk.plugins.RecordView;

public class AddressEditor extends RecordEditor<Address> {

    public AddressEditor() {
        this(null);
    }

    public AddressEditor(Address record) {
        super("Addresses", record);
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
