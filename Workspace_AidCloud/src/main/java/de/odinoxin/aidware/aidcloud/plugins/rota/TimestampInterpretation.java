package de.odinoxin.aidware.aidcloud.plugins.rota;

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
@XmlType(name = "TimestampInterpretationEntity")
@Entity
@Table(name = "TimestampInterpretation")
public class TimestampInterpretation implements Recordable {

    @Id
    @GeneratedValue
    @XmlElement(name = "id")
    @EntityProperty
    private int id;

    @XmlElement(name = "name")
    @EntityProperty
    private String name;

    public TimestampInterpretation() {

    }

    public TimestampInterpretation(int id) {
        this.id = id;
    }

    public TimestampInterpretation(int id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    @Override
    public Object clone() {
        return new TimestampInterpretation(this.getId(), this.getName());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null
                || obj.getClass() != this.getClass())
            return false;
        TimestampInterpretation other = (TimestampInterpretation) obj;
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
