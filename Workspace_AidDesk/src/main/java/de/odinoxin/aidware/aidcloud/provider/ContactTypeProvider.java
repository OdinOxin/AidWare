package de.odinoxin.aidware.aidcloud.provider;

import de.odinoxin.aidware.aiddesk.controls.refbox.RefBoxListItem;
import de.odinoxin.aidware.aiddesk.plugins.contact.types.ContactType;
import de.odinoxin.aidware.aiddesk.plugins.contact.types.ContactTypeEditor;

public class ContactTypeProvider extends Provider<ContactType> {

    @Override
    public RefBoxListItem<ContactType> getRefBoxItem(ContactType item) {
        if (item == null)
            return null;
        return new RefBoxListItem<>(item,
                (item.getName() == null ? "" : item.getName()),
                (item.getCode() == null ? "" : item.getCode()));
    }

    @Override
    public ContactTypeEditor openEditor(ContactType entity) {
        return new ContactTypeEditor(entity);
    }
}
