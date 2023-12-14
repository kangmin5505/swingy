package me.kangmin.swingy.view.menu.element;

public class PlayerElement implements MenuElement {
    private final String description;

    public PlayerElement(String description) {
        this.description = description;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
}
