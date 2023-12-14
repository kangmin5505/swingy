package me.kangmin.swingy.view.menu;

import me.kangmin.swingy.view.menu.element.MenuElement;
import me.kangmin.swingy.view.menu.element.ResetDataElement;

public class ResetDataMenu implements Menu {
    @Override
    public MenuElement[] getElements() {
        return ResetDataElement.values();
    }
}
