package de.odinoxin.aidware.aidcloud.plugins.person.personalsetting;

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
@XmlType(name = "PersonalSettingEntity")
@Entity
@Table(name = "PersonalSetting")
public class PersonalSetting implements Recordable {

    @Id
    @GeneratedValue
    @XmlElement(name = "id")
    @EntityProperty
    private int id;

    @XmlElement(name = "bgColor")
    @EntityProperty
    private String bgColor;

    @XmlElement(name = "bgColorPlugin")
    @EntityProperty
    private String bgColorPlugin;

    public PersonalSetting() {

    }

    public PersonalSetting(int id) {
        this();
        this.setId(id);
    }

    public PersonalSetting(int id, String bgColor, String bgColorPlugin) {
        this(id);
        this.bgColor = bgColor;
        this.bgColorPlugin = bgColorPlugin;
    }

    @Override
    public Object clone() {
        return new PersonalSetting(this.getId(), this.getBgColor(), this.getBgColorPlugin());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        PersonalSetting other = (PersonalSetting) obj;
        return RecordableComparer.Equals(this.getId(), other.getId())
                && RecordableComparer.Equals(this.getBgColor(), other.getBgColor())
                && RecordableComparer.Equals(this.getBgColorPlugin(), other.getBgColorPlugin());
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    public String getBgColorPlugin() {
        return bgColorPlugin;
    }

    public void setBgColorPlugin(String bgColorPlugin) {
        this.bgColorPlugin = bgColorPlugin;
    }
}

