package me.kangmin.swingy.enums;

public enum ArtifactType {
    HEADSET("헤드셋"),
    KEYBOARD("키보드"),
    HOODIE("후드티");

    private final String name;

    ArtifactType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
