package de.odinoxin.aidware.aiddesk.plugins.people;

import de.odinoxin.aidware.aiddesk.plugins.RecordItem;
import de.odinoxin.aidware.aiddesk.plugins.addresses.Address;
import de.odinoxin.aidware.aiddesk.plugins.contact.information.ContactInformation;
import de.odinoxin.aidware.aiddesk.plugins.languages.Language;
import de.odinoxin.aidware.aiddesk.plugins.nutritiontype.NutritionType;
import de.odinoxin.aidware.aiddesk.plugins.people.personalsetting.PersonalSetting;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;

import java.util.Hashtable;
import java.util.List;

public class Person extends RecordItem {

    private StringProperty name = new SimpleStringProperty(null, "Name");
    private StringProperty forename = new SimpleStringProperty(null, "Forename");
    private StringProperty code = new SimpleStringProperty(null, "Code");
    private StringProperty pwd = new SimpleStringProperty(null, "Pwd");
    private ObjectProperty<Language> language = new SimpleObjectProperty<>(null, "Language");
    private ObjectProperty<NutritionType> nutritionType = new SimpleObjectProperty<>(null, "NutritionType");
    private ObjectProperty<Address> address = new SimpleObjectProperty<>(null, "Address");
    private ListProperty<ContactInformation> contactInformation = new SimpleListProperty<>(null, "ContactInformation", FXCollections.observableArrayList());
    private ObjectProperty<PersonalSetting> personalSetting = new SimpleObjectProperty<>(null, "PersonalSetting");

    public Person() {
        super();
        this.name.addListener((observable, oldValue, newValue) -> setChanged(true));
        this.forename.addListener((observable, oldValue, newValue) -> setChanged(true));
        this.code.addListener((observable, oldValue, newValue) -> setChanged(true));
        this.pwd.addListener((observable, oldValue, newValue) -> setChanged(true));
        this.language.addListener((observable, oldValue, newValue) -> setChanged(true));
        this.nutritionType.addListener((observable, oldValue, newValue) -> setChanged(true));
        this.address.addListener((observable, oldValue, newValue) -> setChanged(true));
        this.contactInformation.addListener((ListChangeListener.Change<? extends ContactInformation> c) -> setChanged(true));
        this.personalSetting.addListener((observable, oldValue, newValue) -> setChanged(true));
        this.setChanged(false);
    }

    public Person(int id) {
        this();
        this.setId(id);
        this.setChanged(false);
    }

    public Person(int id, String name, String forename, String code, Language language, NutritionType nutritionType, Address address, List<ContactInformation> contactInformation, PersonalSetting personalSetting) {
        this(id);
        this.setName(name);
        this.setForename(forename);
        this.setCode(code);
        this.setLanguage(language);
        this.setNutritionType(nutritionType);
        this.setAddress(address);
        this.setContactInformation(contactInformation);
        this.setPersonalSetting(personalSetting);
        this.setChanged(false);
    }

    @Override
    protected Object clone() {
        return new Person(this.getId(), this.getName(), this.getForename(), this.getCode(), this.getLanguage(), this.getNutritionType(), this.getAddress(), this.getContactInformation(), this.getPersonalSetting());
    }

    public String getName() {
        return name.get();
    }

    public String getForename() {
        return forename.get();
    }

    public String getCode() {
        return code.get();
    }

    public String getPwd() {
        return pwd.get();
    }

    public Language getLanguage() {
        return language.get();
    }

    public NutritionType getNutritionType() {
        return nutritionType.get();
    }

    public Address getAddress() {
        return address.get();
    }

    public List<ContactInformation> getContactInformation() {
        return contactInformation.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setForename(String forename) {
        this.forename.set(forename);
    }

    public void setCode(String code) {
        this.code.set(code);
    }

    public void setPwd(String pwd) {
        this.pwd.set(pwd);
    }

    public void setLanguage(Language language) {
        this.language.set(language);
    }

    public void setNutritionType(NutritionType nutritionType) {
        this.nutritionType.set(nutritionType);
    }

    public void setAddress(Address address) {
        this.address.set(address);
    }

    public void setContactInformation(List<ContactInformation> contactInformation) {
        if (this.contactInformation.get() != null) {
            this.contactInformation.get().clear();
            if (contactInformation != null)
                this.contactInformation.get().addAll(contactInformation);
        } else if (contactInformation != null)
            this.contactInformation.set(FXCollections.observableArrayList(contactInformation));
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty forenameProperty() {
        return forename;
    }

    public StringProperty codeProperty() {
        return code;
    }

    public StringProperty pwdProperty() {
        return pwd;
    }

    public ObjectProperty<Language> languageProperty() {
        return language;
    }

    public ObjectProperty<NutritionType> nutritionTypeProperty() {
        return nutritionType;
    }

    public ObjectProperty<Address> addressProperty() {
        return address;
    }

    public ListProperty<ContactInformation> contactInformationProperty() {
        return contactInformation;
    }

    public PersonalSetting getPersonalSetting() {
        return personalSetting.get();
    }

    public void setPersonalSetting(PersonalSetting personalSetting) {
        this.personalSetting.set(personalSetting);
    }

    public ObjectProperty<PersonalSetting> personalSettingProperty() {
        return personalSetting;
    }

    @Override
    protected Hashtable<String, Property<?>> getProperties() {
        Hashtable<String, Property<?>> properties = new Hashtable<>();
        properties.put(this.name.getName(), this.name);
        properties.put(this.forename.getName(), this.forename);
        properties.put(this.code.getName(), this.code);
        properties.put(this.pwd.getName(), this.pwd);
        properties.put(this.language.getName(), this.language);
        properties.put(this.nutritionType.getName(), this.nutritionType);
        properties.put(this.address.getName(), this.address);
        properties.put(this.contactInformation.getName(), this.contactInformation);
        properties.put(this.personalSetting.getName(), this.personalSetting);
        return properties;
    }
}
