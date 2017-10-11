package de.odinoxin.aidware.aidcloud.provider;

import de.odinoxin.aidware.aiddesk.Login;
import de.odinoxin.aidware.aiddesk.controls.refbox.RefBoxListItem;
import de.odinoxin.aidware.aiddesk.plugins.RecordEditor;
import de.odinoxin.aidware.aiddesk.plugins.RecordItem;
import de.odinoxin.aidware.aiddesk.utils.Tuple;

import javax.ws.rs.client.*;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public abstract class Provider<T extends RecordItem> {

    protected String basePath;

    /**
     * Gets a single Entity for the given ID; Or null if no entity was found.
     *
     * @param id The ID to search for.
     * @return The read entity; Or null if no entity was found.
     */
    public T get(int id) {
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(Login.getServerUrl()).path(getBasePath()).path(String.valueOf(id));
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();
        return response.readEntity(getParameterizedType());
    }

    public T save(T item, T original) {
        if (item == null)
            throw new IllegalArgumentException("The item cannot be null!");

        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(Login.getServerUrl()).path(getBasePath());
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        Response response = null;

        if (item.getId() == 0) {
            response = invocationBuilder.post(Entity.entity(item, MediaType.APPLICATION_JSON));
        } else if (item.getId() > 0) {
            if (original == null)
                throw new IllegalArgumentException("The original item cannot be null on update!");
            if (item.getId() != original.getId())
                throw new IllegalArgumentException("The item is different from the original item!");
            Tuple<T, T> set = new Tuple<>(item, original);
            response = invocationBuilder.put(Entity.entity(set, MediaType.APPLICATION_JSON));
        }
        return response != null ? response.readEntity(getParameterizedType()) : null;
    }

    public boolean delete(int id) {
        return false;
    }

    /**
     * Reads a list of entities from the AidCloud service and return a List of RefBoxItems for these entities.
     *
     * @param expr        The search expressions
     * @param max         The maximum numbers of entities to read. Choose as less as possible, but as most as needed.
     * @param exceptedIds A list of IDs to NOT search for.
     * @return A List of RefBoxItems representing the read entities.
     */
    public List<RefBoxListItem<T>> search(List<String> expr, int max, List<Integer> exceptedIds) {
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(Login.getServerUrl()).path(getBasePath()).queryParam("expr", expr).queryParam("max", max);
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        List<T> entities = invocationBuilder.get(new GenericType<List<T>>(new ParameterizedType() {
            public Type[] getActualTypeArguments() {
                return new Type[]{getParameterizedType()};
            }

            public Type getRawType() {
                return List.class;
            }

            public Type getOwnerType() {
                return List.class;
            }
        }) {
        });
        List<RefBoxListItem<T>> refBoxListItems = new ArrayList<>();
        for (T entity : entities)
            refBoxListItems.add(getRefBoxItem(entity));
        return refBoxListItems;
    }

    /**
     * Subclasses should implement: Gets a RefBoxItem for the given item.
     * This method is not marked abstract, because subclasses don't have to implement this.
     *
     * @param item The item to get a RefBoxItem for.
     * @return null (If not overridden by subclass)
     */
    public RefBoxListItem<T> getRefBoxItem(T item) {
        return null;
    }

    /**
     * Subclasses should open a editor and initialise it with the given entity.
     *
     * @param entity The init-entity.
     * @return null (If not overridden by subclass)
     */
    public RecordEditor<T> openEditor(T entity) {
        return null;
    }

    private Class<T> getParameterizedType() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    private String getBasePath() {
        if (basePath == null || basePath.isEmpty())
            basePath = getParameterizedType().getSimpleName();
        return basePath;
    }
}
