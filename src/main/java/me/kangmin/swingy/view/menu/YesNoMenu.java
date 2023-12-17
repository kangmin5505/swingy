package me.kangmin.swingy.view.menu;

import me.kangmin.swingy.view.menu.element.MenuElement;
import me.kangmin.swingy.view.menu.element.YesNoElement;

public class YesNoMenu implements Menu {
    @Override
    public MenuElement[] getElements() {
        return YesNoElement.values();
    }
}
