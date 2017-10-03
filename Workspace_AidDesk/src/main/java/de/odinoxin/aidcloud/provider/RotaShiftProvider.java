package de.odinoxin.aidcloud.provider;

import de.odinoxin.aidcloud.service.ConcurrentFault_Exception;
import de.odinoxin.aidcloud.service.RotaShiftEntity;
import de.odinoxin.aidcloud.service.RotaShiftProviderService;
import de.odinoxin.aiddesk.Login;
import de.odinoxin.aiddesk.controls.refbox.RefBoxListItem;
import de.odinoxin.aiddesk.plugins.rota.shift.RotaShift;
import de.odinoxin.aiddesk.plugins.rota.shift.RotaShiftEditor;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RotaShiftProvider implements Provider<RotaShift> {
    private static de.odinoxin.aidcloud.service.RotaShiftProvider svc;

    private static de.odinoxin.aidcloud.service.RotaShiftProvider getSvc() {
        if (svc == null) {
            if (Login.getServerUrl() == null)
                return null;
            try {
                svc = new RotaShiftProviderService(new URL(Login.getServerUrl() + "/RotaShiftProvider?wsdl")).getRotaShiftProviderPort();
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            }
        }
        if (svc != null)
            Requester.setRequestHeaders(svc);
        return svc;
    }

    @Override
    public RotaShift get(int id) {
        if (RotaShiftProvider.getSvc() != null) {
            RotaShiftEntity entity = RotaShiftProvider.getSvc().getRotaShift(id);
            if (entity != null)
                return new RotaShift(entity);
        }
        return null;
    }

    @Override
    public RotaShift save(RotaShift item, RotaShift original) throws ConcurrentFault_Exception {
        if (RotaShiftProvider.getSvc() != null) {
            RotaShiftEntity entity = RotaShiftProvider.getSvc().saveRotaShift(item.toEntity(), original.toEntity());
            if (entity != null)
                return new RotaShift(entity);
        }
        return null;
    }

    @Override
    public boolean delete(int id) {
        if (RotaShiftProvider.getSvc() != null)
            return RotaShiftProvider.getSvc().deleteRotaShift(id);
        return false;
    }

    @Override
    public RefBoxListItem<RotaShift> getRefBoxItem(RotaShift item) {
        if (item == null)
            return null;
        String format = item.getTsBeginn() == null || item.getTsEnd() == null ? "%s%s" : "%s - %s";
        DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.getDefault());
        return new RefBoxListItem<>(item,
                String.format(format, item.getTsBeginn() == null ? "" : dateFormatter.format(item.getTsBeginn()), item.getTsEnd() == null ? "" : dateFormatter.format(item.getTsEnd())),
                "");
    }

    @Override
    public List<RefBoxListItem<RotaShift>> search(List<String> expr, int max, List<Integer> exceptedIds) {
        if (RotaShiftProvider.getSvc() != null) {
            List<RotaShiftEntity> entities = RotaShiftProvider.getSvc().searchRotaShift(expr, max, exceptedIds);
            List<RefBoxListItem<RotaShift>> result = new ArrayList<>();
            if (entities != null)
                for (RotaShiftEntity entity : entities)
                    if (entity != null)
                        result.add(getRefBoxItem(new RotaShift(entity)));
            return result;
        }
        return null;
    }

    @Override
    public RotaShiftEditor openEditor(RotaShift entity) {
        return new RotaShiftEditor(entity);
    }
}
