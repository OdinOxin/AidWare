package de.odinoxin.aidcloud.plugins.rota;

import de.odinoxin.aidcloud.plugins.EntityProperty;
import de.odinoxin.aidcloud.plugins.Recordable;
import de.odinoxin.aidcloud.plugins.RecordableComparer;
import de.odinoxin.aidcloud.plugins.rota.category.RotaCategory;
import de.odinoxin.aidcloud.plugins.rota.shift.RotaShift;
import org.hibernate.Hibernate;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.FetchProfile;
import org.hibernate.annotations.FetchProfiles;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RotaEntity")
@FetchProfiles(
        @FetchProfile(fetchOverrides = {
                @FetchProfile.FetchOverride(association = "rotaShifts", entity = Rota.class, mode = FetchMode.JOIN),
        }, name = "JOIN_RotaShift")
)
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
    @XmlElement(name = "rotaCategory")
    @EntityProperty
    private RotaCategory rotaCategory;

    @OneToMany(cascade = CascadeType.PERSIST)
    @XmlElement(name = "rotaShifts")
    @EntityProperty
    private List<RotaShift> rotaShifts = new ArrayList<>();

    public Rota() {

    }

    public Rota(int id) {
        this.id = id;
    }

    public Rota(int id, String title, RotaCategory rotaCategory, List<RotaShift> rotaShifts) {
        super();
        this.id = id;
        this.title = title;
        this.rotaCategory = rotaCategory;
        this.rotaShifts = rotaShifts;
    }

    @Override
    public Object clone() {
        return new Rota(this.getId(), this.getTitle(), this.getRotaCategory(), this.getRotaShifts());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        Rota other = (Rota) obj;
        return RecordableComparer.Equals(this.getId(), other.getId())
                && RecordableComparer.Equals(this.getTitle(), other.getTitle())
                && RecordableComparer.Equals(this.getRotaCategory(), other.getRotaCategory())
                && RecordableComparer.Equals(this.getRotaShifts(), other.getRotaShifts());
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

    public RotaCategory getRotaCategory() {
        if (Hibernate.isInitialized(rotaCategory))
            return rotaCategory;
        return null;
    }

    public void setRotaCategory(RotaCategory rotaCategory) {
        this.rotaCategory = rotaCategory;
    }

    public List<RotaShift> getRotaShifts() {
        if (Hibernate.isInitialized(rotaShifts))
            return rotaShifts;
        return null;
    }

    public void setRotaShifts(List<RotaShift> rotaShifts) {
        this.rotaShifts = rotaShifts;
    }
}
