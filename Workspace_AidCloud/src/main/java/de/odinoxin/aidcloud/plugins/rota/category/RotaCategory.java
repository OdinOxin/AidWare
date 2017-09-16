package de.odinoxin.aidcloud.plugins.rota.category;

import de.odinoxin.aidcloud.plugins.EntityProperty;
import de.odinoxin.aidcloud.plugins.Recordable;

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
        return new RotaCategory(this.id, this.name);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null
                || obj.getClass() != this.getClass())
            return false;
        RotaCategory RotaCategory = (RotaCategory) obj;
        return RotaCategory.getId() == this.getId()
                && ((RotaCategory.getName() == null && this.getName() == null) || (RotaCategory.getName() != null && RotaCategory.getName().equals(this.getName())));
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