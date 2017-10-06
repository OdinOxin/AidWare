package de.odinoxin.aidware.aidcloud.plugins.contact.type;

import de.odinoxin.aidware.aidcloud.recordable.EntityProperty;
import de.odinoxin.aidware.aidcloud.recordable.Recordable;
import de.odinoxin.aidware.aidcloud.recordable.RecordableComparer;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContactTypeEntity")
@Entity
@Table(name = "ContactType")
public class ContactType implements Recordable {

    @Id
    @GeneratedValue
    @XmlElement(name = "id")
    @EntityProperty
    private int id;
    @XmlElement(name = "name")
    @EntityProperty
    private String name;
    @XmlElement(name = "code")
    @EntityProperty
    private String code;
    @XmlElement(name = "regex")
    @EntityProperty
    private String regex;

    public ContactType() {

    }

    public ContactType(int id) {
        this();
        this.id = id;
    }

    public ContactType(int id, String name, String code, String regex) {
        this(id);
        this.name = name;
        this.code = code;
        this.regex = regex;
    }

    @Override
    public Object clone() {
        return new ContactType(this.getId(), this.getName(), this.getCode(), this.getRegex());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null
                || obj.getClass() != this.getClass())
            return false;
        ContactType other = (ContactType) obj;
        return RecordableComparer.Equals(this.getId(), other.getId())
                && RecordableComparer.Equals(this.getName(), other.getName())
                && RecordableComparer.Equals(this.getCode(), other.getCode())
                && RecordableComparer.Equals(this.getRegex(), other.getRegex());
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }
}
