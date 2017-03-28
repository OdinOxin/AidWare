package de.odinoxin.aiddesk.plugins.addresses;

import de.odinoxin.aidcloud.provider.CountryProvider;
import de.odinoxin.aiddesk.controls.refbox.RefBox;
import de.odinoxin.aiddesk.plugins.RecordView;
import de.odinoxin.aiddesk.plugins.countries.Country;
import javafx.scene.control.TextField;

public class AddressView extends RecordView<Address> {

    private TextField txfStreet;
    private TextField txftxfHsNo;
    private TextField txftxfZip;
    private TextField txftxfCity;
    private RefBox<Country> refBoxCountry;

    AddressView() {
        this(null);
    }

    AddressView(Address address) {
        super(address, "/plugins/addressview.fxml");
        this.txfStreet = (TextField) this.root.lookup("#txfStreet");
        this.txftxfHsNo = (TextField) this.root.lookup("#txfHsNo");
        this.txftxfZip = (TextField) this.root.lookup("#txfZip");
        this.txftxfCity = (TextField) this.root.lookup("#txfCity");
        this.refBoxCountry = (RefBox<Country>) this.root.lookup("#refBoxCountry");
        this.refBoxCountry.setProvider(new CountryProvider());
        this.bind(address);
    }

    @Override
    public void bind(Address record) {
        super.bind(record);
        if (record == null)
            return;
        this.txfStreet.textProperty().bindBidirectional(record.streetProperty());
        this.txftxfHsNo.textProperty().bindBidirectional(record.hsNoProperty());
        this.txftxfZip.textProperty().bindBidirectional(record.zipProperty());
        this.txftxfCity.textProperty().bindBidirectional(record.cityProperty());
        this.refBoxCountry.recordProperty().bindBidirectional(record.countryProperty());
    }

    @Override
    public void requestFocus() {
        this.txfStreet.requestFocus();
    }
}
