package de.odinoxin.aidware.aidcloud.provider;

import de.odinoxin.aidware.aidcloud.Result;
import de.odinoxin.aidware.aiddesk.Login;
import de.odinoxin.aidware.aiddesk.controls.refbox.RefBoxListItem;
import de.odinoxin.aidware.aiddesk.plugins.people.Person;
import de.odinoxin.aidware.aiddesk.plugins.people.PersonEditor;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class LoginProvider extends Provider<Person> {

    @Override
    public RefBoxListItem<Person> getRefBoxItem(Person item) {
        if (item == null)
            return null;
        return new RefBoxListItem<>(item,
                (item.getForename() == null ? "" : item.getForename()) + " " + (item.getName() == null ? "" : item.getName()),
                (item.getCode() == null ? "" : item.getCode()));
    }

    @Override
    public PersonEditor openEditor(Person entity) {
        return null;
    }

    public boolean checkConnection() {
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(Login.getServerUrl()).path("Login").path("CheckConnection");
        Response response = webTarget.request(MediaType.APPLICATION_JSON).get();
        return (Boolean) response.readEntity(Result.class).x;
    }

    public boolean checkLogin(int id, String pwd) {
        return true;
    }
}
