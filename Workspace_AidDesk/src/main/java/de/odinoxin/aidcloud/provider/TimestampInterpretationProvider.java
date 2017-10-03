package de.odinoxin.aidcloud.provider;

import de.odinoxin.aidcloud.service.ConcurrentFault_Exception;
import de.odinoxin.aidcloud.service.TimestampInterpretationEntity;
import de.odinoxin.aidcloud.service.TimestampInterpretationProviderService;
import de.odinoxin.aiddesk.Login;
import de.odinoxin.aiddesk.controls.refbox.RefBoxListItem;
import de.odinoxin.aiddesk.plugins.rota.TimestampInterpretation;
import de.odinoxin.aiddesk.plugins.rota.TimestampInterpretationEditor;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TimestampInterpretationProvider implements Provider<TimestampInterpretation> {
    private static de.odinoxin.aidcloud.service.TimestampInterpretationProvider svc;

    private static de.odinoxin.aidcloud.service.TimestampInterpretationProvider getSvc() {
        if (svc == null) {
            if (Login.getServerUrl() == null)
                return null;
            try {
                svc = new TimestampInterpretationProviderService(new URL(Login.getServerUrl() + "/TimestampInterpretationProvider?wsdl")).getTimestampInterpretationProviderPort();
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            }
        }
        if (svc != null)
            Requester.setRequestHeaders(svc);
        return svc;
    }

    @Override
    public TimestampInterpretation get(int id) {
        if (TimestampInterpretationProvider.getSvc() != null) {
            TimestampInterpretationEntity entity = TimestampInterpretationProvider.getSvc().getTimestampInterpretation(id);
            if (entity != null)
                return new TimestampInterpretation(entity);
        }
        return null;
    }

    @Override
    public TimestampInterpretation save(TimestampInterpretation item, TimestampInterpretation original) throws ConcurrentFault_Exception {
        if (TimestampInterpretationProvider.getSvc() != null) {
            TimestampInterpretationEntity entity = TimestampInterpretationProvider.getSvc().saveTimestampInterpretation(item.toEntity(), original.toEntity());
            if (entity != null)
                return new TimestampInterpretation(entity);
        }
        return null;
    }

    @Override
    public boolean delete(int id) {
        if (TimestampInterpretationProvider.getSvc() != null)
            return TimestampInterpretationProvider.getSvc().deleteTimestampInterpretation(id);
        return false;
    }

    @Override
    public RefBoxListItem<TimestampInterpretation> getRefBoxItem(TimestampInterpretation item) {
        if (item == null)
            return null;
        return new RefBoxListItem<>(item,
                item.getName(),
                "");
    }

    @Override
    public List<RefBoxListItem<TimestampInterpretation>> search(List<String> expr, int max, List<Integer> exceptedIds) {
        if (TimestampInterpretationProvider.getSvc() != null) {
            List<TimestampInterpretationEntity> entities = TimestampInterpretationProvider.getSvc().searchTimestampInterpretation(expr, max, exceptedIds);
            List<RefBoxListItem<TimestampInterpretation>> result = new ArrayList<>();
            if (entities != null)
                for (TimestampInterpretationEntity entity : entities)
                    if (entity != null)
                        result.add(getRefBoxItem(new TimestampInterpretation(entity)));
            return result;
        }
        return null;
    }

    @Override
    public TimestampInterpretationEditor openEditor(TimestampInterpretation entity) {
        return new TimestampInterpretationEditor(entity);
    }
}
