package me.kangmin.swingy.view.menu;

import me.kangmin.swingy.view.menu.element.MenuElement;
import me.kangmin.swingy.view.menu.element.StudyMethodElement;

public class StudyMethodMenu implements Menu {

    @Override
    public MenuElement[] getElements() {
        return StudyMethodElement.values();
    }
}
