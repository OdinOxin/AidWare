package de.odinoxin.aiddesk.plugins.countries;

import de.odinoxin.aidcloud.provider.CountryProvider;
import de.odinoxin.aidcloud.provider.Provider;
import de.odinoxin.aidcloud.service.ConcurrentFault_Exception;
import de.odinoxin.aiddesk.plugins.RecordEditor;
import de.odinoxin.aiddesk.plugins.RecordView;

public class CountryEditor extends RecordEditor<Country> {

    public CountryEditor(Country country) {
        super("Countries");
        this.attemptLoadRecord(country);
        if (country == null)
            this.onNew();
    }

    @Override
    protected Country onSave() throws ConcurrentFault_Exception {
        return this.getProvider().save(this.getRecord(), this.getOriginalRecord());
    }

    @Override
    protected boolean onDelete() {
        return this.getProvider().delete(this.getRecord().getId());
    }

    @Override
    protected void setRecord(Country country) {
        if (country == null)
            super.setRecord(new Country());
        else
            super.setRecord(country);
    }

    @Override
    protected Provider<Country> initProvider() {
        return new CountryProvider();
    }

    @Override
    public RecordView<Country> newView(Country record) {
        return new CountryView(record);
    }
}
