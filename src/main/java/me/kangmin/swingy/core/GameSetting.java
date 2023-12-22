package me.kangmin.swingy.core;

import me.kangmin.swingy.enums.ViewMode;

public class GameSetting {
    private ViewMode viewMode;

    void changeViewMode() {
        this.viewMode = (this.viewMode == ViewMode.CONSOLE) ? ViewMode.GUI : ViewMode.CONSOLE;
    }

    public GameSetting(ViewMode viewMode) {
        this.viewMode = viewMode;
    }

    public ViewMode getViewMode() {
        return this.viewMode;
    }
}
