package de.odinoxin.aidcloud.plugins.dietform;

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
@XmlType(name = "DietFormEntity")
@Entity
@Table(name = "DietForm")
public class DietForm implements Recordable {

    @Id
    @GeneratedValue
    @XmlElement(name = "id")
    private int id;

    @XmlElement(name = "name")
    private String name;

    public DietForm() {

    }

    public DietForm(int id) {
        this.id = id;
    }

    public DietForm(int id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    @Override
    public Object clone() {
        return new DietForm(this.id, this.name);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null
                || obj.getClass() != this.getClass())
            return false;
        DietForm dietForm = (DietForm) obj;
        return dietForm.getId() == this.getId()
                && ((dietForm.getName() == null && this.getName() == null) || (dietForm.getName() != null && dietForm.getName().equals(this.getName())));
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
