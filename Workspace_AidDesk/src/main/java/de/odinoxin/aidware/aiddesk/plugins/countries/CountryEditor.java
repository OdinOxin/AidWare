package de.odinoxin.aidware.aiddesk.plugins.countries;

import de.odinoxin.aidware.aidcloud.provider.CountryProvider;
import de.odinoxin.aidware.aidcloud.provider.Provider;
import de.odinoxin.aidware.aiddesk.plugins.RecordEditor;
import de.odinoxin.aidware.aiddesk.plugins.RecordView;

public class CountryEditor extends RecordEditor<Country> {

    public CountryEditor() {
        this(null);
    }

    public CountryEditor(Country record) {
        super("Countries", record);
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
