package de.odinoxin.aidware.aidcloud.provider;

import de.odinoxin.aidware.aiddesk.controls.refbox.RefBoxListItem;
import de.odinoxin.aidware.aiddesk.plugins.rota.TimestampInterpretation;
import de.odinoxin.aidware.aiddesk.plugins.rota.TimestampInterpretationEditor;

public class TimestampInterpretationProvider extends Provider<TimestampInterpretation> {

    @Override
    public RefBoxListItem<TimestampInterpretation> getRefBoxItem(TimestampInterpretation item) {
        if (item == null)
            return null;
        return new RefBoxListItem<>(item,
                item.getName(),
                "");
    }

    @Override
    public TimestampInterpretationEditor openEditor(TimestampInterpretation entity) {
        return new TimestampInterpretationEditor(entity);
    }
}
