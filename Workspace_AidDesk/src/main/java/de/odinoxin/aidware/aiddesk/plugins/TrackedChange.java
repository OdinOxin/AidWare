package de.odinoxin.aidware.aiddesk.plugins;

import javafx.beans.property.*;

import java.util.Date;
import java.util.Hashtable;

public class TrackedChange extends RecordItem {

    private StringProperty entityName = new SimpleStringProperty(null, "EntityName");
    private IntegerProperty entityId = new SimpleIntegerProperty(null, "EntityId");
    private StringProperty propertyName = new SimpleStringProperty(null, "PropertyName");
    private ObjectProperty<Date> timestamp = new SimpleObjectProperty<>(null, "Timestamp");
    private IntegerProperty userId = new SimpleIntegerProperty(null, "UserId");
    private StringProperty valueBefore = new SimpleStringProperty(null, "ValueBefore");
    private StringProperty valueAfter = new SimpleStringProperty(null, "ValueAfter");

    public TrackedChange() {
        super();
        this.entityName.addListener((observable, oldValue, newValue) -> this.setChanged(true));
        this.setChanged(false);
    }

    public TrackedChange(int id) {
        this();
        this.setId(id);
        this.setChanged(false);
    }

    public TrackedChange(int id, String entityName, int entityId, String propertyName, Date timestamp, int userId, String valueBefore, String valueAfter) {
        this(id);
        this.setEntityName(entityName);
        this.setEntityId(entityId);
        this.setPropertyName(propertyName);
        this.setTimestamp(timestamp);
        this.setUserId(userId);
        this.setValueBefore(valueBefore);
        this.setValueAfter(valueAfter);
        this.setChanged(false);
    }

    @Override
    protected Object clone() {
        return new TrackedChange(this.getId(), this.getEntityName(), this.getEntityId(), this.getPropertyName(), this.getTimestamp(), this.getUserId(), this.getValueBefore(), this.getValueAfter());
    }

    public String getEntityName() {
        return entityName.get();
    }

    public void setEntityName(String entityName) {
        this.entityName.set(entityName);
    }

    public StringProperty entityNameProperty() {
        return entityName;
    }

    public int getEntityId() {
        return entityId.get();
    }

    public void setEntityId(int entityId) {
        this.entityId.set(entityId);
    }

    public IntegerProperty entityIdProperty() {
        return entityId;
    }

    public String getPropertyName() {
        return propertyName.get();
    }

    public void setPropertyName(String propertyName) {
        this.propertyName.set(propertyName);
    }

    public StringProperty propertyNameProperty() {
        return propertyName;
    }

    public Date getTimestamp() {
        return timestamp.get();
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp.set(timestamp);
    }

    public ObjectProperty<Date> timestampProperty() {
        return timestamp;
    }

    public int getUserId() {
        return userId.get();
    }

    public void setUserId(int userId) {
        this.userId.set(userId);
    }

    public IntegerProperty userIdProperty() {
        return userId;
    }

    public String getValueBefore() {
        return valueBefore.get();
    }

    public void setValueBefore(String valueBefore) {
        this.valueBefore.set(valueBefore);
    }

    public StringProperty valueBeforeProperty() {
        return valueBefore;
    }

    public String getValueAfter() {
        return valueAfter.get();
    }

    public void setValueAfter(String valueAfter) {
        this.valueAfter.set(valueAfter);
    }

    public StringProperty valueAfterProperty() {
        return valueAfter;
    }

    @Override
    protected Hashtable<String, Property<?>> getProperties() {
        Hashtable<String, Property<?>> properties = new Hashtable<>();
        properties.put(this.entityName.getName(), this.entityName);
        properties.put(this.entityId.getName(), this.entityId);
        properties.put(this.propertyName.getName(), this.propertyName);
        properties.put(this.timestamp.getName(), this.timestamp);
        properties.put(this.userId.getName(), this.userId);
        properties.put(this.valueBefore.getName(), this.valueBefore);
        properties.put(this.valueAfter.getName(), this.valueAfter);
        return properties;
    }
}
