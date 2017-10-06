package de.odinoxin.aidware.aidcloud.recordable;

public interface Recordable extends Cloneable {
    public abstract int getId();
    public abstract void setId(int id);
    public abstract Object clone();
}
