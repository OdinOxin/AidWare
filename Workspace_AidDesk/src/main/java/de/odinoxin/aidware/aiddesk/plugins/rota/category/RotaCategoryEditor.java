package de.odinoxin.aidware.aiddesk.plugins.rota.category;

import de.odinoxin.aidware.aidcloud.provider.Provider;
import de.odinoxin.aidware.aidcloud.provider.RotaCategoryProvider;
import de.odinoxin.aidware.aiddesk.plugins.RecordEditor;
import de.odinoxin.aidware.aiddesk.plugins.RecordView;

public class RotaCategoryEditor extends RecordEditor<RotaCategory> {

    public RotaCategoryEditor() {
        this(null);
    }

    public RotaCategoryEditor(RotaCategory record) {
        super("RotaCategory", record);
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
