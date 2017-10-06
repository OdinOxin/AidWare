package de.odinoxin.aidware.aiddesk.plugins.rota;

import de.odinoxin.aidware.aidcloud.provider.Provider;
import de.odinoxin.aidware.aidcloud.provider.RotaProvider;
import de.odinoxin.aidware.aiddesk.plugins.RecordEditor;
import de.odinoxin.aidware.aiddesk.plugins.RecordView;

public class RotaEditor extends RecordEditor<Rota> {

    public RotaEditor() {
        this(null);
    }

    public RotaEditor(Rota record) {
        super("Rota", record);
    }

    @Override
    protected void setRecord(Rota rota) {
        if (rota == null)
            super.setRecord(new Rota());
        else
            super.setRecord(rota);
    }

    @Override
    protected Provider<Rota> initProvider() {
        return new RotaProvider();
    }

    @Override
    public RecordView<Rota> newView(Rota record) {
        return new RotaView(record);
    }
}
