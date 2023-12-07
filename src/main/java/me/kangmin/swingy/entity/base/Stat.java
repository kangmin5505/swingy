package me.kangmin.swingy.entity.base;

import me.kangmin.swingy.enums.PlayerType;

import java.io.Serializable;

public class Stat implements Serializable {
    public static final int PURE_STAT_LIMIT = 27;
    public static final int MAX_STAT_LIMIT = 42;
    private int codingSkill;
    private int mentalStrength;
    private int health;
    private int currentHealth;
    private final int codingSkillIncrementAmount;
    private final int mentalStrengthIncrementAmount;
    private final int healthIncrementAmount;

    public void levelUp() {
        this.codingSkill = Math.min(this.codingSkill + this.codingSkillIncrementAmount, PURE_STAT_LIMIT);
        this.mentalStrength = Math.min(this.mentalStrength + this.mentalStrengthIncrementAmount, PURE_STAT_LIMIT);
        this.health = Math.min(this.health + this.healthIncrementAmount, PURE_STAT_LIMIT);
        this.currentHealth = this.health;
    }

    public void decreaseHealth() {
        this.currentHealth--;
    }

    public boolean isDie() {
        return this.currentHealth <= 0;
    }

    // ========== constructor ==========
    public Stat(PlayerType playerType) {
        this.codingSkill = playerType.getCodingSkill();
        this.mentalStrength = playerType.getMentalStrength();
        this.health = playerType.getHealth();
        this.currentHealth = this.health;

        this.codingSkillIncrementAmount = this.calculateIncrementAmount(codingSkill);
        this.mentalStrengthIncrementAmount = this.calculateIncrementAmount(mentalStrength);
        this.healthIncrementAmount = this.calculateIncrementAmount(health);
    }

    // ========== private methods ==========
    private int calculateIncrementAmount(int initialStat) {
        int difference = PURE_STAT_LIMIT - initialStat;
        double divisionResult = (double) difference / Level.MAX_LEVEL;
        return (int) Math.ceil(divisionResult);
    }

    // ========== getter && setter ==========

    public int getCodingSkill() {
        return this.codingSkill;
    }

    public int getMentalStrength() {
        return this.mentalStrength;
    }

    public int getHealth() {
        return this.health;
    }
    public int getCurrentHealth() {
        return this.currentHealth;
    }
}