package me.kangmin.swingy.view.menu.element;

public enum ArtifactElement implements MenuElement {
    ACQUIRE("획득"),
    DISCARD("버리기"),
    ;

    private final String description;

    ArtifactElement(String description) {
        this.description = description;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
}
