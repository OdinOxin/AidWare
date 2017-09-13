package de.odinoxin.aiddesk.plugins.rota;

import de.odinoxin.aidcloud.service.RotaEntity;
import de.odinoxin.aiddesk.plugins.RecordItem;
import de.odinoxin.aiddesk.plugins.rota.category.RotaCategory;
import javafx.beans.property.*;

import java.util.Hashtable;

public class Rota extends RecordItem<RotaEntity> {

    private StringProperty title = new SimpleStringProperty(null, "Title");
    private ObjectProperty<RotaCategory> category = new SimpleObjectProperty<>(null, "Category");

    public Rota() {
        super();
        this.title.addListener((observable, oldValue, newValue) -> this.setChanged(true));
        this.category.addListener((observable, oldValue, newValue) -> this.setChanged(true));
        this.setChanged(false);
    }

    public Rota(int id) {
        this();
        this.setId(id);
        this.setChanged(false);
    }

    public Rota(int id, String title, RotaCategory category) {
        this(id);
        this.setTitle(title);
        this.setChanged(false);
    }

    public Rota(RotaEntity entity) {
        this(entity.getId(), entity.getTitle(), entity.getCategory() == null ? null : new RotaCategory(entity.getCategory()));
    }

    @Override
    protected Object clone() {
        return new Rota(this.getId(), this.getTitle(), this.getCategory());
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public StringProperty titleProperty() {
        return title;
    }

    public RotaCategory getCategory() {
        return category.get();
    }

    public void setCategory(RotaCategory category) {
        this.category.set(category);
    }

    public ObjectProperty<RotaCategory> categoryProperty() {
        return category;
    }

    @Override
    public RotaEntity toEntity() {
        RotaEntity entity = new RotaEntity();
        entity.setId(this.getId());
        entity.setTitle(this.getTitle());
        entity.setCategory(this.getCategory() == null ? null : this.getCategory().toEntity());
        return entity;
    }

    @Override
    protected Hashtable<String, Property<?>> getProperties() {
        Hashtable<String, Property<?>> properties = new Hashtable<>();
        properties.put(this.title.getName(), this.title);
        properties.put(this.category.getName(), this.category);
        return properties;
    }
}
