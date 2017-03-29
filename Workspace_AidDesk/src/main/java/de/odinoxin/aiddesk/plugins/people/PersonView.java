package de.odinoxin.aiddesk.plugins.people;

import de.odinoxin.aidcloud.provider.AddressProvider;
import de.odinoxin.aidcloud.provider.ContactInformationProvider;
import de.odinoxin.aidcloud.provider.LanguageProvider;
import de.odinoxin.aiddesk.controls.SelectablePane;
import de.odinoxin.aiddesk.controls.refbox.RefBox;
import de.odinoxin.aiddesk.controls.reflist.RefList;
import de.odinoxin.aiddesk.controls.translateable.Button;
import de.odinoxin.aiddesk.plugins.Plugin;
import de.odinoxin.aiddesk.plugins.RecordView;
import de.odinoxin.aiddesk.plugins.addresses.Address;
import de.odinoxin.aiddesk.plugins.contact.information.ContactInformation;
import de.odinoxin.aiddesk.plugins.languages.Language;
import javafx.scene.control.TextField;

public class PersonView extends RecordView<Person> {

    TextField txfForename;
    TextField txfName;
    TextField txfCode;
    Button btnPwd;
    RefBox<Language> refBoxLanguage;
    RefBox<Address> refBoxAddress;
    RefList<ContactInformation> refListContactInformation;

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
        this.refBoxAddress = (RefBox<Address>) this.root.lookup("#refBoxAddress");
        this.refBoxAddress.setProvider(new AddressProvider());
        this.refListContactInformation = (RefList<ContactInformation>) this.root.lookup("#refListContactInformation");
        this.refListContactInformation.setProvider(new ContactInformationProvider());
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
        this.btnPwd.disableProperty().bind(record.idProperty().isEqualTo(0));
        this.refBoxLanguage.recordProperty().bindBidirectional(record.languageProperty());
        this.refBoxAddress.recordProperty().bindBidirectional(record.addressProperty());
        this.refListContactInformation.bindBidirectional(record.contactInformationProperty());

        this.selectables.clear();
        this.selectables.put(record.forenameProperty().getName(), (SelectablePane) txfForename.getParent());
        this.selectables.put(record.nameProperty().getName(), (SelectablePane) txfName.getParent());
        this.selectables.put(record.codeProperty().getName(), (SelectablePane) txfCode.getParent());
        this.selectables.put(record.languageProperty().getName(), (SelectablePane) refBoxLanguage.getParent());
        this.selectables.put(record.addressProperty().getName(), (SelectablePane) refBoxAddress.getParent());
    }

    @Override
    public void requestFocus() {
        this.txfForename.requestFocus();
    }
}