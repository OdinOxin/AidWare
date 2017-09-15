package de.odinoxin.aiddesk.plugins.rota.shift;

import de.odinoxin.aidcloud.provider.TimestampInterpretationProvider;
import de.odinoxin.aiddesk.controls.refbox.RefBox;
import de.odinoxin.aiddesk.plugins.RecordView;
import de.odinoxin.aiddesk.plugins.rota.TimestampInterpretation;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class RotaShiftView extends RecordView<RotaShift> {

    DatePicker dtpTsBeginn;
    RefBox<TimestampInterpretation> refBoxBeginnInterpretation;
    DatePicker dtpTsEnd;
    RefBox<TimestampInterpretation> refBoxEndInterpretation;
    TextArea txfAdditionalInformation;

    private ChangeListener<LocalDate> tsBeginnListener;
    private ChangeListener<LocalDate> tsEndListener;

    RotaShiftView() {
        this(null);
    }

    RotaShiftView(RotaShift rotaShift) {
        super(rotaShift, "/plugins/rotashiftview.fxml");
        this.dtpTsBeginn = (DatePicker) this.root.lookup("#dtpTsBeginn");
        this.refBoxBeginnInterpretation = (RefBox<TimestampInterpretation>) this.root.lookup("#refBoxBeginnInterpretation");
        this.refBoxBeginnInterpretation.setProvider(new TimestampInterpretationProvider());
        this.dtpTsEnd = (DatePicker) this.root.lookup("#dtpTsEnd");
        this.refBoxEndInterpretation = (RefBox<TimestampInterpretation>) this.root.lookup("#refBoxEndInterpretation");
        this.refBoxEndInterpretation.setProvider(new TimestampInterpretationProvider());
        this.txfAdditionalInformation = (TextArea) this.root.lookup("#txfAdditionalInformation");
        this.bind(rotaShift);
    }

    @Override
    public void bind(RotaShift record) {
        super.bind(record);
        if (this.tsBeginnListener != null) {
            this.dtpTsBeginn.valueProperty().removeListener(this.tsBeginnListener);
            this.tsBeginnListener = null;
        }
        if (this.tsEndListener != null) {
            this.dtpTsEnd.valueProperty().removeListener(this.tsEndListener);
            this.tsEndListener = null;
        }

        if (record == null)
            return;

        this.dtpTsBeginn.setValue(record.getTsBeginn() == null ? null : record.getTsBeginn().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        this.tsBeginnListener = (observable, oldValue, newValue) ->
        {
            record.setTsBeginn(newValue == null ? null : Date.from(newValue.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        };
        this.dtpTsBeginn.valueProperty().addListener(this.tsBeginnListener);
        this.refBoxBeginnInterpretation.recordProperty().bindBidirectional(record.beginnInterpretationProperty());

        this.dtpTsEnd.setValue(record.getTsEnd() == null ? null : record.getTsEnd().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        this.tsEndListener = (observable, oldValue, newValue) ->
        {
            record.setTsEnd(newValue == null ? null : Date.from(newValue.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        };
        this.dtpTsEnd.valueProperty().addListener(this.tsEndListener);
        this.refBoxEndInterpretation.recordProperty().bindBidirectional(record.endInterpretationProperty());

        this.txfAdditionalInformation.textProperty().bindBidirectional(record.textProperty());
    }

    @Override
    public void requestFocus() {
        this.dtpTsBeginn.requestFocus();
    }
}
