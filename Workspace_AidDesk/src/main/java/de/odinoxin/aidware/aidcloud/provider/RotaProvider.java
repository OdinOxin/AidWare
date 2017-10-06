package de.odinoxin.aidware.aidcloud.provider;

import de.odinoxin.aidware.aiddesk.controls.refbox.RefBoxListItem;
import de.odinoxin.aidware.aiddesk.plugins.rota.Rota;
import de.odinoxin.aidware.aiddesk.plugins.rota.RotaEditor;

public class RotaProvider extends Provider<Rota> {

    @Override
    public RefBoxListItem<Rota> getRefBoxItem(Rota item) {
        if (item == null)
            return null;
        return new RefBoxListItem<>(item,
                (item.getTitle() == null ? "" : item.getTitle()),
                (item.getRotaCategory() == null ? "" : item.getRotaCategory().getName()));
    }

    @Override
    public RotaEditor openEditor(Rota entity) {
        return new RotaEditor(entity);
    }
}
