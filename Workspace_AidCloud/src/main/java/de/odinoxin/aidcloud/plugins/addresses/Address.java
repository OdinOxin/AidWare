package de.odinoxin.aidcloud.plugins.addresses;

import de.odinoxin.aidcloud.plugins.EntityProperty;
import de.odinoxin.aidcloud.plugins.Recordable;
import de.odinoxin.aidcloud.plugins.countries.Country;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AddressEntity")
@Entity
@Table(name = "Address")
public class Address implements Recordable {

    @Id
    @GeneratedValue
    @XmlElement(name = "id")
    @EntityProperty
    private int id;
    @XmlElement(name = "street")
    @EntityProperty
    private String street;
    @XmlElement(name = "hsNo")
    @EntityProperty
    private String hsNo;
    @XmlElement(name = "zip")
    @EntityProperty
    private String zip;
    @XmlElement(name = "city")
    @EntityProperty
    private String city;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @XmlElement(name = "country")
    @EntityProperty
    private Country country;

    public Address() {

    }

    public Address(int id) {
        this();
        this.id = id;
    }

    public Address(int id, String street, String hsNo, String zip, String city, Country country) {
        this(id);
        this.street = street;
        this.hsNo = hsNo;
        this.zip = zip;
        this.city = city;
        this.country = country;
    }

    @Override
    public Object clone() {
        try {
            Address clone = (Address) super.clone();
            if (clone.getCountry() != null)
                clone.setCountry((Country) clone.getCountry().clone());
            return clone;
        } catch (CloneNotSupportedException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null
                || obj.getClass() != this.getClass())
            return false;
        Address address = (Address) obj;
        return address.getId() == this.getId()
                && ((address.getStreet() == null && this.getStreet() == null) || (address.getStreet() != null && address.getStreet().equals(this.getStreet())))
                && ((address.getHsNo() == null && this.getHsNo() == null) || (address.getHsNo() != null && address.getHsNo().equals(this.getHsNo())))
                && ((address.getZip() == null && this.getZip() == null) || (address.getZip() != null && address.getZip().equals(this.getZip())))
                && ((address.getCity() == null && this.getCity() == null) || (address.getCity() != null && address.getCity().equals(this.getCity())))
                && ((address.getCountry() == null && this.getCountry() == null) || (address.getCountry() != null && this.getCountry() != null && address.getCountry().getId() == this.getCountry().getId()));
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHsNo() {
        return hsNo;
    }

    public void setHsNo(String hsNo) {
        this.hsNo = hsNo;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Country getCountry() {
        if (Hibernate.isInitialized(country))
            return country;
        return null;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
