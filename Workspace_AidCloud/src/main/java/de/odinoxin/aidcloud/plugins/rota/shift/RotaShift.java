package de.odinoxin.aidcloud.plugins.rota.shift;

import de.odinoxin.aidcloud.plugins.Recordable;
import de.odinoxin.aidcloud.plugins.rota.TimestampInterpretation;
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
    private int id;

    @XmlElement(name = "text")
    private String text;

    @XmlElement(name = "tsBeginn")
    private Date tsBeginn;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @XmlElement(name = "beginnInterpretation")
    private TimestampInterpretation beginnInterpretation;

    @XmlElement(name = "tsEnd")
    private Date tsEnd;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @XmlElement(name = "endInterpretation")
    private TimestampInterpretation endInterpretation;

    public RotaShift() {

    }

    public RotaShift(int id) {
        this.id = id;
    }

    public RotaShift(int id, Date tsBeginn, Date tsEnd) {
        super();
        this.id = id;
        this.tsBeginn = tsBeginn;
        this.tsEnd = tsEnd;
    }

    @Override
    public Object clone() {
        return new RotaShift(this.id, this.tsBeginn, this.tsEnd);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null
                || obj.getClass() != this.getClass())
            return false;
        RotaShift rotaShift = (RotaShift) obj;
        return rotaShift.getId() == this.getId()
                && ((rotaShift.getText() == null && this.getText() == null) || (rotaShift.getText() != null && rotaShift.getText().equals(this.getText())))
                && ((rotaShift.getTsBeginn() == null && this.getTsBeginn() == null) || (rotaShift.getTsBeginn() != null && rotaShift.getTsBeginn().equals(this.getTsBeginn())))
                && ((rotaShift.getBeginnInterpretation() == null && this.getBeginnInterpretation() == null) || (rotaShift.getBeginnInterpretation() != null && rotaShift.getBeginnInterpretation().equals(this.getBeginnInterpretation())))
                && ((rotaShift.getTsEnd() == null && this.getTsEnd() == null) || (rotaShift.getTsEnd() != null && rotaShift.getTsEnd().equals(this.getTsEnd())))
                && ((rotaShift.getEndInterpretation() == null && this.getEndInterpretation() == null) || (rotaShift.getEndInterpretation() != null && rotaShift.getEndInterpretation().equals(this.getEndInterpretation())));
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