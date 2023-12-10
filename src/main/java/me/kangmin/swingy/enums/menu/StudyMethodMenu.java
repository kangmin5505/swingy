package me.kangmin.swingy.enums.menu;

public enum StudyMethodMenu implements Menu<StudyMethodMenu> {
    STUDY("학습"),
    CHEAT("치팅")
    ;

    private final String description;

    StudyMethodMenu(String description) {
        this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public StudyMethodMenu[] getValues() {
        return StudyMethodMenu.values();
    }
}
