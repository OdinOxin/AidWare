package de.odinoxin.aiddesk.plugins.nutritiontype;

import de.odinoxin.aidcloud.provider.NutritionTypeProvider;
import de.odinoxin.aidcloud.provider.Provider;
import de.odinoxin.aidcloud.service.ConcurrentFault_Exception;
import de.odinoxin.aiddesk.plugins.RecordEditor;
import de.odinoxin.aiddesk.plugins.RecordView;

public class NutritionTypeEditor extends RecordEditor<NutritionType> {

    public NutritionTypeEditor() {
        this(null);
    }

    public NutritionTypeEditor(NutritionType record) {
        super("NutritionType", record);
    }

    @Override
    protected NutritionType onSave() throws ConcurrentFault_Exception {
        return this.getProvider().save(this.getRecord(), this.getOriginalRecord());
    }

    @Override
    protected boolean onDelete() {
        return this.getProvider().delete(this.getRecord().getId());
    }

    @Override
    protected void setRecord(NutritionType nutritionType) {
        if (nutritionType == null)
            super.setRecord(new NutritionType());
        else
            super.setRecord(nutritionType);
    }

    @Override
    protected Provider<NutritionType> initProvider() {
        return new NutritionTypeProvider();
    }

    @Override
    public RecordView<NutritionType> newView(NutritionType record) {
        return new NutritionTypeView(record);
    }
}
