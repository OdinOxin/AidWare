package de.odinoxin.aidware.aidcloud.plugins;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TrackedChangeEntity")
@Entity
@Table(name = "TrackedChange")
public class TrackedChange implements Recordable {

    @Id
    @GeneratedValue
    @XmlElement(name = "id")
    @EntityProperty
    private int id;

    @XmlElement(name = "entityName")
    @EntityProperty
    private String entityName;

    @XmlElement(name = "entityId")
    @EntityProperty
    private int entityId;

    @XmlElement(name = "propertyName")
    @EntityProperty
    private String propertyName;

    @XmlElement(name = "timestamp")
    @EntityProperty
    private Date timestamp;

    @XmlElement(name = "userId")
    @EntityProperty
    private int userId;

    @XmlElement(name = "valueBefore")
    @EntityProperty
    private String valueBefore;

    @XmlElement(name = "valueAfter")
    @EntityProperty
    private String valueAfter;

    public TrackedChange() {

    }

    public TrackedChange(int id) {
        this.id = id;
    }

    public TrackedChange(int id, String entityName, int entityId, String propertyName, Date timestamp, int userId, String valueBefore, String valueAfter) {
        this(id);
        this.entityName = entityName;
        this.entityId = entityId;
        this.propertyName = propertyName;
        this.timestamp = timestamp;
        this.userId = userId;
        this.valueBefore = valueBefore;
        this.valueAfter = valueAfter;
    }

    @Override
    public Object clone() {
        return new TrackedChange(this.getId(), this.getEntityName(), this.getEntityId(), this.getPropertyName(), this.getTimestamp(), this.getUserId(), this.getValueBefore(), this.getValueAfter());
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getValueBefore() {
        return valueBefore;
    }

    public void setValueBefore(String valueBefore) {
        this.valueBefore = valueBefore;
    }

    public String getValueAfter() {
        return valueAfter;
    }

    public void setValueAfter(String valueAfter) {
        this.valueAfter = valueAfter;
    }
}
