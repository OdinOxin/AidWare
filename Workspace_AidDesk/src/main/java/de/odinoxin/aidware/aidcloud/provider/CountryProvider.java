package de.odinoxin.aidware.aidcloud.provider;

import de.odinoxin.aidware.aiddesk.controls.refbox.RefBoxListItem;
import de.odinoxin.aidware.aiddesk.plugins.countries.Country;
import de.odinoxin.aidware.aiddesk.plugins.countries.CountryEditor;

public class CountryProvider extends Provider<Country> {

    @Override
    public RefBoxListItem<Country> getRefBoxItem(Country item) {
        if (item == null)
            return null;
        return new RefBoxListItem<>(item,
                (item.getName() == null ? "" : item.getName()),
                (item.getAlpha2() == null ? "" : item.getAlpha2()) + " " +
                        (item.getAlpha3() == null ? "" : item.getAlpha3()) + " " +
                        (item.getAreaCode() == null ? "" : item.getAreaCode()));
    }

    @Override
    public CountryEditor openEditor(Country entity) {
        return new CountryEditor(entity);
    }
}
