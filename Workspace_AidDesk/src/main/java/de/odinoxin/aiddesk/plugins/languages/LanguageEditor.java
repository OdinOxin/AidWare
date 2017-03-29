package de.odinoxin.aiddesk.plugins.languages;

import de.odinoxin.aidcloud.provider.LanguageProvider;
import de.odinoxin.aidcloud.provider.Provider;
import de.odinoxin.aidcloud.service.ConcurrentFault_Exception;
import de.odinoxin.aiddesk.plugins.RecordEditor;
import de.odinoxin.aiddesk.plugins.RecordView;

public class LanguageEditor extends RecordEditor<Language> {

    public LanguageEditor(Language language) {
        super("Languages");
        this.attemptLoadRecord(language);
        if (language == null)
            this.onNew();
    }

    @Override
    protected Language onSave() throws ConcurrentFault_Exception {
        return this.getProvider().save(this.getRecordItem(), this.getOriginalRecordItem());
    }

    @Override
    protected boolean onDelete() {
        return this.getProvider().delete(this.getRecordItem().getId());
    }

    @Override
    protected void setRecord(Language language) {
        if (language == null)
            this.setRecordItem(new Language());
        else
            this.setRecordItem(language);
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
