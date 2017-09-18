package de.odinoxin.aiddesk.controls;

import javafx.scene.control.DatePicker;

public class DateTimePicker extends DatePicker {

    private String resettedWith = null;

    public DateTimePicker() {
        this.editableProperty().addListener((observable, oldValue, newValue) -> this.getEditor().setEditable(newValue));
        this.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            if (!this.isEditable()) {
                if (oldValue == null && newValue == null)
                    return;
                if (oldValue != null && oldValue.equals(newValue))
                    return;
                if (newValue != null && newValue.equals(oldValue))
                    return;
                if (resettedWith != null && resettedWith.equals(newValue)) {
                    resettedWith = null;
                    return;
                }
                resettedWith = oldValue;
                this.getEditor().setText(oldValue);
                return;
            }
            resettedWith = null;
        });
    }
}
