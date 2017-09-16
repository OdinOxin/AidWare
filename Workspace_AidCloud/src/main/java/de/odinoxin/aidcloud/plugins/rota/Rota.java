package de.odinoxin.aidcloud.plugins.rota;

import de.odinoxin.aidcloud.plugins.EntityProperty;
import de.odinoxin.aidcloud.plugins.Recordable;
import de.odinoxin.aidcloud.plugins.rota.category.RotaCategory;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RotaEntity")
@Entity
@Table(name = "Rota")
public class Rota implements Recordable {

    @Id
    @GeneratedValue
    @XmlElement(name = "id")
    @EntityProperty
    private int id;

    @XmlElement(name = "title")
    @EntityProperty
    private String title;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @XmlElement(name = "category")
    @EntityProperty
    private RotaCategory category;

    public Rota() {

    }

    public Rota(int id) {
        this.id = id;
    }

    public Rota(int id, String title, RotaCategory category) {
        super();
        this.id = id;
        this.title = title;
        this.category = category;
    }

    @Override
    public Object clone() {
        return new Rota(this.id, this.title, this.category);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null
                || obj.getClass() != this.getClass())
            return false;
        Rota rota = (Rota) obj;
        return rota.getId() == this.getId()
                && ((rota.getTitle() == null && this.getTitle() == null) || (rota.getTitle() != null && rota.getTitle().equals(this.getTitle())))
                && ((rota.getCategory() == null && this.getCategory() == null) || (rota.getCategory() != null && rota.getCategory().equals(this.getCategory())));
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public RotaCategory getCategory() {
        if (Hibernate.isInitialized(category))
            return category;
        return null;
    }

    public void setCategory(RotaCategory category) {
        this.category = category;
    }
}
