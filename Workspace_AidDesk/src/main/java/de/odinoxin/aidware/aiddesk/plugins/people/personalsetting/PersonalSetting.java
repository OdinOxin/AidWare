package de.odinoxin.aidware.aiddesk.plugins.people.personalsetting;

import de.odinoxin.aidware.aiddesk.plugins.RecordItem;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Hashtable;

public class PersonalSetting extends RecordItem {
    private StringProperty bgColor = new SimpleStringProperty(null, "bgColor");
    private StringProperty bgColorPlugin = new SimpleStringProperty(null, "bgColorPlugin");

    public PersonalSetting() {
        super();
        this.bgColor.addListener((observable, oldValue, newValue) -> setChanged(true));
        this.bgColorPlugin.addListener((observable, oldValue, newValue) -> setChanged(true));
    }

    public PersonalSetting(int id) {
        this();
        this.setId(id);
        this.setChanged(false);
    }

    public PersonalSetting(int id, String bgColor, String bgColorPlugin) {
        this(id);
        this.setBgColor(bgColor);
        this.setBgColorPlugin(bgColorPlugin);
        this.setChanged(false);
    }

    @Override
    protected Object clone() {
        return new PersonalSetting(this.getId(), this.getBgColor(), this.getBgColorPlugin());
    }

    public String getBgColor() {
        return bgColor.get();
    }

    public void setBgColor(String bgColor) {
        this.bgColor.set(bgColor);
    }

    public StringProperty bgColorProperty() {
        return bgColor;
    }

    public String getBgColorPlugin() {
        return bgColorPlugin.get();
    }

    public void setBgColorPlugin(String bgColorPlugin) {
        this.bgColorPlugin.set(bgColorPlugin);
    }

    public StringProperty bgColorPluginProperty() {
        return bgColorPlugin;
    }

    @Override
    protected Hashtable<String, Property<?>> getProperties() {
        Hashtable<String, Property<?>> properties = new Hashtable<>();
        properties.put(this.bgColor.getName(), this.bgColor);
        properties.put(this.bgColorPlugin.getName(), this.bgColorPlugin);
        return properties;
    }
}
