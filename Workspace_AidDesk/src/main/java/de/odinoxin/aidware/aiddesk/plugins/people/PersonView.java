package de.odinoxin.aidware.aiddesk.plugins.people;

import de.odinoxin.aidware.aidcloud.provider.AddressProvider;
import de.odinoxin.aidware.aidcloud.provider.ContactInformationProvider;
import de.odinoxin.aidware.aidcloud.provider.LanguageProvider;
import de.odinoxin.aidware.aidcloud.provider.NutritionTypeProvider;
import de.odinoxin.aidware.aiddesk.controls.MergeablePane;
import de.odinoxin.aidware.aiddesk.controls.refbox.RefBox;
import de.odinoxin.aidware.aiddesk.controls.reflist.RefList;
import de.odinoxin.aidware.aiddesk.controls.translateable.Button;
import de.odinoxin.aidware.aiddesk.plugins.Plugin;
import de.odinoxin.aidware.aiddesk.plugins.RecordView;
import de.odinoxin.aidware.aiddesk.plugins.addresses.Address;
import de.odinoxin.aidware.aiddesk.plugins.contact.information.ContactInformation;
import de.odinoxin.aidware.aiddesk.plugins.languages.Language;
import de.odinoxin.aidware.aiddesk.plugins.nutritiontype.NutritionType;
import de.odinoxin.aidware.aiddesk.plugins.people.personalsetting.PersonalSettingView;
import javafx.scene.control.TextField;

public class PersonView extends RecordView<Person> {

    private TextField txfForename;
    private TextField txfName;
    private TextField txfCode;
    private Button btnPwd;
    private RefBox<Language> refBoxLanguage;
    private RefBox<NutritionType> refBoxNutritionType;
    private RefBox<Address> refBoxAddress;
    private RefList<ContactInformation> refListContactInformation;
    private PersonalSettingView icdPersonalSetting;

    PersonView(PersonEditor editor) {
        this(null, editor);
    }

    PersonView(Person person, PersonEditor editor) {
        super(person, "/plugins/personview.fxml");

        this.txfName = (TextField) this.root.lookup("#txfName");
        this.txfForename = (TextField) this.root.lookup("#txfForename");
        this.txfCode = (TextField) this.root.lookup("#txfCode");
        this.btnPwd = (Button) this.root.lookup("#btnPwd");
        if (editor != null)
            this.btnPwd.setOnAction(ev -> new PwdEditor(editor));
        else
            this.btnPwd.setDisable(true);
        Plugin.setButtonEnter(this.btnPwd);
        this.refBoxLanguage = (RefBox<Language>) this.root.lookup("#refBoxLanguage");
        this.refBoxLanguage.setProvider(new LanguageProvider());
        this.refBoxNutritionType = (RefBox<NutritionType>) this.root.lookup("#refBoxNutritionType");
        this.refBoxNutritionType.setProvider(new NutritionTypeProvider());
        this.refBoxAddress = (RefBox<Address>) this.root.lookup("#refBoxAddress");
        this.refBoxAddress.setProvider(new AddressProvider());
        this.refListContactInformation = (RefList<ContactInformation>) this.root.lookup("#refListContactInformation");
        this.refListContactInformation.setProvider(new ContactInformationProvider());
        this.icdPersonalSetting = (PersonalSettingView) this.root.lookup("#icdPersonalSetting");
        this.bind(person);
    }

    @Override
    public void bind(Person record) {
        super.bind(record);
        if (record == null)
            return;
        this.txfForename.textProperty().bindBidirectional(record.forenameProperty());
        this.txfName.textProperty().bindBidirectional(record.nameProperty());
        this.txfCode.textProperty().bindBidirectional(record.codeProperty());
        this.btnPwd.disableProperty().bind(((MergeablePane) this.btnPwd.getParent()).contentEditableProperty().not().or(record.idProperty().isEqualTo(0)));
        this.refBoxLanguage.recordProperty().bindBidirectional(record.languageProperty());
        this.refBoxNutritionType.recordProperty().bindBidirectional(record.nutritionTypeProperty());
        this.refBoxAddress.recordProperty().bindBidirectional(record.addressProperty());
        this.refListContactInformation.bindBidirectional(record.contactInformationProperty());
        this.icdPersonalSetting.bind(record.getPersonalSetting());
    }

    @Override
    public void requestFocus() {
        this.txfForename.requestFocus();
    }

    @Override
    public void setViewMode(ViewMode viewMode) {
        super.setViewMode(viewMode);
        ((MergeablePane) this.btnPwd.getParent()).setContentEditable(viewMode == ViewMode.EDITING);
    }
}
