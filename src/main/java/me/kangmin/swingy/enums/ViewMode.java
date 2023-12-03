package me.kangmin.swingy.enums;

public enum ViewMode {
    GUI("gui"),
    CONSOLE("console")
    ;

    private final String mode;

    ViewMode(String mode) {
        this.mode = mode;
    }
}
