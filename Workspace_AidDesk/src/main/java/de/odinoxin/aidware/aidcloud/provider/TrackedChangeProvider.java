package de.odinoxin.aidware.aidcloud.provider;

import de.odinoxin.aidware.aidcloud.service.TrackedChangeEntity;
import de.odinoxin.aidware.aidcloud.service.TrackedChangeProviderService;
import de.odinoxin.aidware.aiddesk.Login;
import de.odinoxin.aidware.aiddesk.plugins.RecordItem;
import de.odinoxin.aidware.aiddesk.plugins.TrackedChange;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TrackedChangeProvider {
    private static de.odinoxin.aidware.aidcloud.service.TrackedChangeProvider svc;

    private static de.odinoxin.aidware.aidcloud.service.TrackedChangeProvider getSvc() {
        if (svc == null) {
            if (Login.getServerUrl() == null)
                return null;
            try {
                svc = new TrackedChangeProviderService(new URL(Login.getServerUrl() + "/TrackedChangeProvider?wsdl")).getTrackedChangeProviderPort();
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            }
        }
        if (svc != null)
            Requester.setRequestHeaders(svc);
        return svc;
    }

    public List<TrackedChange> getEntityChanges(String entityName, int entityId, Date since) {
        if (TrackedChangeProvider.getSvc() != null) {
            List<TrackedChangeEntity> entities = TrackedChangeProvider.getSvc().getEntityChanges(entityName, entityId, RecordItem.toXMLGregorianCalendar(since));
            if (entities != null) {
                List<TrackedChange> result = new ArrayList<>();
                for (TrackedChangeEntity entity : entities)
                    result.add(new TrackedChange(entity));
                return result;
            }
        }
        return null;
    }

    public TrackedChange getCreationEntry(String entityName, int entityId) {
        if (TrackedChangeProvider.getSvc() != null) {
            TrackedChangeEntity entity = TrackedChangeProvider.getSvc().getCreationEntry(entityName, entityId);
            if (entity != null)
                return new TrackedChange(entity);
        }
        return null;
    }

    public TrackedChange getLastChangeEntry(String entityName, int entityId) {
        if (TrackedChangeProvider.getSvc() != null) {
            TrackedChangeEntity entity = TrackedChangeProvider.getSvc().getLastChangeEntry(entityName, entityId);
            if (entity != null)
                return new TrackedChange(entity);
        }
        return null;
    }
}
