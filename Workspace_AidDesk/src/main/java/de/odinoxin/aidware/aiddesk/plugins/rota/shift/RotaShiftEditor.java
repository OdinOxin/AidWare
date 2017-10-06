package de.odinoxin.aidware.aiddesk.plugins.rota.shift;

import de.odinoxin.aidware.aidcloud.provider.Provider;
import de.odinoxin.aidware.aidcloud.provider.RotaShiftProvider;
import de.odinoxin.aidware.aiddesk.plugins.RecordEditor;
import de.odinoxin.aidware.aiddesk.plugins.RecordView;

public class RotaShiftEditor extends RecordEditor<RotaShift> {

    public RotaShiftEditor() {
        this(null);
    }

    public RotaShiftEditor(RotaShift record) {
        super("RotaShift", record);
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
