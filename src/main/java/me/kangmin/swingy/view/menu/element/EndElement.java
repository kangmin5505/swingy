package me.kangmin.swingy.view.menu.element;

public enum EndElement implements MenuElement{
    MAIN("메인으로");


    private final String description;

    EndElement(String description) {
        this.description = description;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
}
