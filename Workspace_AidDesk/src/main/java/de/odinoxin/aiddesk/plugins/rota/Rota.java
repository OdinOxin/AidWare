package de.odinoxin.aiddesk.plugins.rota;

import de.odinoxin.aidcloud.service.RotaEntity;
import de.odinoxin.aidcloud.service.RotaShiftEntity;
import de.odinoxin.aiddesk.plugins.RecordItem;
import de.odinoxin.aiddesk.plugins.rota.category.RotaCategory;
import de.odinoxin.aiddesk.plugins.rota.shift.RotaShift;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class Rota extends RecordItem<RotaEntity> {

    private StringProperty title = new SimpleStringProperty(null, "Title");
    private ObjectProperty<RotaCategory> rotaCategory = new SimpleObjectProperty<>(null, "Category");
    private ListProperty<RotaShift> rotaShifts = new SimpleListProperty<>(null, "RotaShifts", FXCollections.observableArrayList());

    public Rota() {
        super();
        this.title.addListener((observable, oldValue, newValue) -> this.setChanged(true));
        this.rotaCategory.addListener((observable, oldValue, newValue) -> this.setChanged(true));
        this.rotaShifts.addListener((ListChangeListener.Change<? extends RotaShift> c) -> this.setChanged(true));
        this.setChanged(false);
    }

    public Rota(int id) {
        this();
        this.setId(id);
        this.setChanged(false);
    }

    public Rota(int id, String title, RotaCategory category, List<RotaShift> rotaShifts) {
        this(id);
        this.setTitle(title);
        this.setRotaCategory(category);
        this.setRotaShifts(rotaShifts);
        this.setChanged(false);
    }

    public Rota(RotaEntity entity) {
        this(entity.getId(), entity.getTitle(), entity.getRotaCategory() == null ? null : new RotaCategory(entity.getRotaCategory()), null);
        if (entity.getRotaShifts() != null) {
            List<RotaShift> list = new ArrayList<>();
            for (RotaShiftEntity contactInformationEntity : entity.getRotaShifts())
                list.add(new RotaShift(contactInformationEntity));
            this.setRotaShifts(list);
        }
        this.setChanged(false);
    }

    @Override
    protected Object clone() {
        return new Rota(this.getId(), this.getTitle(), this.getRotaCategory(), this.getRotaShifts());
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

    public RotaCategory getRotaCategory() {
        return rotaCategory.get();
    }

    public void setRotaCategory(RotaCategory category) {
        this.rotaCategory.set(category);
    }

    public ObjectProperty<RotaCategory> rotaCategoryProperty() {
        return rotaCategory;
    }

    public void setRotaShifts(List<RotaShift> rotaShifts) {
        if (this.rotaShifts.get() != null) {
            this.rotaShifts.get().clear();
            if (rotaShifts != null)
                this.rotaShifts.get().addAll(rotaShifts);
        } else if (rotaShifts != null)
            this.rotaShifts.set(FXCollections.observableArrayList(rotaShifts));
    }

    public List<RotaShift> getRotaShifts() {
        return rotaShifts.get();
    }

    public ListProperty<RotaShift> rotaShiftsProperty() {
        return rotaShifts;
    }

    @Override
    public RotaEntity toEntity() {
        RotaEntity entity = new RotaEntity();
        entity.setId(this.getId());
        entity.setTitle(this.getTitle());
        entity.setRotaCategory(this.getRotaCategory() == null ? null : this.getRotaCategory().toEntity());
        for (RotaShift item : this.getRotaShifts())
            if (item != null)
                entity.getRotaShifts().add(item.toEntity());
        return entity;
    }

    @Override
    protected Hashtable<String, Property<?>> getProperties() {
        Hashtable<String, Property<?>> properties = new Hashtable<>();
        properties.put(this.title.getName(), this.title);
        properties.put(this.rotaCategory.getName(), this.rotaCategory);
        properties.put(this.rotaShifts.getName(), this.rotaShifts);
        return properties;
    }
}
