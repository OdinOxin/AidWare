package de.odinoxin.aiddesk.plugins.countries;

import de.odinoxin.aiddesk.plugins.RecordView;
import javafx.scene.control.TextField;

public class CountryView extends RecordView<Country> {

    TextField txfAlpha2;
    private TextField txfAlpha3;
    private TextField txfName;
    private TextField txfAreaCode;

    public CountryView()
    {
        super("/plugins/countryeditor.fxml");
        this.txfAlpha2 = (TextField) this.root.lookup("#txfAlpha2");
        this.txfAlpha3 = (TextField) this.root.lookup("#txfAlpha3");
        this.txfName = (TextField) this.root.lookup("#txfName");
        this.txfAreaCode = (TextField) this.root.lookup("#txfAreaCode");
    }

    @Override
    public void bind(Country record) {
        this.txfAlpha2.textProperty().bindBidirectional(record.alpha2Property());
        this.txfAlpha3.textProperty().bindBidirectional(record.alpha3Property());
        this.txfName.textProperty().bindBidirectional(record.nameProperty());
        this.txfAreaCode.textProperty().bindBidirectional(record.areaCodeProperty());
    }

    @Override
    public void requestFocus() {
        this.txfAlpha2.requestFocus();
    }
}
