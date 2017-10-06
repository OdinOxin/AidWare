package de.odinoxin.aidware.aidcloud.provider;

import de.odinoxin.aidware.aiddesk.Login;
import de.odinoxin.aidware.aiddesk.controls.refbox.RefBoxListItem;
import de.odinoxin.aidware.aiddesk.plugins.RecordEditor;
import de.odinoxin.aidware.aiddesk.plugins.RecordItem;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public class Provider<T extends RecordItem> {

    public T get(int id) {
        Class<T> clazz = getParameterizedType();
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(Login.getServerUrl()).path(clazz.getSimpleName()).path(String.valueOf(id));
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();
        return response.readEntity(getParameterizedType());
    }

    public T save(T item, T original) {
        return null;
    }

    public boolean delete(int id) {
        return false;
    }

    public RefBoxListItem<T> getRefBoxItem(T item) {
        return null;
    }

    public List<RefBoxListItem<T>> search(List<String> expr, int max, List<Integer> exceptedIds) {
        return null;
    }

    public RecordEditor<T> openEditor(T entity) {
        return null;
    }

    private Class<T> getParameterizedType() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
}
