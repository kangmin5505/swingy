package me.kangmin.swingy.view.menu.element;

public enum StudyMethodElement implements MenuElement{
    STUDY("학습"),
    CHEAT("치팅")
    ;

    private final String description;

    StudyMethodElement(String description) {
        this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
