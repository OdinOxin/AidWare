package de.odinoxin.aiddesk.plugins.contact.information;

import de.odinoxin.aidcloud.provider.ContactTypeProvider;
import de.odinoxin.aiddesk.controls.refbox.RefBox;
import de.odinoxin.aiddesk.plugins.RecordView;
import de.odinoxin.aiddesk.plugins.contact.types.ContactType;
import javafx.scene.control.TextField;

public class ContactInformationView extends RecordView<ContactInformation> {

    private RefBox<ContactType> refBoxContactType;
    private TextField txfInformation;

    public ContactInformationView() {
        super("/plugins/contactinformationeditor.fxml");
        this.refBoxContactType = (RefBox<ContactType>) this.root.lookup("#refBoxContactType");
        this.refBoxContactType.setProvider(new ContactTypeProvider());
        this.txfInformation = (TextField) this.root.lookup("#txfInformation");
    }

    @Override
    public void bind(ContactInformation record) {
        if(record == null)
            return;
        this.refBoxContactType.recordProperty().bindBidirectional(record.contactTypeProperty());
        this.txfInformation.textProperty().bindBidirectional(record.informationProperty());
    }

    @Override
    public void requestFocus() {
        this.refBoxContactType.requestFocus();
    }
}
