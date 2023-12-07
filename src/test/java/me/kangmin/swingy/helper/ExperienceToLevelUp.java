package me.kangmin.swingy.helper;

public enum ExperienceToLevelUp {
    ZERO(0),
    ONE(1000),
    TWO(2450),
    THREE(4800),
    FOUR(8050),
    FIVE(12200),
    SIX(17250),
    SEVEN(23200)
    ;

    private final int experience;

    ExperienceToLevelUp(int experience) {
        this.experience = experience;
    }

    public int getExperience() {
        return experience;
    }
}
