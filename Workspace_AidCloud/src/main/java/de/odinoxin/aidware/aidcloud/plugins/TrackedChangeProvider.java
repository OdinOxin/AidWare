package de.odinoxin.aidware.aidcloud.plugins;

import de.odinoxin.aidware.aidcloud.DB;
import org.hibernate.Session;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.persistence.Query;
import javax.xml.ws.WebServiceContext;
import java.util.Date;
import java.util.List;

@WebService
public class TrackedChangeProvider extends RecordHandler<TrackedChange> {

    private static TrackedChangeProvider instance = new TrackedChangeProvider();

    @Resource
    WebServiceContext wsCtx;

    public static TrackedChangeProvider getInstance() {
        return instance;
    }

    @WebMethod
    public List<TrackedChange> getEntityChanges(@WebParam(name = "entityName") String entityName, @WebParam(name = "entityId") int entityId, @WebParam(name = "since") Date since) {
        if (entityName == null || entityId <= 0 || since == null)
            return null;
        try (Session session = DB.open()) {
            Query q = session.createQuery("FROM TrackedChange WHERE entityName = :entityName AND entityId = :entityId AND timestamp >= :since");
            q.setParameter("entityName", entityName);
            q.setParameter("entityId", entityId);
            q.setParameter("since", since);
            return q.getResultList();
        }
    }

    @WebMethod
    public TrackedChange getCreationEntry(@WebParam(name = "entityName") String entityName, @WebParam(name = "entityId") int entityId) {
        if (entityName == null || entityId <= 0)
            return null;
        try (Session session = DB.open()) {
            Query q = session.createQuery("FROM TrackedChange WHERE entityName = :entityName AND entityId = :entityId AND propertyName = :propertyName AND valueBefore IS NULL");
            q.setParameter("entityName", entityName);
            q.setParameter("entityId", entityId);
            q.setParameter("propertyName", "id");
            List<TrackedChange> result = q.getResultList();
            if (result != null && result.size() == 1)
                return result.get(0);
        }
        return null;
    }

    @WebMethod
    public TrackedChange getLastChangeEntry(@WebParam(name = "entityName") String entityName, @WebParam(name = "entityId") int entityId) {
        if (entityName == null || entityId <= 0)
            return null;
        try (Session session = DB.open()) {
            Query q = session.createQuery("FROM TrackedChange WHERE entityName = :entityName AND entityId = :entityId ORDER BY timestamp DESC, id DESC");
            q.setParameter("entityName", entityName);
            q.setParameter("entityId", entityId);
            List<TrackedChange> result = q.setMaxResults(1).getResultList();
            if (result != null && !result.isEmpty())
                return result.get(0);
        }
        return null;
    }
}
