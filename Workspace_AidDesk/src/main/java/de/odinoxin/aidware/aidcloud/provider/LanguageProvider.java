package de.odinoxin.aidware.aidcloud.provider;

import de.odinoxin.aidware.aidcloud.service.ConcurrentFault_Exception;
import de.odinoxin.aidware.aidcloud.service.LanguageEntity;
import de.odinoxin.aidware.aidcloud.service.LanguageProviderService;
import de.odinoxin.aidware.aiddesk.Login;
import de.odinoxin.aidware.aiddesk.controls.refbox.RefBoxListItem;
import de.odinoxin.aidware.aiddesk.plugins.languages.Language;
import de.odinoxin.aidware.aiddesk.plugins.languages.LanguageEditor;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class LanguageProvider implements Provider<Language> {

    private static de.odinoxin.aidware.aidcloud.service.LanguageProvider svc;

    private static de.odinoxin.aidware.aidcloud.service.LanguageProvider getSvc() {
        if (svc == null) {
            if (Login.getServerUrl() == null)
                return null;
            try {
                svc = new LanguageProviderService(new URL(Login.getServerUrl() + "/LanguageProvider?wsdl")).getLanguageProviderPort();
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            }
        }
        if (svc != null)
            Requester.setRequestHeaders(svc);
        return svc;
    }

    @Override
    public Language get(int id) {
        if (LanguageProvider.getSvc() != null) {
            LanguageEntity entity = LanguageProvider.getSvc().getLanguage(id);
            if (entity != null)
                return new Language(entity);
        }
        return null;
    }

    @Override
    public Language save(Language item, Language original) throws ConcurrentFault_Exception {
        if (LanguageProvider.getSvc() != null) {
            LanguageEntity entity = LanguageProvider.getSvc().saveLanguage(item.toEntity(), original.toEntity());
            if (entity != null)
                return new Language(entity);
        }
        return null;
    }

    @Override
    public boolean delete(int id) {
        if (LanguageProvider.getSvc() != null)
            return LanguageProvider.getSvc().deleteLanguage(id);
        return false;
    }

    @Override
    public RefBoxListItem<Language> getRefBoxItem(Language item) {
        if (item == null)
            return null;
        return new RefBoxListItem<>(item,
                (item.getName() == null ? "" : item.getName()),
                (item.getCode() == null ? "" : item.getCode()));
    }

    @Override
    public List<RefBoxListItem<Language>> search(List<String> expr, int max, List<Integer> exceptedIds) {
        if (LanguageProvider.getSvc() != null) {
            List<LanguageEntity> entities = LanguageProvider.getSvc().searchLanguage(expr, max, exceptedIds);
            List<RefBoxListItem<Language>> result = new ArrayList<>();
            if (entities != null)
                for (LanguageEntity entity : entities)
                    if (entity != null)
                        result.add(getRefBoxItem(new Language(entity)));
            return result;
        }
        return null;
    }

    @Override
    public LanguageEditor openEditor(Language entity) {
        return new LanguageEditor(entity);
    }
}
