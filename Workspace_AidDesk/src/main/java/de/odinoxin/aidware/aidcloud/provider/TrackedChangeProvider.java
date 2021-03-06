package de.odinoxin.aidware.aidcloud.provider;

import de.odinoxin.aidware.aiddesk.auth.Login;
import de.odinoxin.aidware.aiddesk.plugins.TrackedChange;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

public class TrackedChangeProvider {

    public List<TrackedChange> getEntityChanges(String entityName, int entityId, Date since) {
        return Provider.webCall(webTarget -> Provider.newInvocationBuilder(webTarget).get(new GenericType<List<TrackedChange>>(new ParameterizedType() {
            public Type[] getActualTypeArguments() {
                return new Type[]{TrackedChange.class};
            }

            public Type getRawType() {
                return List.class;
            }

            public Type getOwnerType() {
                return List.class;
            }
        }) {
        }), ClientBuilder.newClient().target(Login.getServerUrl()).path("TrackedChange").path(entityName).path(String.valueOf(entityId)).queryParam("since", since));
    }

    public TrackedChange getCreationEntry(String entityName, int entityId) {
        return get(entityName, entityId, -1);
    }

    public TrackedChange getLastChangeEntry(String entityName, int entityId) {
        return get(entityName, entityId, 1);
    }

    private TrackedChange get(String entityName, int entityId, int lastN) {
        return Provider.webCall(webTarget -> {
            List<TrackedChange> entities = Provider.newInvocationBuilder(webTarget).get(new GenericType<List<TrackedChange>>(new ParameterizedType() {
                public Type[] getActualTypeArguments() {
                    return new Type[]{TrackedChange.class};
                }

                public Type getRawType() {
                    return List.class;
                }

                public Type getOwnerType() {
                    return List.class;
                }
            }) {
            });
            return entities != null && entities.size() == 1 ? entities.get(0) : null;
        }, ClientBuilder.newClient().target(Login.getServerUrl()).path("TrackedChange").path(entityName).path(String.valueOf(entityId)).queryParam("lastN", lastN));
    }
}
