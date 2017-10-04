package de.odinoxin.aidware.aidcloud.plugins;

import java.util.Date;
import java.util.List;

public abstract class RecordableComparer {

    public static boolean Equals(int int1, int int2) {
        return int1 == int2;
    }

    public static boolean Equals(Recordable rec1, Recordable rec2) {
        return (rec1 == null && rec2 == null) || (rec1 != null && rec2 != null && rec1.getId() == rec2.getId());
    }

    public static boolean Equals(String str1, String str2) {
        return (str1 == null && str2 == null) || (str1 != null && str2 != null && str1.equals(str2));
    }

    public static boolean Equals(Date d1, Date d2) {
        return (d1 == null && d2 == null) || (d1 != null && d2 != null && d1.compareTo(d2) == 0);
    }

    public static boolean Equals(List<? extends Recordable> list1, List<? extends Recordable> list2) {
        if (list1 != null && list2 != null) {
            if (list1.size() == list2.size())
                for (int i = 0; i < list1.size(); i++)
                    if (list1.get(i) == null || list2.get(i) == null || list1.get(i).getId() != list2.get(i).getId())
                        return false;
            return true;
        } else if (list1 == null && list2 == null)
            return true;
        return false;
    }
}
