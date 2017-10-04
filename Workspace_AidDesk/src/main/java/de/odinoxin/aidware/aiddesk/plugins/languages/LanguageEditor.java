package de.odinoxin.aidware.aiddesk.plugins.languages;

import de.odinoxin.aidware.aidcloud.provider.LanguageProvider;
import de.odinoxin.aidware.aidcloud.provider.Provider;
import de.odinoxin.aidware.aidcloud.service.ConcurrentFault_Exception;
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
    protected Language onSave() throws ConcurrentFault_Exception {
        return this.getProvider().save(this.getRecord(), this.getOriginalRecord());
    }

    @Override
    protected boolean onDelete() {
        return this.getProvider().delete(this.getRecord().getId());
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
