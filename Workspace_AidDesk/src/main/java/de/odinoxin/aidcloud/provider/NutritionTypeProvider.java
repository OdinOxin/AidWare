package de.odinoxin.aidcloud.provider;

import de.odinoxin.aidcloud.service.ConcurrentFault_Exception;
import de.odinoxin.aidcloud.service.NutritionTypeEntity;
import de.odinoxin.aidcloud.service.NutritionTypeProviderService;
import de.odinoxin.aiddesk.Login;
import de.odinoxin.aiddesk.controls.refbox.RefBoxListItem;
import de.odinoxin.aiddesk.plugins.nutritiontype.NutritionType;
import de.odinoxin.aiddesk.plugins.nutritiontype.NutritionTypeEditor;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NutritionTypeProvider implements Provider<NutritionType> {
    private static de.odinoxin.aidcloud.service.NutritionTypeProvider svc;

    private static de.odinoxin.aidcloud.service.NutritionTypeProvider getSvc() {
        if (svc == null) {
            if (Login.getServerUrl() == null)
                return null;
            try {
                svc = new NutritionTypeProviderService(new URL(Login.getServerUrl() + "/NutritionTypeProvider?wsdl")).getNutritionTypeProviderPort();
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            }
        }
        if (svc != null)
            Requester.setRequestHeaders(svc);
        return svc;
    }

    @Override
    public NutritionType get(int id) {
        if (NutritionTypeProvider.getSvc() != null) {
            NutritionTypeEntity entity = NutritionTypeProvider.getSvc().getNutritionType(id);
            if (entity != null)
                return new NutritionType(entity);
        }
        return null;
    }

    @Override
    public NutritionType save(NutritionType item, NutritionType original) throws ConcurrentFault_Exception {
        if (NutritionTypeProvider.getSvc() != null) {
            NutritionTypeEntity entity = NutritionTypeProvider.getSvc().saveNutritionType(item.toEntity(), original.toEntity());
            if (entity != null)
                return new NutritionType(entity);
        }
        return null;
    }

    @Override
    public boolean delete(int id) {
        if (NutritionTypeProvider.getSvc() != null)
            return NutritionTypeProvider.getSvc().deleteNutritionType(id);
        return false;
    }

    @Override
    public RefBoxListItem<NutritionType> getRefBoxItem(NutritionType item) {
        if (item == null)
            return null;
        return new RefBoxListItem<>(item,
                (item.getName() == null ? "" : item.getName()),
                "");
    }

    @Override
    public List<RefBoxListItem<NutritionType>> search(List<String> expr, int max, List<Integer> exceptedIds) {
        if (NutritionTypeProvider.getSvc() != null) {
            List<NutritionTypeEntity> entities = NutritionTypeProvider.getSvc().searchNutritionType(expr, max, exceptedIds);
            List<RefBoxListItem<NutritionType>> result = new ArrayList<>();
            if (entities != null)
                for (NutritionTypeEntity entity : entities)
                    if (entity != null)
                        result.add(getRefBoxItem(new NutritionType(entity)));
            return result;
        }
        return null;
    }

    @Override
    public NutritionTypeEditor openEditor(NutritionType entity) {
        return new NutritionTypeEditor(entity);
    }
}
