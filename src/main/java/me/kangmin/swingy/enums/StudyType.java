package me.kangmin.swingy.enums;

public enum StudyType {
    STUDY("학습"),
    CHEAT("치팅")
    ;

    private final String description;

    StudyType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
