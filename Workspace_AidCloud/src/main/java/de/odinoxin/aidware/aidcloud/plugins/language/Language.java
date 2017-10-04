package de.odinoxin.aidware.aidcloud.plugins.language;

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
@XmlType(name = "LanguageEntity")
@Entity
@Table(name = "Language")
public class Language implements Recordable {

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

    public Language() {

    }

    public Language(int id) {
        this();
        this.id = id;
    }

    public Language(int id, String name, String code) {
        this(id);
        this.name = name;
        this.code = code;
    }

    @Override
    public Object clone() {
        return new Language(this.getId(), this.getName(), this.getCode());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null
                || obj.getClass() != this.getClass())
            return false;
        Language other = (Language) obj;
        return RecordableComparer.Equals(this.getId(), other.getId())
                && RecordableComparer.Equals(this.getName(), other.getName())
                && RecordableComparer.Equals(this.getCode(), other.getCode());
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
}
