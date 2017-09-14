package de.odinoxin.aidcloud.provider;

import de.odinoxin.aidcloud.service.ConcurrentFault_Exception;
import de.odinoxin.aidcloud.service.RotaEntity;
import de.odinoxin.aidcloud.service.RotaProviderService;
import de.odinoxin.aiddesk.Login;
import de.odinoxin.aiddesk.controls.refbox.RefBoxListItem;
import de.odinoxin.aiddesk.plugins.rota.Rota;
import de.odinoxin.aiddesk.plugins.rota.RotaEditor;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RotaProvider implements Provider<Rota> {
    private static de.odinoxin.aidcloud.service.RotaProvider svc;

    private static de.odinoxin.aidcloud.service.RotaProvider getSvc() {
        if (svc == null) {
            if (Login.getServerUrl() == null)
                return null;
            try {
                svc = new RotaProviderService(new URL(Login.getServerUrl() + "/RotaProvider?wsdl")).getRotaProviderPort();
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            }
        }
        if (svc != null)
            Requester.setRequestHeaders(svc);
        return svc;
    }

    @Override
    public Rota get(int id) {
        if (RotaProvider.getSvc() != null) {
            RotaEntity entity = RotaProvider.getSvc().getRota(id);
            if (entity != null)
                return new Rota(entity);
        }
        return null;
    }

    @Override
    public Rota save(Rota item, Rota original) throws ConcurrentFault_Exception {
        if (RotaProvider.getSvc() != null) {
            RotaEntity entity = RotaProvider.getSvc().saveRota(item.toEntity(), original.toEntity());
            if (entity != null)
                return new Rota(entity);
        }
        return null;
    }

    @Override
    public boolean delete(int id) {
        if (RotaProvider.getSvc() != null)
            return RotaProvider.getSvc().deleteRota(id);
        return false;
    }

    @Override
    public RefBoxListItem<Rota> getRefBoxItem(Rota item) {
        if (item == null)
            return null;
        return new RefBoxListItem<>(item,
                (item.getTitle() == null ? "" : item.getTitle()),
                (item.getCategory() == null ? "" : item.getCategory().getName()));
    }

    @Override
    public List<RefBoxListItem<Rota>> search(List<String> expr, int max) {
        if (RotaProvider.getSvc() != null) {
            List<RotaEntity> entities = RotaProvider.getSvc().searchRota(expr, max);
            List<RefBoxListItem<Rota>> result = new ArrayList<>();
            if (entities != null)
                for (RotaEntity entity : entities)
                    if (entity != null)
                        result.add(getRefBoxItem(new Rota(entity)));
            return result;
        }
        return null;
    }

    @Override
    public RotaEditor openEditor(Rota entity) {
        return new RotaEditor(entity);
    }
}
