package de.odinoxin.aidware.aiddesk.dialogs;

import de.odinoxin.aidware.aidcloud.provider.TranslatorProvider;
import de.odinoxin.aidware.aiddesk.plugins.Plugin;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Window;

/**
 * Confirmation {@link Alert}, where the use has to make a decision.
 */
public class DecisionDialog extends Alert {

    public DecisionDialog(Window owner, String title, String msg) {
        super(AlertType.CONFIRMATION, TranslatorProvider.getTranslation(msg));

        this.setHeaderText(TranslatorProvider.getTranslation(title));

        Button btnOK = (Button) this.getDialogPane().lookupButton(ButtonType.OK);
        btnOK.setDefaultButton(false);
        Plugin.setButtonEnter(btnOK);
        Button btnCancel = (Button) this.getDialogPane().lookupButton(ButtonType.CANCEL);
        btnCancel.setDefaultButton(true);
        Plugin.setButtonEnter(btnCancel);
        Platform.runLater(() -> btnCancel.requestFocus());

        this.initModality(Modality.APPLICATION_MODAL);
        this.initOwner(owner);
    }
}
