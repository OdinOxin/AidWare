package de.odinoxin.aiddesk.plugins.rota;

import de.odinoxin.aidcloud.provider.RotaCategoryProvider;
import de.odinoxin.aiddesk.controls.refbox.RefBox;
import de.odinoxin.aiddesk.plugins.RecordView;
import de.odinoxin.aiddesk.plugins.rota.category.RotaCategory;
import javafx.scene.control.TextField;

public class RotaView extends RecordView<Rota> {

    TextField txfTitle;
    RefBox<RotaCategory> refBoxCategory;

    RotaView() {
        this(null);
    }

    RotaView(Rota rota) {
        super(rota, "/plugins/rotaview.fxml");
        this.txfTitle = (TextField) this.root.lookup("#txfTitle");
        this.refBoxCategory = (RefBox<RotaCategory>) this.root.lookup("#refBoxCategory");
        this.refBoxCategory.setProvider(new RotaCategoryProvider());
        this.bind(rota);
    }

    @Override
    public void bind(Rota record) {
        super.bind(record);
        if (record == null)
            return;
        this.txfTitle.textProperty().bindBidirectional(record.titleProperty());
        this.refBoxCategory.recordProperty().bindBidirectional(record.categoryProperty());
    }

    @Override
    public void requestFocus() {
        this.txfTitle.requestFocus();
    }
}
