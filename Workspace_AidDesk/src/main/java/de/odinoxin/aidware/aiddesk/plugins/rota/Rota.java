package de.odinoxin.aidware.aiddesk.plugins.rota;

import de.odinoxin.aidware.aiddesk.plugins.RecordItem;
import de.odinoxin.aidware.aiddesk.plugins.rota.category.RotaCategory;
import de.odinoxin.aidware.aiddesk.plugins.rota.shift.RotaShift;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;

public class Rota extends RecordItem {

    private StringProperty title = new SimpleStringProperty(null, "title");
    private ObjectProperty<RotaCategory> rotaCategory = new SimpleObjectProperty<>(null, "rotaCategory");
    private ListProperty<RotaShift> rotaShifts = new SimpleListProperty<>(null, "rotaShifts", FXCollections.observableArrayList());
    private ObjectProperty<Date> firstBeginn = new SimpleObjectProperty<>(null, "firstBeginn");
    private ObjectProperty<Date> lastEnd = new SimpleObjectProperty<>(null, "lastEnd");

    public Rota() {
        super();
        this.title.addListener((observable, oldValue, newValue) -> this.setChanged(true));
        this.rotaCategory.addListener((observable, oldValue, newValue) -> this.setChanged(true));
        this.rotaShifts.addListener((ListChangeListener.Change<? extends RotaShift> c) -> {
            this.setChanged(true);
            this.calcRotaShiftBounds();
        });
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

    public Date getFirstBeginn() {
        return firstBeginn.get();
    }

    public void setFirstBeginn(Date firstBeginn) {
        this.firstBeginn.set(firstBeginn);
    }

    public ObjectProperty<Date> firstBeginnProperty() {
        return firstBeginn;
    }

    public Date getLastEnd() {
        return lastEnd.get();
    }

    public void setLastEnd(Date lastEnd) {
        this.lastEnd.set(lastEnd);
    }

    public ObjectProperty<Date> lastEndProperty() {
        return lastEnd;
    }

    private void calcRotaShiftBounds() {
        List<RotaShift> rotaShifts = this.getRotaShifts();
        setFirstBeginn(null);
        setLastEnd(null);
        if (rotaShifts != null) {
            for (int i = 0; i < rotaShifts.size(); i++) {
                if (getFirstBeginn() == null || getFirstBeginn().after(rotaShifts.get(i).getTsBeginn()))
                    setFirstBeginn(rotaShifts.get(i).getTsBeginn());
                if (getLastEnd() == null || getLastEnd().before(rotaShifts.get(i).getTsEnd()))
                    setLastEnd(rotaShifts.get(i).getTsEnd());
            }
        }
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
