package de.odinoxin.aiddesk.controls;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.HBox;
import javafx.util.StringConverter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateTimePicker extends HBox {

    @FXML
    private DatePicker dtpDate;
    @FXML
    private Spinner<Integer> spnHour;
    @FXML
    private Spinner<Integer> spnMinute;

    private ObjectProperty<Date> value = new SimpleObjectProperty<>(this, "value");
    private BooleanProperty editable = new SimpleBooleanProperty(this, "editable", true);

    private boolean blockMerge = false;
    private LocalDate resettedWith = null;

    public DateTimePicker() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/controls/datetimepicker.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        this.spnHour.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(-1, 24, -1)); // -1, 24 to enable 'overflow' to prev. / next date.
        this.spnHour.getEditor().setAlignment(Pos.CENTER_RIGHT);
        this.spnHour.getEditor().setTextFormatter(new TextFormatter<>(new ClockFormatter()));
        this.spnHour.getEditor().editableProperty().bind(this.editable);
        this.spnMinute.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(-1, 60, -1)); // -1, 60 to enable 'overflow' to prev. / next hour.
        this.spnMinute.getEditor().setAlignment(Pos.CENTER_RIGHT);
        this.spnMinute.getEditor().setTextFormatter(new TextFormatter<>(new ClockFormatter()));
        this.spnMinute.getEditor().editableProperty().bind(this.editable);

        this.editableProperty().addListener((observable, oldValue, newValue) -> this.dtpDate.getEditor().setEditable(newValue));
        this.dtpDate.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (!this.isEditable()) {
                if (oldValue == null && newValue == null)
                    return;
                if (newValue != null && oldValue != null && oldValue.compareTo(newValue) == 0)
                    return;
                if (resettedWith != null && newValue != null && resettedWith.compareTo(newValue) == 0) {
                    resettedWith = null;
                    return;
                }
                resettedWith = oldValue;
                this.dtpDate.setValue(oldValue);
                return;
            }
            resettedWith = null;
            this.mergeInfos(Action.NOTSET);
        });
        this.spnHour.valueProperty().addListener((observable, oldValue, newValue) -> {
            Action action = Action.NOTSET;
            if (oldValue < newValue)
                action = Action.INCREMENT;
            if (oldValue > newValue)
                action = Action.DECREMENT;
            this.mergeInfos(action);
        });
        this.spnMinute.valueProperty().addListener((observable, oldValue, newValue) -> {
            Action action = Action.NOTSET;
            if (oldValue < newValue)
                action = Action.INCREMENT;
            if (oldValue > newValue)
                action = Action.DECREMENT;
            this.mergeInfos(action);
        });
        this.valueProperty().addListener((observable, oldValue, newValue) -> {
            this.blockMerge = true;
            if (newValue == null) {
                this.dtpDate.setValue(null);
                this.spnHour.getValueFactory().setValue(-1);
                this.spnMinute.getValueFactory().setValue(-1);
            } else {
                Calendar cal = new GregorianCalendar();
                cal.setTime(newValue);
                this.dtpDate.setValue(newValue.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                this.spnHour.getValueFactory().setValue(cal.get(Calendar.HOUR_OF_DAY));
                this.spnMinute.getValueFactory().setValue(cal.get(Calendar.MINUTE));
            }
            this.blockMerge = false;
        });
    }

    private void mergeInfos(Action action) {
        if (this.blockMerge)
            return;
        if (this.dtpDate.getValue() == null && this.spnHour.getValue() < 0 && this.spnMinute.getValue() < 0) {
            // Is null date
            this.setValue(null);
            return;
        }
        Date newDate = new Date();
        Calendar cal = new GregorianCalendar();
        if (this.dtpDate.getValue() != null) {
            newDate = Date.from(this.dtpDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        }
        cal.setTime(newDate);
        int sum = this.spnHour.getValue() + this.spnMinute.getValue();
        if (sum == -2 || (sum < 0 && action == Action.INCREMENT)) {
            this.blockMerge = true;
            this.spnHour.getValueFactory().setValue(0);
            this.spnMinute.getValueFactory().setValue(0);
            this.blockMerge = false;
        }
        cal.set(Calendar.HOUR_OF_DAY, this.spnHour.getValue());
        cal.set(Calendar.MINUTE, this.spnMinute.getValue());
        newDate = cal.getTime();
        if (this.getValue() == null || this.getValue().compareTo(newDate) != 0)
            this.setValue(newDate);
    }

    public Date getValue() {
        return value.get();
    }

    public void setValue(Date value) {
        this.value.set(value);
    }

    public ObjectProperty<Date> valueProperty() {
        return value;
    }

    public boolean isEditable() {
        return editable.get();
    }

    public void setEditable(boolean editable) {
        this.editable.set(editable);
    }

    public BooleanProperty editableProperty() {
        return editable;
    }

    private class ClockFormatter extends StringConverter<Integer> {
        @Override
        public Integer fromString(String string) {
            try {
                return Integer.parseInt(string);
            } catch (Exception ex) {
                return null;
            }
        }

        @Override
        public String toString(Integer i) {
            if (i == null || i < 0)
                return "";
            return String.format("%02d", i);
        }
    }

    private enum Action {
        NOTSET, INCREMENT, DECREMENT
    }
}
