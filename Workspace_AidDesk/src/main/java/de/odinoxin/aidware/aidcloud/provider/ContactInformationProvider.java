package de.odinoxin.aidware.aidcloud.provider;

import de.odinoxin.aidware.aiddesk.controls.refbox.RefBoxListItem;
import de.odinoxin.aidware.aiddesk.plugins.contact.information.ContactInformation;
import de.odinoxin.aidware.aiddesk.plugins.contact.information.ContactInformationEditor;
import de.odinoxin.aidware.aiddesk.plugins.contact.types.ContactType;

public class ContactInformationProvider extends Provider<ContactInformation> {

    @Override
    public RefBoxListItem<ContactInformation> getRefBoxItem(ContactInformation item) {
        if (item == null)
            return null;
        ContactType type = item.getContactType();
        String format = type == null || item.getInformation() == null ? "%s%s" : "%s: %s";

        return new RefBoxListItem<>(item,
                String.format(format, type == null ? "" : type.getCode() == null ? "" : type.getCode(), item.getInformation() == null ? "" : item.getInformation()),
                (type == null ? "" : type.getName() == null ? "" : type.getName()));
    }

    @Override
    public ContactInformationEditor openEditor(ContactInformation entity) {
        return new ContactInformationEditor(entity);
    }
}
