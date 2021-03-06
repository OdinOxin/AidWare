package de.odinoxin.aidware.aidcloud.plugins.nutritiontype;

import de.odinoxin.aidware.aidcloud.recordable.EntityProperty;
import de.odinoxin.aidware.aidcloud.recordable.Recordable;
import de.odinoxin.aidware.aidcloud.recordable.RecordableComparer;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NutritionTypeEntity")
@Entity
@Table(name = "NutritionType")
public class NutritionType implements Recordable {

    @Id
    @GeneratedValue
    @XmlElement(name = "id")
    @EntityProperty
    private int id;

    @XmlElement(name = "name")
    @EntityProperty
    private String name;

    public NutritionType() {

    }

    public NutritionType(int id) {
        this.id = id;
    }

    public NutritionType(int id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    @Override
    public Object clone() {
        return new NutritionType(this.getId(), this.getName());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null
                || obj.getClass() != this.getClass())
            return false;
        NutritionType other = (NutritionType) obj;
        return RecordableComparer.Equals(this.getId(), other.getId())
                && RecordableComparer.Equals(this.getName(), other.getName());
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
