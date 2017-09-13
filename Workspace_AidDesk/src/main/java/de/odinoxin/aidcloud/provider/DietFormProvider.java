package de.odinoxin.aidcloud.provider;

import de.odinoxin.aidcloud.service.ConcurrentFault_Exception;
import de.odinoxin.aidcloud.service.DietFormEntity;
import de.odinoxin.aidcloud.service.DietFormProviderService;
import de.odinoxin.aiddesk.Login;
import de.odinoxin.aiddesk.controls.refbox.RefBoxListItem;
import de.odinoxin.aiddesk.plugins.dietform.DietForm;
import de.odinoxin.aiddesk.plugins.dietform.DietFormEditor;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DietFormProvider implements Provider<DietForm> {
    private static de.odinoxin.aidcloud.service.DietFormProvider svc;

    private static de.odinoxin.aidcloud.service.DietFormProvider getSvc() {
        if (svc == null) {
            if (Login.getServerUrl() == null)
                return null;
            try {
                svc = new DietFormProviderService(new URL(Login.getServerUrl() + "/DietFormProvider?wsdl")).getDietFormProviderPort();
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            }
        }
        if (svc != null)
            Requester.setRequestHeaders(svc);
        return svc;
    }

    @Override
    public DietForm get(int id) {
        if (DietFormProvider.getSvc() != null) {
            DietFormEntity entity = DietFormProvider.getSvc().getDietForm(id);
            if (entity != null)
                return new DietForm(entity);
        }
        return null;
    }

    @Override
    public DietForm save(DietForm item, DietForm original) throws ConcurrentFault_Exception {
        if (DietFormProvider.getSvc() != null) {
            DietFormEntity entity = DietFormProvider.getSvc().saveDietForm(item.toEntity(), original.toEntity());
            if (entity != null)
                return new DietForm(entity);
        }
        return null;
    }

    @Override
    public boolean delete(int id) {
        if (DietFormProvider.getSvc() != null)
            return DietFormProvider.getSvc().deleteDietForm(id);
        return false;
    }

    @Override
    public RefBoxListItem<DietForm> getRefBoxItem(DietForm item) {
        if (item == null)
            return null;
        return new RefBoxListItem<>(item,
                (item.getName() == null ? "" : item.getName()),
                "");
    }

    @Override
    public List<RefBoxListItem<DietForm>> search(List<String> expr, int max) {
        if (DietFormProvider.getSvc() != null) {
            List<DietFormEntity> entities = DietFormProvider.getSvc().searchDietForm(expr, max);
            List<RefBoxListItem<DietForm>> result = new ArrayList<>();
            if (entities != null)
                for (DietFormEntity entity : entities)
                    if (entity != null)
                        result.add(getRefBoxItem(new DietForm(entity)));
            return result;
        }
        return null;
    }

    @Override
    public DietFormEditor openEditor(DietForm entity) {
        return new DietFormEditor(entity);
    }
}
