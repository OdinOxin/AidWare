package de.odinoxin.aiddesk.plugins;

import javafx.beans.property.*;

import java.util.Hashtable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Base class for RecordItems.
 *
 * @param <T> Class of the related service entity class.
 */
public abstract class RecordItem<T> implements Cloneable {

    /**
     * ID of the RecordItem.
     */
    private IntegerProperty id = new SimpleIntegerProperty(this, "id", 0);
    /**
     * Property to indicate, whether any property changed.
     */
    private BooleanProperty changed = new SimpleBooleanProperty(this, "changed", false);

    public RecordItem() {
        idProperty().addListener((observable, oldValue, newValue) -> setChanged(true));
    }

    /**
     * Clones the RecordItem
     *
     * @return a clone of the RecordItem
     */
    @Override
    protected abstract Object clone();

    public int getId() {
        return id.get();
    }

    public boolean isChanged() {
        return changed.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public void setChanged(boolean changed) {
        this.changed.set(changed);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public BooleanProperty changedProperty() {
        return changed;
    }

    /**
     * Convertes the RecordItem into the related service entity class.
     *
     * @return the converted entity
     */
    public abstract T toEntity();

    protected abstract Hashtable<String, ReadOnlyProperty<?>> getProperties();

    public List<String> getDifferentPropertyNames(RecordItem<?> other) {
        if (other == null || !this.getClass().getName().equals(other.getClass().getName()) || this.getId() != other.getId())
            return null;
        Hashtable<String, ReadOnlyProperty<?>> properties = getProperties();
        Hashtable<String, ReadOnlyProperty<?>> otherProperties = other.getProperties();
        return properties.keySet().stream().filter(key ->
        {
            boolean notNull = properties.get(key).getValue() != null && otherProperties.get(key).getValue() != null;
            if (notNull) {
                if (properties.get(key).getValue() instanceof RecordItem<?>)
                    return ((RecordItem<?>) properties.get(key).getValue()).getId() != ((RecordItem<?>) otherProperties.get(key).getValue()).getId();
                return !properties.get(key).getValue().equals(otherProperties.get(key).getValue());
            } else
                return (properties.get(key).getValue() == null && otherProperties.get(key).getValue() != null)
                        || (properties.get(key).getValue() != null && otherProperties.get(key).getValue() == null);
        }).collect(Collectors.toList());
    }
}
