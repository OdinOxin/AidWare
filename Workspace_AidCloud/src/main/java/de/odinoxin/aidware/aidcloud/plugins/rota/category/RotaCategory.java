package de.odinoxin.aidware.aidcloud.plugins.rota.category;

import de.odinoxin.aidware.aidcloud.plugins.EntityProperty;
import de.odinoxin.aidware.aidcloud.plugins.Recordable;
import de.odinoxin.aidware.aidcloud.plugins.RecordableComparer;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RotaCategoryEntity")
@Entity
@Table(name = "RotaCategory")
public class RotaCategory implements Recordable {

    @Id
    @GeneratedValue
    @XmlElement(name = "id")
    @EntityProperty
    private int id;

    @XmlElement(name = "name")
    @EntityProperty
    private String name;

    public RotaCategory() {

    }

    public RotaCategory(int id) {
        this.id = id;
    }

    public RotaCategory(int id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    @Override
    public Object clone() {
        return new RotaCategory(this.getId(), this.getName());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null
                || obj.getClass() != this.getClass())
            return false;
        RotaCategory other = (RotaCategory) obj;
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