package de.odinoxin.aidcloud.plugins.people;

import de.odinoxin.aidcloud.plugins.Recordable;
import de.odinoxin.aidcloud.plugins.addresses.Address;
import de.odinoxin.aidcloud.plugins.contact.information.ContactInformation;
import de.odinoxin.aidcloud.plugins.languages.Language;
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
@XmlType(name = "PersonEntity")
@FetchProfiles(
        @FetchProfile(fetchOverrides = {
                @FetchProfile.FetchOverride(association = "contactInformation", entity = Person.class, mode = FetchMode.JOIN),
        }, name = "JOIN_ContactInformation")
)
@Entity
@Table(name = "Person")
public class Person implements Recordable {

    @Id
    @GeneratedValue
    @XmlElement(name = "id")
    private int id;

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "forename")
    private String forename;

    @XmlElement(name = "code")
    private String code;

    @XmlElement(name = "pwd")
    private String pwd;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @XmlElement(name = "language")
    private Language language;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @XmlElement(name = "address")
    private Address address;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @XmlElement(name = "contactInformation")
    private List<ContactInformation> contactInformation = new ArrayList<>();

    public Person() {

    }

    public Person(int id) {
        this();
        this.id = id;
    }

    public Person(int id, String name, String forename, String code, Language language, Address address, List<ContactInformation> contactInformation) {
        this(id);
        this.name = name;
        this.forename = forename;
        this.code = code;
        this.language = language;
        this.address = address;
        this.contactInformation = contactInformation;
    }

    @Override
    public Object clone() {
        return new Person(this.getId(), this.getName(), this.getForename(), this.getCode(), this.getLanguage(), this.getAddress(), this.getContactInformation());
//        try {
//            Person clone = (Person) super.clone();
//            if (clone.getLanguage() != null)
//                clone.setLanguage((Language) clone.getLanguage().clone());
//            if (clone.getAddress() != null)
//                clone.setAddress((Address) clone.getAddress().clone());
//            if (clone.getContactInformation() != null) {
//                List<ContactInformation> newList = new ArrayList<>();
//                for (ContactInformation ci : clone.getContactInformation())
//                    newList.add((ContactInformation) ci.clone());
//                clone.setContactInformation(newList);
//            }
//            return clone;
//        } catch (CloneNotSupportedException ex) {
//            ex.printStackTrace();
//        }
//        return null;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null
                || obj.getClass() != this.getClass())
            return false;
        Person person = (Person) obj;
        if (person.getId() == this.getId()
                && ((person.getName() == null && this.getName() == null) || (person.getName() != null && person.getName().equals(this.getName())))
                && ((person.getForename() == null && this.getForename() == null) || (person.getForename() != null && person.getForename().equals(this.getForename())))
                && ((person.getCode() == null && this.getCode() == null) || (person.getCode() != null && person.getCode().equals(this.getCode())))
                && ((person.getLanguage() == null && this.getLanguage() == null) || (person.getLanguage() != null && person.getLanguage().equals(this.getLanguage())))
                && ((person.getAddress() == null && this.getAddress() == null) || (person.getAddress() != null && person.getAddress().equals(this.getAddress())))) {
            if (person.getContactInformation() != null
                    && this.getContactInformation() != null
                    && person.getContactInformation().size() == this.getContactInformation().size()) {
                for (int i = 0; i < this.getContactInformation().size(); i++)
                    if (!person.getContactInformation().get(i).equals(this.getContactInformation().get(i)))
                        return false;
                return true;
            } else if (person.getContactInformation() == null
                    && this.getContactInformation() == null)
                return true;
        }
        return false;
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

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Language getLanguage() {
        if (Hibernate.isInitialized(language))
            return language;
        return null;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Address getAddress() {
        if (Hibernate.isInitialized(address))
            return address;
        return null;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<ContactInformation> getContactInformation() {
        if (Hibernate.isInitialized(contactInformation))
            return contactInformation;
        return null;
    }

    public void setContactInformation(List<ContactInformation> contactInformation) {
        this.contactInformation = contactInformation;
    }
}
