package de.odinoxin.aidware.aiddesk.plugins.rota;

import de.odinoxin.aidware.aiddesk.plugins.RecordView;
import javafx.scene.control.TextField;

public class TimestampInterpretationView extends RecordView<TimestampInterpretation> {

    TextField txfName;

    TimestampInterpretationView() {
        this(null);
    }

    TimestampInterpretationView(TimestampInterpretation record) {
        super(record, "/plugins/timestampinterpretationview.fxml");
        this.txfName = (TextField) this.root.lookup("#txfName");
        this.bind(record);
    }

    @Override
    public void bind(TimestampInterpretation record) {
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
