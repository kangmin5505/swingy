package me.kangmin.swingy.view.menu;


import me.kangmin.swingy.view.menu.element.MenuElement;
import me.kangmin.swingy.view.menu.element.MoveElement;

public class MoveMenu implements Menu {

    @Override
    public MenuElement[] getElements() {
        return MoveElement.values();
    }
}