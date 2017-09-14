package de.odinoxin.aiddesk.plugins.rota;

import de.odinoxin.aidcloud.provider.Provider;
import de.odinoxin.aidcloud.provider.TimestampInterpretationProvider;
import de.odinoxin.aidcloud.service.ConcurrentFault_Exception;
import de.odinoxin.aiddesk.plugins.RecordEditor;
import de.odinoxin.aiddesk.plugins.RecordView;

public class TimestampInterpretationEditor extends RecordEditor<TimestampInterpretation> {

    public TimestampInterpretationEditor() {
        this(null);
    }

    public TimestampInterpretationEditor(TimestampInterpretation rota) {
        super("TimestampInterpretation");
        this.attemptLoadRecord(rota);
        if (rota == null)
            this.onNew();
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
