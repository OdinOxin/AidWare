package de.odinoxin.aiddesk.plugins.people.personalsetting;

import de.odinoxin.aidcloud.provider.PersonalSettingProvider;
import de.odinoxin.aidcloud.provider.Provider;
import de.odinoxin.aiddesk.plugins.RecordEditor;
import de.odinoxin.aiddesk.plugins.RecordView;

public class PersonalSettingEditor extends RecordEditor<PersonalSetting> {

    public PersonalSettingEditor() {
        this(null);
    }

    public PersonalSettingEditor(PersonalSetting record) {
        super("PersonalSetting", record);
    }

    @Override
    protected void setRecord(PersonalSetting record) {
        if (record == null)
            super.setRecord(new PersonalSetting());
        else
            super.setRecord(record);
    }

    @Override
    protected Provider<PersonalSetting> initProvider() {
        return new PersonalSettingProvider();
    }

    @Override
    public RecordView<PersonalSetting> newView(PersonalSetting record) {
        return new PersonalSettingView(record, this);
    }
}
