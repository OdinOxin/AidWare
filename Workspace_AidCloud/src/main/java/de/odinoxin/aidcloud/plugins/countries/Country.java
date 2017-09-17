package de.odinoxin.aidcloud.plugins.countries;

import de.odinoxin.aidcloud.plugins.EntityProperty;
import de.odinoxin.aidcloud.plugins.Recordable;
import de.odinoxin.aidcloud.plugins.RecordableComparer;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CountryEntity")
@Entity
@Table(name = "Country")
public class Country implements Recordable {

    @Id
    @GeneratedValue
    @XmlElement(name = "id")
    @EntityProperty
    private int id;
    @XmlElement(name = "alpha2")
    @EntityProperty
    private String alpha2;
    @XmlElement(name = "alpha3")
    @EntityProperty
    private String alpha3;
    @XmlElement(name = "name")
    @EntityProperty
    private String name;
    @XmlElement(name = "areaCode")
    @EntityProperty
    private String areaCode;

    public Country() {

    }

    public Country(int id) {
        this();
        this.id = id;
    }

    public Country(int id, String alpha2, String alpha3, String name, String areaCode) {
        this(id);
        this.alpha2 = alpha2;
        this.alpha3 = alpha3;
        this.name = name;
        this.areaCode = areaCode;
    }

    @Override
    public Object clone() {
        return new Country(this.getId(), this.getAlpha2(), this.getAlpha3(), this.getName(), this.getAreaCode());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null
                || obj.getClass() != this.getClass())
            return false;
        Country other = (Country) obj;
        return RecordableComparer.Equals(this.getId(), other.getId())
                && RecordableComparer.Equals(this.getAlpha2(), other.getAlpha2())
                && RecordableComparer.Equals(this.getAlpha3(), other.getAlpha3())
                && RecordableComparer.Equals(this.getName(), other.getName())
                && RecordableComparer.Equals(this.getAreaCode(), other.getAreaCode());
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getAlpha2() {
        return alpha2;
    }

    public void setAlpha2(String alpha2) {
        this.alpha2 = alpha2;
    }

    public String getAlpha3() {
        return alpha3;
    }

    public void setAlpha3(String alpha3) {
        this.alpha3 = alpha3;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
}