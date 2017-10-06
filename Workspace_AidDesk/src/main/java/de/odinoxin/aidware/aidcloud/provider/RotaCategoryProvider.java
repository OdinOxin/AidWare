package de.odinoxin.aidware.aidcloud.provider;

import de.odinoxin.aidware.aiddesk.controls.refbox.RefBoxListItem;
import de.odinoxin.aidware.aiddesk.plugins.rota.category.RotaCategory;
import de.odinoxin.aidware.aiddesk.plugins.rota.category.RotaCategoryEditor;

public class RotaCategoryProvider extends Provider<RotaCategory> {

    @Override
    public RefBoxListItem<RotaCategory> getRefBoxItem(RotaCategory item) {
        if (item == null)
            return null;
        return new RefBoxListItem<>(item,
                (item.getName() == null ? "" : item.getName()),
                "");
    }

    @Override
    public RotaCategoryEditor openEditor(RotaCategory entity) {
        return new RotaCategoryEditor(entity);
    }
}
