package me.kangmin.swingy.enums;

public enum SubjectType {
    STUDY("학습"),
    CHEAT("치팅")
    ;

    private final String description;

    SubjectType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
