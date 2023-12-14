package me.kangmin.swingy.enums;

public enum PlayerType {
    HUMANITIES("인문",3, 12, 12),                     // 인문
    SOCIAL_SCIENCES("사회", 5, 14, 8),                // 사회
    EDUCATION("교육", 4, 15, 8),                      // 교육
    NATURAL_SCIENCES("자연", 9, 9, 9),               // 자연
    ENGINEERING("공학", 15, 6, 6),                    // 공학
    MEDICINE("의약", 6, 10, 11),                       // 의약
    ARTS_AND_PHYSICAL_EDUCATION("예체능", 2, 8, 17),     // 예체능
    ;

    final String type;
    final int codingSkill;
    final int mentalStrength;
    final int health;

    PlayerType(String type, int codingSkill, int mentalStrength, int health) {
        this.type = type;
        this.codingSkill = codingSkill;
        this.mentalStrength = mentalStrength;
        this.health = health;
    }

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

}
