package de.odinoxin.aidware.aiddesk.plugins.languages;

import de.odinoxin.aidware.aidcloud.provider.LanguageProvider;
import de.odinoxin.aidware.aidcloud.provider.Provider;
import de.odinoxin.aidware.aiddesk.plugins.RecordEditor;
import de.odinoxin.aidware.aiddesk.plugins.RecordView;

public class LanguageEditor extends RecordEditor<Language> {

    public LanguageEditor() {
        this(null);
    }

    public LanguageEditor(Language record) {
        super("Languages", record);
    }

    @Override
    protected void setRecord(Language language) {
        if (language == null)
            super.setRecord(new Language());
        else
            super.setRecord(language);
    }

    @Override
    protected Provider<Language> initProvider() {
        return new LanguageProvider();
    }

    @Override
    public RecordView<Language> newView(Language record) {
        return new LanguageView(record);
    }
}
