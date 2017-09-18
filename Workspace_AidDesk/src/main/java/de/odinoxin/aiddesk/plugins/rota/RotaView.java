package de.odinoxin.aiddesk.plugins.rota;

import de.odinoxin.aidcloud.provider.RotaCategoryProvider;
import de.odinoxin.aidcloud.provider.RotaShiftProvider;
import de.odinoxin.aiddesk.controls.refbox.RefBox;
import de.odinoxin.aiddesk.controls.reflist.RefList;
import de.odinoxin.aiddesk.plugins.RecordView;
import de.odinoxin.aiddesk.plugins.rota.category.RotaCategory;
import de.odinoxin.aiddesk.plugins.rota.shift.RotaShift;
import javafx.beans.binding.Bindings;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.ZoneId;

public class RotaView extends RecordView<Rota> {

    TextField txfTitle;
    RefBox<RotaCategory> refBoxCategory;
    DatePicker dtpFirstBeginn;
    DatePicker dtpLastEnd;
    RefList<RotaShift> refListRotaShifts;

    RotaView() {
        this(null);
    }

    RotaView(Rota rota) {
        super(rota, "/plugins/rotaview.fxml");
        this.txfTitle = (TextField) this.root.lookup("#txfTitle");
        this.refBoxCategory = (RefBox<RotaCategory>) this.root.lookup("#refBoxCategory");
        this.refBoxCategory.setProvider(new RotaCategoryProvider());
        this.dtpFirstBeginn = (DatePicker) this.root.lookup("#dtpFirstBeginn");
        this.dtpLastEnd = (DatePicker) this.root.lookup("#dtpLastEnd");
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
        this.dtpFirstBeginn.setValue(record.getFirstBeginn() == null ? null : record.getFirstBeginn().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        this.dtpLastEnd.setValue(record.getLastEnd() == null ? null : record.getLastEnd().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        this.refListRotaShifts.bindBidirectional(record.rotaShiftsProperty());
    }

    @Override
    public void requestFocus() {
        this.txfTitle.requestFocus();
    }
}
