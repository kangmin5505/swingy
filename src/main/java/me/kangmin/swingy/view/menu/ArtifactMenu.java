package me.kangmin.swingy.view.menu;

import me.kangmin.swingy.view.menu.element.ArtifactElement;
import me.kangmin.swingy.view.menu.element.MenuElement;

public class ArtifactMenu implements Menu {
    @Override
    public MenuElement[] getElements() {
        return ArtifactElement.values();
    }
}
