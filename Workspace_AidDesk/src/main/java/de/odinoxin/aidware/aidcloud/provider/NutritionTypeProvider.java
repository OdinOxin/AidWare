package de.odinoxin.aidware.aidcloud.provider;

import de.odinoxin.aidware.aiddesk.controls.refbox.RefBoxListItem;
import de.odinoxin.aidware.aiddesk.plugins.nutritiontype.NutritionType;
import de.odinoxin.aidware.aiddesk.plugins.nutritiontype.NutritionTypeEditor;

public class NutritionTypeProvider extends Provider<NutritionType> {

    @Override
    public RefBoxListItem<NutritionType> getRefBoxItem(NutritionType item) {
        if (item == null)
            return null;
        return new RefBoxListItem<>(item,
                (item.getName() == null ? "" : item.getName()),
                "");
    }

    @Override
    public NutritionTypeEditor openEditor(NutritionType entity) {
        return new NutritionTypeEditor(entity);
    }
}
