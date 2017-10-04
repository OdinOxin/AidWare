package de.odinoxin.aidware.aiddesk.plugins.rota;

import de.odinoxin.aidware.aidcloud.provider.Provider;
import de.odinoxin.aidware.aidcloud.provider.TimestampInterpretationProvider;
import de.odinoxin.aidware.aidcloud.service.ConcurrentFault_Exception;
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
    protected TimestampInterpretation onSave() throws ConcurrentFault_Exception {
        return this.getProvider().save(this.getRecord(), this.getOriginalRecord());
    }

    @Override
    protected boolean onDelete() {
        return this.getProvider().delete(this.getRecord().getId());
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
