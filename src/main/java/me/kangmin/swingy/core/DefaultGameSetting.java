package me.kangmin.swingy.core;

import me.kangmin.swingy.enums.ViewMode;

public class DefaultGameSetting extends GameSetting {
    public DefaultGameSetting(ViewMode viewMode) {
        super(viewMode);
    }

    @Override
    public void changeViewMode() {
        this.viewMode = this.viewMode == ViewMode.CONSOLE ? ViewMode.GUI : ViewMode.CONSOLE;
    }
}
