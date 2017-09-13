package de.odinoxin.aiddesk.plugins.rota.category;

import de.odinoxin.aiddesk.plugins.RecordView;
import javafx.scene.control.TextField;

public class RotaCategoryView extends RecordView<RotaCategory> {

    TextField txfName;

    RotaCategoryView() {
        this(null);
    }

    RotaCategoryView(RotaCategory category) {
        super(category, "/plugins/rotacategoryview.fxml");
        this.txfName = (TextField) this.root.lookup("#txfName");
        this.bind(category);
    }

    @Override
    public void bind(RotaCategory record) {
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
