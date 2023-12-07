package me.kangmin.swingy.core;

import me.kangmin.swingy.enums.ViewMode;

public abstract class GameSetting {
    protected ViewMode viewMode;

    abstract void changeViewMode();

    // ========== constructor ==========
    public GameSetting(ViewMode viewMode) {
        this.viewMode = viewMode;
    }

    // ========== getter ==========
    public ViewMode getViewMode() {
        return this.viewMode;
    }
}
