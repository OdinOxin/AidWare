package de.odinoxin.aidware.aiddesk.plugins.languages;

import de.odinoxin.aidware.aiddesk.plugins.RecordItem;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Hashtable;

public class Language extends RecordItem {

    private StringProperty name = new SimpleStringProperty(null, "Name");
    private StringProperty code = new SimpleStringProperty(null, "Code");

    public Language() {
        super();
        this.name.addListener((observable, oldValue, newValue) -> this.setChanged(true));
        this.code.addListener((observable, oldValue, newValue) -> this.setChanged(true));
        this.setChanged(false);
    }

    public Language(int id) {
        this();
        this.setId(id);
        this.setChanged(false);
    }

    public Language(int id, String name, String code) {
        this(id);
        this.setName(name);
        this.setCode(code);
        this.setChanged(false);
    }

    @Override
    protected Object clone() {
        return new Language(this.getId(), this.getName(), this.getCode());
    }

    public String getName() {
        return name.get();
    }

    public String getCode() {
        return code.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setCode(String code) {
        this.code.set(code);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty codeProperty() {
        return code;
    }

    @Override
    protected Hashtable<String, Property<?>> getProperties() {
        Hashtable<String, Property<?>> properties = new Hashtable<>();
        properties.put(this.name.getName(), this.name);
        properties.put(this.code.getName(), this.code);
        return properties;
    }
}
