package de.odinoxin.aidware.aiddesk.plugins.rota;

import de.odinoxin.aidware.aidcloud.provider.Provider;
import de.odinoxin.aidware.aidcloud.provider.TimestampInterpretationProvider;
import de.odinoxin.aidware.aiddesk.plugins.RecordEditor;
import de.odinoxin.aidware.aiddesk.plugins.RecordView;

public class TimestampInterpretationEditor extends RecordEditor<TimestampInterpretation> {

    public TimestampInterpretationEditor() {
        this(null);
    }

    public TimestampInterpretationEditor(TimestampInterpretation record) {
        super("TimestampInterpretation", record);
    }

    @Override
    protected void setRecord(TimestampInterpretation rota) {
        if (rota == null)
            super.setRecord(new TimestampInterpretation());
        else
            super.setRecord(rota);
    }

    @Override
    protected Provider<TimestampInterpretation> initProvider() {
        return new TimestampInterpretationProvider();
    }

    @Override
    public RecordView<TimestampInterpretation> newView(TimestampInterpretation record) {
        return new TimestampInterpretationView(record);
    }
}
