package me.kangmin.swingy.enums.menu;

public enum AcquireArtifactMenu implements Menu<AcquireArtifactMenu> {
    ACQUIRE("획득"),
    DISCARD("버리기"),
    ;

    private final String description;

    AcquireArtifactMenu(String description) {
        this.description = description;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public AcquireArtifactMenu[] getValues() {
        return AcquireArtifactMenu.values();
    }
}
