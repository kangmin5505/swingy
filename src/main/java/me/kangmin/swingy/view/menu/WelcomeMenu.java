package me.kangmin.swingy.view.menu;

import me.kangmin.swingy.view.menu.element.MenuElement;
import me.kangmin.swingy.view.menu.element.WelcomeElement;

public class WelcomeMenu implements Menu {

    @Override
    public MenuElement[] getElements() {
        return WelcomeElement.values();
    }
}
