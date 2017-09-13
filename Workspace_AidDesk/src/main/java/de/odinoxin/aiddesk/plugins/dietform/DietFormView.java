package de.odinoxin.aiddesk.plugins.dietform;

import de.odinoxin.aiddesk.plugins.RecordView;
import javafx.scene.control.TextField;

public class DietFormView extends RecordView<DietForm> {

    TextField txfName;

    DietFormView() {
        this(null);
    }

    DietFormView(DietForm dietForm) {
        super(dietForm, "/plugins/dietformview.fxml");
        this.txfName = (TextField) this.root.lookup("#txfName");
        this.bind(dietForm);
    }

    @Override
    public void bind(DietForm record) {
        super.bind(record);
        if (record == null)
            return;
        this.txfName.textProperty().bindBidirectional(record.nameProperty());
    }

    @Override
    public void requestFocus() {
        this.txfName.requestFocus();
    }
}
