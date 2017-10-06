package de.odinoxin.aidware.aidcloud.provider;

import de.odinoxin.aidware.aiddesk.controls.refbox.RefBoxListItem;
import de.odinoxin.aidware.aiddesk.plugins.languages.Language;
import de.odinoxin.aidware.aiddesk.plugins.languages.LanguageEditor;

public class LanguageProvider extends Provider<Language> {

    @Override
    public RefBoxListItem<Language> getRefBoxItem(Language item) {
        if (item == null)
            return null;
        return new RefBoxListItem<>(item,
                (item.getName() == null ? "" : item.getName()),
                (item.getCode() == null ? "" : item.getCode()));
    }

    @Override
    public LanguageEditor openEditor(Language entity) {
        return new LanguageEditor(entity);
    }
}
