package de.odinoxin.aidware.aidcloud.plugins.trackedchange;

import de.odinoxin.aidware.aidcloud.DB;
import de.odinoxin.aidware.aidcloud.recordable.RecordHandler;
import org.hibernate.Session;

import javax.persistence.Query;
import javax.ws.rs.*;
import java.util.Date;
import java.util.List;

@Path("TrackedChange")
public class TrackedChangeProvider extends RecordHandler<TrackedChange> {

    private static TrackedChangeProvider instance = new TrackedChangeProvider();

    public static TrackedChangeProvider getInstance() {
        return instance;
    }

    @GET
    @Path("{entityName}/{entityId}")
    public List<TrackedChange> getEntityChanges(@PathParam("entityName") String entityName, @PathParam("entityId") int entityId, @QueryParam("since") Date since, @DefaultValue("0") @QueryParam("lastN") int lastN) {
        if (entityName == null || entityId <= 0)
            return null;
        Query q = null;
        try (Session session = DB.open()) {
            if (since != null && lastN >= 0) {
                q = session.createQuery("FROM TrackedChange WHERE entityName = :entityName AND entityId = :entityId AND timestamp >= :since");
                q.setParameter("since", since);
            }
            if (lastN > 0) {
                if (q == null)
                    q = session.createQuery("FROM TrackedChange WHERE entityName = :entityName AND entityId = :entityId");
                q.setMaxResults(lastN);
            } else if (lastN < 0) {
                q = session.createQuery("FROM TrackedChange WHERE entityName = :entityName AND entityId = :entityId AND propertyName = :propertyName AND valueBefore IS NULL");
                q.setParameter("propertyName", "id");
            }
            if (q != null) {
                q.setParameter("entityName", entityName);
                q.setParameter("entityId", entityId);
                return q.getResultList();
            }
        }
        return null;
    }
}
