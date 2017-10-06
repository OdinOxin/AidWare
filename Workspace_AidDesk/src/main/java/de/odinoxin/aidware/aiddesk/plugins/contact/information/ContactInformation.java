package de.odinoxin.aidware.aiddesk.plugins.contact.information;

import de.odinoxin.aidware.aiddesk.plugins.RecordItem;
import de.odinoxin.aidware.aiddesk.plugins.contact.types.ContactType;
import javafx.beans.property.*;

import java.util.Hashtable;

public class ContactInformation extends RecordItem {

    private ObjectProperty<ContactType> contactType = new SimpleObjectProperty<>(null, "ContactType");
    private StringProperty information = new SimpleStringProperty(null, "Information");

    public ContactInformation() {
        super();
        contactType.addListener((observable, oldValue, newValue) -> this.setChanged(true));
        information.addListener((observable, oldValue, newValue) -> this.setChanged(true));
        this.setChanged(false);
    }

    public ContactInformation(int id) {
        this();
        this.setId(id);
        this.setChanged(false);
    }

    public ContactInformation(int id, ContactType contactType, String information) {
        this(id);
        this.setContactType(contactType);
        this.setInformation(information);
        this.setChanged(false);
    }

    @Override
    protected Object clone() {
        return new ContactInformation(this.getId(), this.getContactType(), this.getInformation());
    }

    public ContactType getContactType() {
        return contactType.get();
    }

    public String getInformation() {
        return information.get();
    }

    public void setContactType(ContactType contactType) {
        this.contactType.set(contactType);
    }

    public void setInformation(String information) {
        this.information.set(information);
    }

    public ObjectProperty<ContactType> contactTypeProperty() {
        return contactType;
    }

    public StringProperty informationProperty() {
        return information;
    }

    @Override
    protected Hashtable<String, Property<?>> getProperties() {
        Hashtable<String, Property<?>> properties = new Hashtable<>();
        properties.put(this.contactType.getName(), this.contactType);
        properties.put(this.information.getName(), this.information);
        return properties;
    }
}
