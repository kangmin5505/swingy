package me.kangmin.swingy.view.menu;

import me.kangmin.swingy.view.menu.element.MenuElement;
import me.kangmin.swingy.view.menu.element.SaveGameElement;

public class SaveGameMenu implements Menu {
    @Override
    public MenuElement[] getElements() {
        return SaveGameElement.values();
    }
}
