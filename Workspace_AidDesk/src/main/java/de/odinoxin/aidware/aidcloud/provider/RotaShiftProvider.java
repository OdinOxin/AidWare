package de.odinoxin.aidware.aidcloud.provider;

import de.odinoxin.aidware.aiddesk.controls.refbox.RefBoxListItem;
import de.odinoxin.aidware.aiddesk.plugins.rota.shift.RotaShift;
import de.odinoxin.aidware.aiddesk.plugins.rota.shift.RotaShiftEditor;

import java.text.DateFormat;
import java.util.Locale;

public class RotaShiftProvider extends Provider<RotaShift> {

    @Override
    public RefBoxListItem<RotaShift> getRefBoxItem(RotaShift item) {
        if (item == null)
            return null;
        String format = item.getTsBeginn() == null || item.getTsEnd() == null ? "%s%s" : "%s - %s";
        DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.getDefault());
        return new RefBoxListItem<>(item,
                String.format(format, item.getTsBeginn() == null ? "" : dateFormatter.format(item.getTsBeginn()), item.getTsEnd() == null ? "" : dateFormatter.format(item.getTsEnd())),
                "");
    }

    @Override
    public RotaShiftEditor openEditor(RotaShift entity) {
        return new RotaShiftEditor(entity);
    }
}
