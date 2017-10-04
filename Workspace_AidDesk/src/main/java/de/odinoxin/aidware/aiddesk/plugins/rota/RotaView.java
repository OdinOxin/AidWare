package de.odinoxin.aidware.aiddesk.plugins.rota;

import de.odinoxin.aidware.aidcloud.provider.RotaCategoryProvider;
import de.odinoxin.aidware.aidcloud.provider.RotaShiftProvider;
import de.odinoxin.aidware.aiddesk.controls.DateTimePicker;
import de.odinoxin.aidware.aiddesk.controls.refbox.RefBox;
import de.odinoxin.aidware.aiddesk.controls.reflist.RefList;
import de.odinoxin.aidware.aiddesk.plugins.RecordView;
import de.odinoxin.aidware.aiddesk.plugins.rota.category.RotaCategory;
import de.odinoxin.aidware.aiddesk.plugins.rota.shift.RotaShift;
import javafx.scene.control.TextField;

public class RotaView extends RecordView<Rota> {

    TextField txfTitle;
    RefBox<RotaCategory> refBoxCategory;
    DateTimePicker dtpFirstBeginn;
    DateTimePicker dtpLastEnd;
    RefList<RotaShift> refListRotaShifts;

    RotaView() {
        this(null);
    }

    RotaView(Rota rota) {
        super(rota, "/plugins/rotaview.fxml");
        this.txfTitle = (TextField) this.root.lookup("#txfTitle");
        this.refBoxCategory = (RefBox<RotaCategory>) this.root.lookup("#refBoxCategory");
        this.refBoxCategory.setProvider(new RotaCategoryProvider());
        this.dtpFirstBeginn = (DateTimePicker) this.root.lookup("#dtpFirstBeginn");
        this.dtpLastEnd = (DateTimePicker) this.root.lookup("#dtpLastEnd");
        this.refListRotaShifts = (RefList<RotaShift>) this.root.lookup("#refListRotaShifts");
        this.refListRotaShifts.setProvider(new RotaShiftProvider());
        this.refListRotaShifts.setShowDetails(true);
        this.bind(rota);
    }

    @Override
    public void bind(Rota record) {
        super.bind(record);
        if (record == null)
            return;
        this.txfTitle.textProperty().bindBidirectional(record.titleProperty());
        this.refBoxCategory.recordProperty().bindBidirectional(record.rotaCategoryProperty());
        this.dtpFirstBeginn.setValue(record.getFirstBeginn());
        this.dtpLastEnd.setValue(record.getLastEnd());
        this.refListRotaShifts.bindBidirectional(record.rotaShiftsProperty());
    }

    @Override
    public void requestFocus() {
        this.txfTitle.requestFocus();
    }
}
