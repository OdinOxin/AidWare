package de.odinoxin.aidcloud.plugins;

import java.util.Date;

public abstract class RecordableComparer {
    public static boolean Equals(Recordable rec1, Recordable rec2) {
        return (rec1 == null && rec2 == null) || (rec1 != null && rec2 != null && rec1.equals(rec2));
    }

    public static boolean Equals(String str1, String str2) {
        return (str1 == null && str2 == null) || (str1 != null && str2 != null && str1.equals(str2));
    }

    public static boolean Equals(Date d1, Date d2) {
        return (d1 == null && d2 == null) || (d1 != null && d2 != null && d1.compareTo(d2) == 0);
    }
}
