package de.odinoxin.aidware.aiddesk.plugins.nutritiontype;

import de.odinoxin.aidware.aidcloud.service.NutritionTypeEntity;
import de.odinoxin.aidware.aiddesk.plugins.RecordItem;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Hashtable;

public class NutritionType extends RecordItem<NutritionTypeEntity> {

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

    public NutritionType(NutritionTypeEntity entity) {
        this(entity.getId(), entity.getName());
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
    public NutritionTypeEntity toEntity() {
        NutritionTypeEntity entity = new NutritionTypeEntity();
        entity.setId(this.getId());
        entity.setName(this.getName());
        return entity;
    }

    @Override
    protected Hashtable<String, Property<?>> getProperties() {
        Hashtable<String, Property<?>> properties = new Hashtable<>();
        properties.put(this.name.getName(), this.name);
        return properties;
    }
}
