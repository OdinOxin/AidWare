package de.odinoxin.aidware.aiddesk.plugins.countries;

import de.odinoxin.aidware.aiddesk.plugins.RecordItem;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Hashtable;

public class Country extends RecordItem {
    private StringProperty alpha2 = new SimpleStringProperty(null, "Alpha2");
    private StringProperty alpha3 = new SimpleStringProperty(null, "Alpha3");
    private StringProperty name = new SimpleStringProperty(null, "Name");
    private StringProperty areaCode = new SimpleStringProperty(null, "AreaCode");

    public Country() {
        super();
        this.alpha2.addListener((observable, oldValue, newValue) -> this.setChanged(true));
        this.alpha3.addListener((observable, oldValue, newValue) -> this.setChanged(true));
        this.name.addListener((observable, oldValue, newValue) -> this.setChanged(true));
        this.areaCode.addListener((observable, oldValue, newValue) -> this.setChanged(true));
        this.setChanged(false);
    }

    public Country(int id) {
        this();
        this.setId(id);
        this.setChanged(false);
    }

    public Country(int id, String alpha2, String alpha3, String name, String areaCode) {
        this(id);
        this.setAlpha2(alpha2);
        this.setAlpha3(alpha3);
        this.setName(name);
        this.setAreaCode(areaCode);
        this.setChanged(false);
    }

    @Override
    protected Object clone() {
        return new Country(this.getId(), this.getAlpha2(), this.getAlpha3(), this.getName(), this.getAreaCode());
    }

    public String getAlpha2() {
        return alpha2.get();
    }

    public String getAlpha3() {
        return alpha3.get();
    }

    public String getName() {
        return name.get();
    }

    public String getAreaCode() {
        return areaCode.get();
    }

    public void setAlpha2(String alpha2) {
        this.alpha2.set(alpha2);
    }

    public void setAlpha3(String alpha3) {
        this.alpha3.set(alpha3);
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setAreaCode(String areaCode) {
        this.areaCode.set(areaCode);
    }

    public StringProperty alpha2Property() {
        return alpha2;
    }

    public StringProperty alpha3Property() {
        return alpha3;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty areaCodeProperty() {
        return areaCode;
    }

    @Override
    protected Hashtable<String, Property<?>> getProperties() {
        Hashtable<String, Property<?>> properties = new Hashtable<>();
        properties.put(this.alpha2.getName(), this.alpha2);
        properties.put(this.alpha3.getName(), this.alpha3);
        properties.put(this.name.getName(), this.name);
        properties.put(this.areaCode.getName(), this.areaCode);
        return properties;
    }
}
