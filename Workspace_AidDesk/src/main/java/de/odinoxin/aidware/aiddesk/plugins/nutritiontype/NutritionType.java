package de.odinoxin.aidware.aiddesk.plugins.nutritiontype;

import de.odinoxin.aidware.aiddesk.plugins.RecordItem;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Hashtable;

public class NutritionType extends RecordItem {

    private StringProperty name = new SimpleStringProperty(null, "Name");

    public NutritionType() {
        super();
        this.name.addListener((observable, oldValue, newValue) -> this.setChanged(true));
        this.setChanged(false);
    }

    public NutritionType(int id) {
        this();
        this.setId(id);
        this.setChanged(false);
    }

    public NutritionType(int id, String name) {
        this(id);
        this.setName(name);
        this.setChanged(false);
    }

    @Override
    protected Object clone() {
        return new NutritionType(this.getId(), this.getName());
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    @Override
    protected Hashtable<String, Property<?>> getProperties() {
        Hashtable<String, Property<?>> properties = new Hashtable<>();
        properties.put(this.name.getName(), this.name);
        return properties;
    }
}
