package de.odinoxin.aidware.aidcloud.provider;

import de.odinoxin.aidware.aiddesk.controls.refbox.RefBoxListItem;
import de.odinoxin.aidware.aiddesk.plugins.people.personalsetting.PersonalSetting;
import de.odinoxin.aidware.aiddesk.plugins.people.personalsetting.PersonalSettingEditor;

public class PersonalSettingProvider extends Provider<PersonalSetting> {

    @Override
    public RefBoxListItem<PersonalSetting> getRefBoxItem(PersonalSetting item) {
        if (item == null)
            return null;
        return new RefBoxListItem<>(item,
                String.format("%d", item.getId()),
                ""
        );
    }

    @Override
    public PersonalSettingEditor openEditor(PersonalSetting entity) {
        return new PersonalSettingEditor(entity);
    }
}
