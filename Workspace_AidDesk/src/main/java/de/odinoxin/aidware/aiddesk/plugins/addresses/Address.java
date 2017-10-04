package de.odinoxin.aidware.aiddesk.plugins.addresses;

import de.odinoxin.aidware.aidcloud.service.AddressEntity;
import de.odinoxin.aidware.aiddesk.plugins.RecordItem;
import de.odinoxin.aidware.aiddesk.plugins.countries.Country;
import javafx.beans.property.*;

import java.util.Hashtable;

public class Address extends RecordItem<AddressEntity> {

    private StringProperty street = new SimpleStringProperty(null, "Street");
    private StringProperty hsNo = new SimpleStringProperty(null, "HsNo");
    private StringProperty zip = new SimpleStringProperty(null, "Zip");
    private StringProperty city = new SimpleStringProperty(null, "City");
    private ObjectProperty<Country> country = new SimpleObjectProperty<>(null, "Country");

    public Address() {
        super();
        this.street.addListener((observable, oldValue, newValue) -> setChanged(true));
        this.hsNo.addListener((observable, oldValue, newValue) -> setChanged(true));
        this.zip.addListener((observable, oldValue, newValue) -> setChanged(true));
        this.city.addListener((observable, oldValue, newValue) -> setChanged(true));
        this.country.addListener((observable, oldValue, newValue) -> setChanged(true));
        this.setChanged(false);
    }

    public Address(int id) {
        this();
        this.setId(id);
        this.setChanged(false);
    }

    public Address(int id, String street, String hsNo, String zip, String city, Country country) {
        this(id);
        this.setStreet(street);
        this.setHsNo(hsNo);
        this.setZip(zip);
        this.setCity(city);
        this.setCountry(country);
        this.setChanged(true);
    }

    public Address(AddressEntity entity) {
        this(entity.getId(), entity.getStreet(), entity.getHsNo(), entity.getZip(), entity.getCity(), entity.getCountry() == null ? null : new Country(entity.getCountry()));
    }

    @Override
    protected Object clone() {
        return new Address(this.getId(), this.getStreet(), this.getHsNo(), this.getZip(), this.getCity(), this.getCountry());
    }

    public String getStreet() {
        return street.get();
    }

    public String getHsNo() {
        return hsNo.get();
    }

    public String getZip() {
        return zip.get();
    }

    public String getCity() {
        return city.get();
    }

    public Country getCountry() {
        return country.get();
    }

    public void setStreet(String street) {
        this.street.set(street);
    }

    public void setHsNo(String hsNo) {
        this.hsNo.set(hsNo);
    }

    public void setZip(String zip) {
        this.zip.set(zip);
    }

    public void setCity(String city) {
        this.city.set(city);
    }

    public void setCountry(Country country) {
        this.country.set(country);
    }

    public StringProperty streetProperty() {
        return street;
    }

    public StringProperty hsNoProperty() {
        return hsNo;
    }

    public StringProperty zipProperty() {
        return zip;
    }

    public StringProperty cityProperty() {
        return city;
    }

    public ObjectProperty<Country> countryProperty() {
        return country;
    }

    @Override
    public AddressEntity toEntity() {
        AddressEntity entity = new AddressEntity();
        entity.setId(this.getId());
        entity.setStreet(this.getStreet());
        entity.setHsNo(this.getHsNo());
        entity.setZip(this.getZip());
        entity.setCity(this.getCity());
        entity.setCountry(this.getCountry() == null ? null : this.getCountry().toEntity());
        return entity;
    }

    @Override
    protected Hashtable<String, Property<?>> getProperties() {
        Hashtable<String, Property<?>> properties = new Hashtable<>();
        properties.put(this.street.getName(), this.street);
        properties.put(this.hsNo.getName(), this.hsNo);
        properties.put(this.zip.getName(), this.zip);
        properties.put(this.city.getName(), this.city);
        properties.put(this.country.getName(), this.country);
        return properties;
    }
}
