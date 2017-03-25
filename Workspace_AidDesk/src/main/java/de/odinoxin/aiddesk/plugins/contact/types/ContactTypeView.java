package de.odinoxin.aiddesk.plugins.contact.types;

import de.odinoxin.aiddesk.plugins.RecordView;
import javafx.scene.control.TextField;

public class ContactTypeView extends RecordView<ContactType> {

    private TextField txfName;
    private TextField txfCode;
    private TextField txfRegex;

    public ContactTypeView() {
        super("/plugins/contacttypeeditor.fxml");
        this.txfName = (TextField) this.root.lookup("#txfName");
        this.txfCode = (TextField) this.root.lookup("#txfCode");
        this.txfRegex = (TextField) this.root.lookup("#txfRegex");
    }

    @Override
    public void bind(ContactType record) {
        if(record == null)
            return;
        this.txfName.textProperty().bindBidirectional(record.nameProperty());
        this.txfCode.textProperty().bindBidirectional(record.codeProperty());
        this.txfRegex.textProperty().bindBidirectional(record.regexProperty());
    }

    @Override
    public void requestFocus() {
        this.txfName.requestFocus();
    }
}
