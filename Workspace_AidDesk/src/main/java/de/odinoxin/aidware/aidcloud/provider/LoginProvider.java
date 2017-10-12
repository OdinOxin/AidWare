package de.odinoxin.aidware.aidcloud.provider;

import de.odinoxin.aidware.aiddesk.auth.Credentials;
import de.odinoxin.aidware.aiddesk.auth.Login;
import de.odinoxin.aidware.aiddesk.controls.refbox.RefBoxListItem;
import de.odinoxin.aidware.aiddesk.plugins.people.Person;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class LoginProvider extends Provider<Person> {

    public LoginProvider() {
        this.basePath = "Auth";
    }

    @Override
    public RefBoxListItem<Person> getRefBoxItem(Person item) {
        if (item == null)
            return null;
        return new RefBoxListItem<>(item,
                (item.getForename() == null ? "" : item.getForename()) + " " + (item.getName() == null ? "" : item.getName()),
                (item.getCode() == null ? "" : item.getCode()));
    }

    public boolean checkConnection() {
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(Login.getServerUrl()).path(this.basePath).path("CheckConnection");
        Response response = webTarget.request(MediaType.APPLICATION_JSON).get();
        return response.getStatus() == Response.Status.OK.getStatusCode();
    }

    public String authenticate(int id, String pwd) {
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(Login.getServerUrl()).path(this.basePath).path(String.valueOf(id));
        Response response = webTarget.request(MediaType.APPLICATION_JSON).post(Entity.entity(new Credentials(pwd), MediaType.APPLICATION_JSON));
        return response.getStatus() == Response.Status.OK.getStatusCode() ? response.readEntity(String.class) : null;
    }
}
