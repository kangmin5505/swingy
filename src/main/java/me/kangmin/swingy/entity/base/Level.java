package me.kangmin.swingy.entity.base;

import me.kangmin.swingy.entity.Player;
import me.kangmin.swingy.enums.SubjectType;

import java.io.Serializable;

public class Level implements Serializable {
    private static final int INITIAL_LEVEL = 0;
    private static final int INITIAL_EXPERIENCE = 0;
    public static final int MAX_LEVEL = 7;
    private static final int MAIN_SUBJECT_CNT = 3;
    private int level;
    private int experience;

    private void increaseExperience(int experience) {
        this.experience += experience;
        if (this.isLevelUp()) {
            this.levelUp();
        }
    }

    public void studySubject(Stage stage, SubjectType subjectType) {
        int totalCnt = stage.getTotalSubSubjectCnt() + MAIN_SUBJECT_CNT;
        int totalNeededExperience = this.getTotalNeededExperience();
        int experience = (int) Math.ceil((double) totalNeededExperience / totalCnt);

        if (subjectType == SubjectType.MAIN) {
            experience *= MAIN_SUBJECT_CNT;
        }

        this.increaseExperience(experience);
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
    public int getTotalNeededExperience() {
        return this.calculateLevelUpFormula();
    }
}
