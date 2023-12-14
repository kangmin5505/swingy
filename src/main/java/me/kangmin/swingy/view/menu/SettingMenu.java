package me.kangmin.swingy.view.menu;

import me.kangmin.swingy.view.menu.element.MenuElement;
import me.kangmin.swingy.view.menu.element.SettingElement;

public class SettingMenu implements Menu {
    @Override
    public MenuElement[] getElements() {
        return SettingElement.values();
    }
}
