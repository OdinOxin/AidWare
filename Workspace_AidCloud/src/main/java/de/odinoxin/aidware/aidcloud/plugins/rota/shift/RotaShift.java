package de.odinoxin.aidware.aidcloud.plugins.rota.shift;

import de.odinoxin.aidware.aidcloud.recordable.EntityProperty;
import de.odinoxin.aidware.aidcloud.recordable.Recordable;
import de.odinoxin.aidware.aidcloud.recordable.RecordableComparer;
import de.odinoxin.aidware.aidcloud.plugins.rota.TimestampInterpretation;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RotaShiftEntity")
@Entity
@Table(name = "RotaShift")
public class RotaShift implements Recordable {

    @Id
    @GeneratedValue
    @XmlElement(name = "id")
    @EntityProperty
    private int id;

    @XmlElement(name = "text")
    @EntityProperty
    private String text;

    @XmlElement(name = "tsBeginn")
    @EntityProperty
    private Date tsBeginn;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @XmlElement(name = "beginnInterpretation")
    @EntityProperty
    private TimestampInterpretation beginnInterpretation;

    @XmlElement(name = "tsEnd")
    @EntityProperty
    private Date tsEnd;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @XmlElement(name = "endInterpretation")
    @EntityProperty
    private TimestampInterpretation endInterpretation;

    public RotaShift() {

    }

    public RotaShift(int id) {
        this.id = id;
    }

    public RotaShift(int id, Date tsBeginn, TimestampInterpretation beginnInterpretation, Date tsEnd, TimestampInterpretation endInterpretation) {
        super();
        this.id = id;
        this.tsBeginn = tsBeginn;
        this.beginnInterpretation = beginnInterpretation;
        this.tsEnd = tsEnd;
        this.endInterpretation = endInterpretation;
    }

    @Override
    public Object clone() {
        return new RotaShift(this.getId(), this.getTsBeginn(), this.getBeginnInterpretation(), this.getTsEnd(), this.getEndInterpretation());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null
                || obj.getClass() != this.getClass())
            return false;
        RotaShift other = (RotaShift) obj;
        return RecordableComparer.Equals(this.getId(), other.getId())
                && RecordableComparer.Equals(this.getText(), other.getText())
                && RecordableComparer.Equals(this.getTsBeginn(), other.getTsBeginn())
                && RecordableComparer.Equals(this.getBeginnInterpretation(), other.getBeginnInterpretation())
                && RecordableComparer.Equals(this.getTsEnd(), other.getTsEnd())
                && RecordableComparer.Equals(this.getEndInterpretation(), other.getEndInterpretation());
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getTsBeginn() {
        return tsBeginn;
    }

    public void setTsBeginn(Date tsBeginn) {
        this.tsBeginn = tsBeginn;
    }

    public TimestampInterpretation getBeginnInterpretation() {
        if (Hibernate.isInitialized(beginnInterpretation))
            return beginnInterpretation;
        return null;
    }

    public void setBeginnInterpretation(TimestampInterpretation beginnInterpretation) {
        this.beginnInterpretation = beginnInterpretation;
    }

    public Date getTsEnd() {
        return tsEnd;
    }

    public void setTsEnd(Date tsEnd) {
        this.tsEnd = tsEnd;
    }

    public TimestampInterpretation getEndInterpretation() {
        if (Hibernate.isInitialized(endInterpretation))
            return endInterpretation;
        return null;
    }

    public void setEndInterpretation(TimestampInterpretation endInterpretation) {
        this.endInterpretation = endInterpretation;
    }
}