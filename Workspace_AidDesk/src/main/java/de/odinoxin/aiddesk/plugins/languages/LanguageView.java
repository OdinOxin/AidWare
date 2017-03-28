package de.odinoxin.aiddesk.plugins.languages;

import de.odinoxin.aiddesk.plugins.RecordView;
import javafx.scene.control.TextField;

public class LanguageView extends RecordView<Language> {

    TextField txfName;
    TextField txfCode;

    LanguageView() {
        this(null);
    }

    LanguageView(Language language) {
        super(language, "/plugins/languageview.fxml");
        this.txfName = (TextField) this.root.lookup("#txfName");
        this.txfCode = (TextField) this.root.lookup("#txfCode");
        this.bind(language);
    }

    @Override
    public void bind(Language record) {
        super.bind(record);
        if (record == null)
            return;
        this.txfName.textProperty().bindBidirectional(record.nameProperty());
        this.txfCode.textProperty().bindBidirectional(record.codeProperty());
    }

    @Override
    public void requestFocus() {
        this.txfName.requestFocus();
    }
}
