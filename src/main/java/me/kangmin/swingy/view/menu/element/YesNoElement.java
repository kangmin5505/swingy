package me.kangmin.swingy.view.menu.element;

public enum YesNoElement implements MenuElement {
    YES("예"),
    NO("아니오");

    private final String description;

    YesNoElement(String description) {
        this.description = description;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
}
