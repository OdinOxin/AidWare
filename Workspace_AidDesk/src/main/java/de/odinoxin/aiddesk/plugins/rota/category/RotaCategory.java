package de.odinoxin.aiddesk.plugins.rota.category;

import de.odinoxin.aidcloud.service.RotaCategoryEntity;
import de.odinoxin.aiddesk.plugins.RecordItem;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Hashtable;

public class RotaCategory extends RecordItem<RotaCategoryEntity> {

    private StringProperty name = new SimpleStringProperty(null, "Name");

    public RotaCategory() {
        super();
        this.name.addListener((observable, oldValue, newValue) -> this.setChanged(true));
        this.setChanged(false);
    }

    public RotaCategory(int id) {
        this();
        this.setId(id);
        this.setChanged(false);
    }

    public RotaCategory(int id, String name) {
        this(id);
        this.setName(name);
        this.setChanged(false);
    }

    public RotaCategory(RotaCategoryEntity entity) {
        this(entity.getId(), entity.getName());
    }

    @Override
    protected Object clone() {
        return new RotaCategory(this.getId(), this.getName());
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
    public RotaCategoryEntity toEntity() {
        RotaCategoryEntity entity = new RotaCategoryEntity();
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
