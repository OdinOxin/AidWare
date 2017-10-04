package de.odinoxin.aiddesk.plugins.people.personalsetting;

import de.odinoxin.aiddesk.plugins.RecordView;
import javafx.scene.control.TextField;

public class PersonalSettingView extends RecordView<PersonalSetting> {

    private TextField txfBgColor;
    private TextField txfBgColorPlugin;

    public PersonalSettingView() {
        this(null, null);
    }

    public PersonalSettingView(PersonalSettingEditor editor) {
        this(null, editor);
    }

    public PersonalSettingView(PersonalSetting record, PersonalSettingEditor editor) {
        super(record, "/plugins/personalsettings.fxml");

        this.txfBgColor = (TextField) this.root.lookup("#txfBgColor");
        this.txfBgColorPlugin = (TextField) this.root.lookup("#txfBgColorPlugin");

        this.bind(record);
    }

    @Override
    public void bind(PersonalSetting record) {
        super.bind(record);

        if (record == null)
            return;
        this.txfBgColor.textProperty().bindBidirectional(record.bgColorProperty());
        this.txfBgColorPlugin.textProperty().bindBidirectional(record.bgColorPluginProperty());
    }

    @Override
    public void requestFocus() {
        this.txfBgColor.requestFocus();
    }


}
