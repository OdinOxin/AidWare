package de.odinoxin.aidware.aidcloud.provider;


import de.odinoxin.aidware.aidcloud.service.ConcurrentFault_Exception;
import de.odinoxin.aidware.aidcloud.service.RotaCategoryEntity;
import de.odinoxin.aidware.aidcloud.service.RotaCategoryProviderService;
import de.odinoxin.aidware.aiddesk.Login;
import de.odinoxin.aidware.aiddesk.controls.refbox.RefBoxListItem;
import de.odinoxin.aidware.aiddesk.plugins.rota.category.RotaCategory;
import de.odinoxin.aidware.aiddesk.plugins.rota.category.RotaCategoryEditor;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RotaCategoryProvider implements Provider<RotaCategory> {
    private static de.odinoxin.aidware.aidcloud.service.RotaCategoryProvider svc;

    private static de.odinoxin.aidware.aidcloud.service.RotaCategoryProvider getSvc() {
        if (svc == null) {
            if (Login.getServerUrl() == null)
                return null;
            try {
                svc = new RotaCategoryProviderService(new URL(Login.getServerUrl() + "/RotaCategoryProvider?wsdl")).getRotaCategoryProviderPort();
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            }
        }
        if (svc != null)
            Requester.setRequestHeaders(svc);
        return svc;
    }

    @Override
    public RotaCategory get(int id) {
        if (RotaCategoryProvider.getSvc() != null) {
            RotaCategoryEntity entity = RotaCategoryProvider.getSvc().getRotaCategory(id);
            if (entity != null)
                return new RotaCategory(entity);
        }
        return null;
    }

    @Override
    public RotaCategory save(RotaCategory item, RotaCategory original) throws ConcurrentFault_Exception {
        if (RotaCategoryProvider.getSvc() != null) {
            RotaCategoryEntity entity = RotaCategoryProvider.getSvc().saveRotaCategory(item.toEntity(), original.toEntity());
            if (entity != null)
                return new RotaCategory(entity);
        }
        return null;
    }

    @Override
    public boolean delete(int id) {
        if (RotaCategoryProvider.getSvc() != null)
            return RotaCategoryProvider.getSvc().deleteRotaCategory(id);
        return false;
    }

    @Override
    public RefBoxListItem<RotaCategory> getRefBoxItem(RotaCategory item) {
        if (item == null)
            return null;
        return new RefBoxListItem<>(item,
                (item.getName() == null ? "" : item.getName()),
                "");
    }

    @Override
    public List<RefBoxListItem<RotaCategory>> search(List<String> expr, int max, List<Integer> exceptedIds) {
        if (RotaCategoryProvider.getSvc() != null) {
            List<RotaCategoryEntity> entities = RotaCategoryProvider.getSvc().searchRotaCategory(expr, max, exceptedIds);
            List<RefBoxListItem<RotaCategory>> result = new ArrayList<>();
            if (entities != null)
                for (RotaCategoryEntity entity : entities)
                    if (entity != null)
                        result.add(getRefBoxItem(new RotaCategory(entity)));
            return result;
        }
        return null;
    }

    @Override
    public RotaCategoryEditor openEditor(RotaCategory entity) {
        return new RotaCategoryEditor(entity);
    }
}
