package de.odinoxin.aidware.aiddesk.plugins.people;

import de.odinoxin.aidware.aidcloud.provider.LoginProvider;
import de.odinoxin.aidware.aiddesk.controls.translateable.Button;
import de.odinoxin.aidware.aiddesk.dialogs.MsgDialog;
import de.odinoxin.aidware.aiddesk.plugins.Plugin;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.stage.Modality;

public class PwdEditor extends Plugin {

    private PasswordField pwfCurrent;
    private PasswordField pwfNew;
    private PasswordField pwfRepeat;
    private Button btnOK;
    private Button btnCancel;

    public PwdEditor(PersonEditor personEditor) {
        super("/plugins/passwordeditor.fxml", "Passwort");

        this.btnOK = (Button) this.root.lookup("#btnOK");
        Plugin.setButtonEnter(this.btnOK);
        this.btnOK.setOnAction(ev -> {
            this.pwfCurrent = (PasswordField) this.root.lookup("#pwfCurrent");
            this.pwfNew = (PasswordField) this.root.lookup("#pwfNew");
            this.pwfRepeat = (PasswordField) this.root.lookup("#pwfRepeat");
            if (this.pwfNew.getText().isEmpty() || this.pwfRepeat.getText().isEmpty() || !this.pwfNew.getText().equals(this.pwfRepeat.getText())) {
                new MsgDialog(this, Alert.AlertType.ERROR, "Ungültige Eingabe!", "Geben Sie das neue Passwort ein und wiederholen Sie dieses korrekt.").showAndWait();
                return;
            }
            String token = new LoginProvider().authenticate(personEditor.getRecord().getId(), this.pwfCurrent.getText());
            if (token == null || token.isEmpty()) {
                new MsgDialog(this, Alert.AlertType.ERROR, "Ungültige Eingabe!", "Geben Sie das aktuelle Passwort an, um ein neues Passwort zu speichern.").showAndWait();
                return;
            }
            personEditor.changePwd(this.pwfCurrent.getText(), this.pwfNew.getText());
            this.close();
        });
        this.btnCancel = (Button) this.root.lookup("#btnCancel");
        Plugin.setButtonEnter(this.btnCancel);
        this.btnCancel.setOnAction(ev -> this.close());

        this.initModality(Modality.WINDOW_MODAL);
        this.initOwner(personEditor);
        this.show();
    }
}
