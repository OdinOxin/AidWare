package de.odinoxin.aiddesk.plugins.rota.shift;

import de.odinoxin.aidcloud.service.RotaShiftEntity;
import de.odinoxin.aiddesk.plugins.RecordItem;
import de.odinoxin.aiddesk.plugins.rota.TimestampInterpretation;
import javafx.beans.property.*;

import java.util.Date;
import java.util.Hashtable;

public class RotaShift extends RecordItem<RotaShiftEntity> {

    private StringProperty text = new SimpleStringProperty(null, "Text");
    private ObjectProperty<Date> tsBeginn = new SimpleObjectProperty<>(null, "TsBginn");
    private ObjectProperty<TimestampInterpretation> beginnInterpretation = new SimpleObjectProperty<>(null, "BeginnInterpretation");
    private ObjectProperty<Date> tsEnd = new SimpleObjectProperty<>(null, "TsEnd");
    private ObjectProperty<TimestampInterpretation> endInterpretation = new SimpleObjectProperty<>(null, "EndInterpretation");

    public RotaShift() {
        super();
        this.text.addListener((observable, oldValue, newValue) -> this.setChanged(true));
        this.beginnInterpretation.addListener((observable, oldValue, newValue) -> this.setChanged(true));
        this.tsBeginn.addListener((observable, oldValue, newValue) -> this.setChanged(true));
        this.endInterpretation.addListener((observable, oldValue, newValue) -> this.setChanged(true));
        this.tsEnd.addListener((observable, oldValue, newValue) -> this.setChanged(true));
        this.setChanged(false);
    }

    public RotaShift(int id) {
        this();
        this.setId(id);
        this.setChanged(false);
    }

    public RotaShift(int id, String name, Date tsBeginn, TimestampInterpretation beginnInterpretation, Date tsEnd, TimestampInterpretation endInterpretation) {
        this(id);
        this.setText(name);
        this.setTsBeginn(tsBeginn);
        this.setBeginnInterpretation(beginnInterpretation);
        this.setTsEnd(tsEnd);
        this.setEndInterpretation(endInterpretation);
        this.setChanged(false);
    }

    public RotaShift(RotaShiftEntity entity) {
        this(entity.getId(), entity.getText(),
                RecordItem.toDate(entity.getTsBeginn()),
                entity.getBeginnInterpretation() == null ? null : new TimestampInterpretation(entity.getBeginnInterpretation()),
                RecordItem.toDate(entity.getTsEnd()),
                entity.getEndInterpretation() == null ? null : new TimestampInterpretation(entity.getEndInterpretation()));
    }

    @Override
    protected Object clone() {
        return new RotaShift(this.getId(), this.getText(), this.getTsBeginn(), this.getBeginnInterpretation(), this.getTsEnd(), this.getEndInterpretation());
    }

    public String getText() {
        return text.get();
    }

    public void setText(String text) {
        this.text.set(text);
    }

    public StringProperty textProperty() {
        return text;
    }

    public Date getTsBeginn() {
        return tsBeginn.get();
    }

    public void setTsBeginn(Date tsBeginn) {
        this.tsBeginn.set(tsBeginn);
    }

    public ObjectProperty<Date> tsBeginnProperty() {
        return tsBeginn;
    }

    public TimestampInterpretation getBeginnInterpretation() {
        return beginnInterpretation.get();
    }

    public void setBeginnInterpretation(TimestampInterpretation beginnInterpretation) {
        this.beginnInterpretation.set(beginnInterpretation);
    }

    public ObjectProperty<TimestampInterpretation> beginnInterpretationProperty() {
        return beginnInterpretation;
    }

    public Date getTsEnd() {
        return tsEnd.get();
    }

    public void setTsEnd(Date tsEnd) {
        this.tsEnd.set(tsEnd);
    }

    public ObjectProperty<Date> tsEndProperty() {
        return tsEnd;
    }

    public TimestampInterpretation getEndInterpretation() {
        return endInterpretation.get();
    }

    public void setEndInterpretation(TimestampInterpretation endInterpretation) {
        this.endInterpretation.set(endInterpretation);
    }

    public ObjectProperty<TimestampInterpretation> endInterpretationProperty() {
        return endInterpretation;
    }

    @Override
    public RotaShiftEntity toEntity() {
        RotaShiftEntity entity = new RotaShiftEntity();
        entity.setId(this.getId());
        entity.setText(this.getText());
        entity.setTsBeginn(RecordItem.toXMLGregorianCalendar(this.getTsBeginn()));
        entity.setBeginnInterpretation(this.getBeginnInterpretation() == null ? null : this.getBeginnInterpretation().toEntity());
        entity.setTsEnd(RecordItem.toXMLGregorianCalendar(this.getTsEnd()));
        entity.setEndInterpretation(this.getEndInterpretation() == null ? null : this.getEndInterpretation().toEntity());
        return entity;
    }

    @Override
    protected Hashtable<String, Property<?>> getProperties() {
        Hashtable<String, Property<?>> properties = new Hashtable<>();
        properties.put(this.text.getName(), this.text);
        return properties;
    }
}
