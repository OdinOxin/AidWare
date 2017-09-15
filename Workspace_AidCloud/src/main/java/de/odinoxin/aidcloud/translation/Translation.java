package de.odinoxin.aidcloud.translation;

import de.odinoxin.aidcloud.plugins.Recordable;
import de.odinoxin.aidcloud.plugins.languages.Language;

import javax.persistence.*;

@Entity
@Table(name = "Translation")
public class Translation implements Recordable {

    @Id
    @GeneratedValue
    private int id;
    private String sys;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Language lng;
    private String lngText;

    public Translation() {

    }

    public Translation(int id) {
        this();
        this.id = id;
    }

    public Translation(int id, String sys, Language lng, String lngTxt) {
        this(id);
        this.sys = sys;
        this.lng = lng;
        this.lngText = lngTxt;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getSys() {
        return sys;
    }

    public void setSys(String sys) {
        this.sys = sys;
    }

    public Language getLng() {
        return lng;
    }

    public void setLng(Language lng) {
        this.lng = lng;
    }

    public String getLngText() {
        return lngText;
    }

    public void setLngText(String lngText) {
        this.lngText = lngText;
    }

    @Override
    public Object clone() {
        return null;
    }
}
