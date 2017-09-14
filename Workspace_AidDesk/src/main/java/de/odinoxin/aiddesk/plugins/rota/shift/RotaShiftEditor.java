package de.odinoxin.aiddesk.plugins.rota.shift;

import de.odinoxin.aidcloud.provider.Provider;
import de.odinoxin.aidcloud.provider.RotaShiftProvider;
import de.odinoxin.aidcloud.service.ConcurrentFault_Exception;
import de.odinoxin.aiddesk.plugins.RecordEditor;
import de.odinoxin.aiddesk.plugins.RecordView;

public class RotaShiftEditor extends RecordEditor<RotaShift> {

    public RotaShiftEditor() {
        this(null);
    }

    public RotaShiftEditor(RotaShift rota) {
        super("RotaShift");
        this.attemptLoadRecord(rota);
        if (rota == null)
            this.onNew();
    }

    @Override
    protected RotaShift onSave() throws ConcurrentFault_Exception {
        return this.getProvider().save(this.getRecord(), this.getOriginalRecord());
    }

    @Override
    protected boolean onDelete() {
        return this.getProvider().delete(this.getRecord().getId());
    }

    @Override
    protected void setRecord(RotaShift rota) {
        if (rota == null)
            super.setRecord(new RotaShift());
        else
            super.setRecord(rota);
    }

    @Override
    protected Provider<RotaShift> initProvider() {
        return new RotaShiftProvider();
    }

    @Override
    public RecordView<RotaShift> newView(RotaShift record) {
        return new RotaShiftView(record);
    }
}
