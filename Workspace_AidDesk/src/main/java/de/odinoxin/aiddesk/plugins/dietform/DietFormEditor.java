package de.odinoxin.aiddesk.plugins.dietform;

import de.odinoxin.aidcloud.provider.DietFormProvider;
import de.odinoxin.aidcloud.provider.Provider;
import de.odinoxin.aidcloud.service.ConcurrentFault_Exception;
import de.odinoxin.aiddesk.plugins.RecordEditor;
import de.odinoxin.aiddesk.plugins.RecordView;

public class DietFormEditor extends RecordEditor<DietForm> {

    public DietFormEditor() {
        this(null);
    }

    public DietFormEditor(DietForm dietForm) {
        super("DietForms");
        this.attemptLoadRecord(dietForm);
        if (dietForm == null)
            this.onNew();
    }

    @Override
    protected DietForm onSave() throws ConcurrentFault_Exception {
        return this.getProvider().save(this.getRecord(), this.getOriginalRecord());
    }

    @Override
    protected boolean onDelete() {
        return this.getProvider().delete(this.getRecord().getId());
    }

    @Override
    protected void setRecord(DietForm dietForm) {
        if (dietForm == null)
            super.setRecord(new DietForm());
        else
            super.setRecord(dietForm);
    }

    @Override
    protected Provider<DietForm> initProvider() {
        return new DietFormProvider();
    }

    @Override
    public RecordView<DietForm> newView(DietForm record) {
        return new DietFormView(record);
    }
}
