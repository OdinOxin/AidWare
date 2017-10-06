package de.odinoxin.aidware.aidcloud.provider;

import de.odinoxin.aidware.aiddesk.controls.refbox.RefBoxListItem;
import de.odinoxin.aidware.aiddesk.plugins.addresses.Address;
import de.odinoxin.aidware.aiddesk.plugins.addresses.AddressEditor;

public class AddressProvider extends Provider<Address> {

    @Override
    public RefBoxListItem<Address> getRefBoxItem(Address item) {
        if (item == null)
            return null;
        return new RefBoxListItem<>(item,
                (item.getStreet() == null ? "" : item.getStreet()) + " " +
                        (item.getHsNo() == null ? "" : item.getHsNo()),
                (item.getZip() == null ? "" : item.getZip()) + " " +
                        (item.getCity() == null ? "" : item.getCity()) + "\n" +
                        (item.getCountry() == null ? "" : item.getCountry().getName() == null ? "" : item.getCountry().getName()));
    }

    @Override
    public AddressEditor openEditor(Address entity) {
        return new AddressEditor(entity);
    }
}