package de.odinoxin.aiddesk.plugins.rota.shift;

import de.odinoxin.aidcloud.provider.TimestampInterpretationProvider;
import de.odinoxin.aiddesk.controls.DateTimePicker;
import de.odinoxin.aiddesk.controls.refbox.RefBox;
import de.odinoxin.aiddesk.plugins.RecordView;
import de.odinoxin.aiddesk.plugins.rota.TimestampInterpretation;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.TextArea;

import java.time.LocalDate;

public class RotaShiftView extends RecordView<RotaShift> {

    DateTimePicker dtpTsBeginn;
    RefBox<TimestampInterpretation> refBoxBeginnInterpretation;
    DateTimePicker dtpTsEnd;
    RefBox<TimestampInterpretation> refBoxEndInterpretation;
    TextArea txfAdditionalInformation;

    private ChangeListener<LocalDate> tsBeginnListener;
    private ChangeListener<LocalDate> tsEndListener;

    RotaShiftView() {
        this(null);
    }

    RotaShiftView(RotaShift rotaShift) {
        super(rotaShift, "/plugins/rotashiftview.fxml");
        this.dtpTsBeginn = (DateTimePicker) this.root.lookup("#dtpTsBeginn");
        this.refBoxBeginnInterpretation = (RefBox<TimestampInterpretation>) this.root.lookup("#refBoxBeginnInterpretation");
        this.refBoxBeginnInterpretation.setProvider(new TimestampInterpretationProvider());
        this.dtpTsEnd = (DateTimePicker) this.root.lookup("#dtpTsEnd");
        this.refBoxEndInterpretation = (RefBox<TimestampInterpretation>) this.root.lookup("#refBoxEndInterpretation");
        this.refBoxEndInterpretation.setProvider(new TimestampInterpretationProvider());
        this.txfAdditionalInformation = (TextArea) this.root.lookup("#txfAdditionalInformation");
        this.bind(rotaShift);
    }

    @Override
    public void bind(RotaShift record) {
        super.bind(record);
        if (record == null)
            return;

        this.dtpTsBeginn.valueProperty().bindBidirectional(record.tsBeginnProperty());
        this.refBoxBeginnInterpretation.recordProperty().bindBidirectional(record.beginnInterpretationProperty());
        this.dtpTsEnd.valueProperty().bindBidirectional(record.tsEndProperty());
        this.refBoxEndInterpretation.recordProperty().bindBidirectional(record.endInterpretationProperty());
        this.txfAdditionalInformation.textProperty().bindBidirectional(record.textProperty());
    }

    @Override
    public void requestFocus() {
        this.dtpTsBeginn.requestFocus();
    }
}
