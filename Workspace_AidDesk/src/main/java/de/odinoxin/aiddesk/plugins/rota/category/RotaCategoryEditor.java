package de.odinoxin.aiddesk.plugins.rota.category;

import de.odinoxin.aidcloud.provider.RotaCategoryProvider;
import de.odinoxin.aidcloud.provider.Provider;
import de.odinoxin.aidcloud.service.ConcurrentFault_Exception;
import de.odinoxin.aiddesk.plugins.RecordEditor;
import de.odinoxin.aiddesk.plugins.RecordView;

public class RotaCategoryEditor extends RecordEditor<RotaCategory> {

    public RotaCategoryEditor() {
        this(null);
    }

    public RotaCategoryEditor(RotaCategory category) {
        super("RotaCategory");
        this.attemptLoadRecord(category);
        if (category == null)
            this.onNew();
    }

    @Override
    protected RotaCategory onSave() throws ConcurrentFault_Exception {
        return this.getProvider().save(this.getRecord(), this.getOriginalRecord());
    }

    @Override
    protected boolean onDelete() {
        return this.getProvider().delete(this.getRecord().getId());
    }

    @Override
    protected void setRecord(RotaCategory nutritionType) {
        if (nutritionType == null)
            super.setRecord(new RotaCategory());
        else
            super.setRecord(nutritionType);
    }

    @Override
    protected Provider<RotaCategory> initProvider() {
        return new RotaCategoryProvider();
    }

    @Override
    public RecordView<RotaCategory> newView(RotaCategory record) {
        return new RotaCategoryView(record);
    }
}
