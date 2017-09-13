package de.odinoxin.aiddesk.plugins.rota;

import de.odinoxin.aidcloud.provider.Provider;
import de.odinoxin.aidcloud.provider.RotaProvider;
import de.odinoxin.aidcloud.service.ConcurrentFault_Exception;
import de.odinoxin.aiddesk.plugins.RecordEditor;
import de.odinoxin.aiddesk.plugins.RecordView;

public class RotaEditor extends RecordEditor<Rota> {

    public RotaEditor() {
        this(null);
    }

    public RotaEditor(Rota rota) {
        super("Rota");
        this.attemptLoadRecord(rota);
        if (rota == null)
            this.onNew();
    }

    @Override
    protected Rota onSave() throws ConcurrentFault_Exception {
        return this.getProvider().save(this.getRecord(), this.getOriginalRecord());
    }

    @Override
    protected boolean onDelete() {
        return this.getProvider().delete(this.getRecord().getId());
    }

    @Override
    protected void setRecord(Rota nutritionType) {
        if (nutritionType == null)
            super.setRecord(new Rota());
        else
            super.setRecord(nutritionType);
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
