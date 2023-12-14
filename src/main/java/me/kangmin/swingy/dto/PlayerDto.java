package me.kangmin.swingy.dto;

public class PlayerDto {

    private final String name;
    private final String type;
    private final int codingSkill;
    private final int mentalStrength;
    private final int health;
    private final int level;

    private PlayerDto(String name, String type, int codingSkill, int mentalStrength, int health, int level) {
        this.name = name;
        this.type = type;
        this.codingSkill = codingSkill;
        this.mentalStrength = mentalStrength;
        this.health = health;
        this.level = level;
    }

    // ========== builder ==========
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String name;
        private String type;
        private int codingSkill;
        private int mentalStrength;
        private int health;
        private int level;

        Builder() {}

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder codingSkill(int codingSkill) {
            this.codingSkill = codingSkill;
            return this;
        }

        public Builder mentalStrength(int mentalStrength) {
            this.mentalStrength = mentalStrength;
            return this;
        }

        public Builder health(int health) {
            this.health = health;
            return this;
        }

        public Builder level(int level) {
            this.level = level;
            return this;
        }

        public PlayerDto build() {
            assert this.type != null;
            assert this.codingSkill > 0;
            assert this.mentalStrength > 0;
            assert this.health > 0;
            assert this.level >= 0;

            return new PlayerDto(this.name, this.type, this.codingSkill, this.mentalStrength, this.health, this.level);
        }

    }

    // ========== getter ==========
    public String getType() {
        return this.type;
    }

    public int getCodingSkill() {
        return this.codingSkill;
    }

    public int getMentalStrength() {
        return this.mentalStrength;
    }

    public int getHealth() {
        return this.health;
    }

    public int getLevel() {
        return level;
    }
}
