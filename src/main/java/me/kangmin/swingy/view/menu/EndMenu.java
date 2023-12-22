package me.kangmin.swingy.view.menu;

import me.kangmin.swingy.view.menu.element.EndElement;
import me.kangmin.swingy.view.menu.element.MenuElement;

public class EndMenu implements Menu {
    @Override
    public MenuElement[] getElements() {
        return EndElement.values();
    }
}
