package me.kangmin.swingy.enums.menu;

public interface Menu<T> {
    String getDescription();
    T[] getValues();
}
