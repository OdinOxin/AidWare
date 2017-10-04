package de.odinoxin.aidware.aidcloud.provider;

import de.odinoxin.aidware.aidcloud.service.ConcurrentFault_Exception;
import de.odinoxin.aidcloud.service.PersonalSettingEntity;
import de.odinoxin.aidcloud.service.PersonalSettingProviderService;
import de.odinoxin.aidware.aiddesk.Login;
import de.odinoxin.aidware.aiddesk.controls.refbox.RefBoxListItem;
import de.odinoxin.aidware.aiddesk.plugins.people.personalsetting.PersonalSetting;
import de.odinoxin.aidware.aiddesk.plugins.people.personalsetting.PersonalSettingEditor;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PersonalSettingProvider implements Provider<PersonalSetting> {
    private static de.odinoxin.aidcloud.service.PersonalSettingProvider svc;

    private static de.odinoxin.aidcloud.service.PersonalSettingProvider getSvc() {
        if (svc == null) {
            if (Login.getServerUrl() == null)
                return null;
            try {
                svc = new PersonalSettingProviderService(new URL(Login.getServerUrl() + "/PersonalSettingProvider?wsdl")).getPersonalSettingProviderPort();
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            }
        }
        if (svc != null)
            Requester.setRequestHeaders(svc);
        return svc;
    }

    @Override
    public PersonalSetting get(int id) {
        if (PersonalSettingProvider.getSvc() != null) {
            PersonalSettingEntity entity = PersonalSettingProvider.getSvc().getPersonalSetting(id);
            if (entity != null)
                return new PersonalSetting(entity);
        }
        return null;
    }

    @Override
    public PersonalSetting save(PersonalSetting item, PersonalSetting original) throws ConcurrentFault_Exception {
        if (PersonalSettingProvider.getSvc() != null) {
            PersonalSettingEntity entity = PersonalSettingProvider.getSvc().savePersonalSetting(item.toEntity(), original.toEntity());
            if (entity != null)
                return new PersonalSetting(entity);
        }
        return null;
    }

    @Override
    public boolean delete(int id) {
        if (PersonalSettingProvider.getSvc() != null)
            return PersonalSettingProvider.getSvc().deletePersonalSetting(id);
        return false;
    }

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
    public List<RefBoxListItem<PersonalSetting>> search(List<String> expr, int max, List<Integer> exceptedIds) {
        if (PersonalSettingProvider.getSvc() != null) {
            List<PersonalSettingEntity> entities = PersonalSettingProvider.getSvc().searchPersonalSetting(expr, max, exceptedIds);
            List<RefBoxListItem<PersonalSetting>> result = new ArrayList<>();
            if (entities != null)
                for (PersonalSettingEntity entity : entities) {
                    if (entity != null)
                        result.add(getRefBoxItem(new PersonalSetting(entity)));
                }
            return result;
        }
        return null;
    }

    @Override
    public PersonalSettingEditor openEditor(PersonalSetting entity) {
        return new PersonalSettingEditor(entity);
    }
}
