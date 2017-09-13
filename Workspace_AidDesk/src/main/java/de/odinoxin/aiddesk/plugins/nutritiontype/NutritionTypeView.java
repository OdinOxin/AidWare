package de.odinoxin.aiddesk.plugins.nutritiontype;

import de.odinoxin.aiddesk.plugins.RecordView;
import javafx.scene.control.TextField;

public class NutritionTypeView extends RecordView<NutritionType> {

    TextField txfName;

    NutritionTypeView() {
        this(null);
    }

    NutritionTypeView(NutritionType nutritionType) {
        super(nutritionType, "/plugins/dietformview.fxml");
        this.txfName = (TextField) this.root.lookup("#txfName");
        this.bind(nutritionType);
    }

    @Override
    public void bind(NutritionType record) {
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
