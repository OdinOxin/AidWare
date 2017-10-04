package de.odinoxin.aidcloud.plugins.person;

import de.odinoxin.aidcloud.plugins.EntityProperty;
import de.odinoxin.aidcloud.plugins.Recordable;
import de.odinoxin.aidcloud.plugins.RecordableComparer;
import de.odinoxin.aidcloud.plugins.address.Address;
import de.odinoxin.aidcloud.plugins.contact.information.ContactInformation;
import de.odinoxin.aidcloud.plugins.language.Language;
import de.odinoxin.aidcloud.plugins.nutritiontype.NutritionType;
import de.odinoxin.aidcloud.plugins.person.personalsetting.PersonalSetting;
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
    @EntityProperty
    private int id;

    @XmlElement(name = "name")
    @EntityProperty
    private String name;

    @XmlElement(name = "forename")
    @EntityProperty
    private String forename;

    @XmlElement(name = "code")
    @EntityProperty
    private String code;

    @XmlElement(name = "pwd")
    @EntityProperty
    private String pwd;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @XmlElement(name = "language")
    @EntityProperty
    private Language language;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @XmlElement(name = "address")

    private Address address;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @XmlElement(name = "contactInformation")
    @EntityProperty
    private List<ContactInformation> contactInformation = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.PERSIST)
    @XmlElement(name = "nutritionType")
    @EntityProperty
    private NutritionType nutritionType;

    @OneToOne(cascade = CascadeType.PERSIST)
    @XmlElement(name = "personalSetting")
    @EntityProperty
    private PersonalSetting personalSetting;

    public Person() {

    }

    public Person(int id) {
        this();
        this.id = id;
    }

    public Person(int id, String name, String forename, String code, Language language, Address address, List<ContactInformation> contactInformation, NutritionType nutritionType, PersonalSetting personalSetting) {
        this(id);
        this.name = name;
        this.forename = forename;
        this.code = code;
        this.language = language;
        this.address = address;
        this.contactInformation = contactInformation;
        this.nutritionType = nutritionType;
        this.personalSetting = personalSetting;
    }

    @Override
    public Object clone() {
        return new Person(this.getId(), this.getName(), this.getForename(), this.getCode(), this.getLanguage(), this.getAddress(), this.getContactInformation(), this.getNutritionType(), this.getPersonalSetting());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        Person other = (Person) obj;
        return RecordableComparer.Equals(this.getId(), other.getId())
                && RecordableComparer.Equals(this.getName(), other.getName())
                && RecordableComparer.Equals(this.getForename(), other.getForename())
                && RecordableComparer.Equals(this.getCode(), other.getCode())
                && RecordableComparer.Equals(this.getLanguage(), other.getLanguage())
                && RecordableComparer.Equals(this.getAddress(), other.getAddress())
                && RecordableComparer.Equals(this.getNutritionType(), other.getNutritionType())
                && RecordableComparer.Equals(this.getContactInformation(), other.getContactInformation())
                && RecordableComparer.Equals(this.getPersonalSetting(), other.getPersonalSetting());
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

    public NutritionType getNutritionType() {
        if (Hibernate.isInitialized(nutritionType))
            return nutritionType;
        return null;
    }

    public void setNutritionType(NutritionType nutritionType) {
        this.nutritionType = nutritionType;
    }

    public PersonalSetting getPersonalSetting() {
        return personalSetting;
    }

    public void setPersonalSetting(PersonalSetting personalSetting) {
        this.personalSetting = personalSetting;
    }
}
