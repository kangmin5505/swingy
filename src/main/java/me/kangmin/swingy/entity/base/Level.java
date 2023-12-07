package me.kangmin.swingy.entity.base;

import java.io.Serializable;

public class Level implements Serializable {
    private static final int INITIAL_LEVEL = 0;
    private static final int INITIAL_EXPERIENCE = 0;
    public static final int MAX_LEVEL = 7;
    private int level;
    private int experience;

    public void increaseExperience(int experience) {
        this.experience += experience;
        if (this.isLevelUp()) {
            this.levelUp();
        }
    }


    // ========== constructor ==========

    public Level() {
        this.level = INITIAL_LEVEL;
        this.experience = INITIAL_EXPERIENCE;
    }

    //  ========== private ==========

    private boolean isLevelUp() {
        return this.calculateLevelUpFormula() <= this.experience;
    }

    private void levelUp() {
        this.experience -= this.calculateLevelUpFormula();
        this.level++;
    }

    private int calculateLevelUpFormula() {
        return (this.level + 1) * 1000 + this.level * this.level * 450;
    }

    // ========== getter ==========
    public int getLevel() {
        return this.level;
    }

    public int getExperience() {
        return this.experience;
    }
    public int getNeededExperience() {
        return this.calculateLevelUpFormula();
    }
}
