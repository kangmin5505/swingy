package me.kangmin.swingy.entity.base.artifacts;

public enum ArtifactRank {
    COMMON(1, "COMMON"),
    RARE(2, "RARE"),
    LEGENDARY(3, "LEGENDARY")
    ;

    private final int multiplier;
    private final String name;

    ArtifactRank(int multiplier, String name) {
        this.multiplier = multiplier;
        this.name = name;
    }

    public int getMultiplier() {
        return multiplier;
    }

    public String getName() {
        return name;
    }
}
