package me.kangmin.swingy.entity;

import me.kangmin.swingy.entity.base.artifacts.ArtifactRank;
import me.kangmin.swingy.enums.ArtifactType;

import java.io.Serializable;

public abstract class Artifact implements Serializable {
    private long id;
    private final ArtifactType artifactType;
    private final String rank;
    private final int codingSkill;
    private final int mentalStrength;
    private final int health;

    public static int calcDegree(ArtifactRank rank, int degree) {
        return rank.getMultiplier() * degree;
    }

    // ========== constructor ==========
    public Artifact(ArtifactType artifactType, ArtifactRank rank, int codingSkillDegree, int mentalStrengthDegree, int healthDegree) {
        this.artifactType = artifactType;
        this.rank = rank.getName();
        this.codingSkill = codingSkillDegree;
        this.mentalStrength = mentalStrengthDegree;
        this.health = healthDegree;
    }

    // ========== getter && setter ==========
    public long getId() {
        return this.id;
    }

    public ArtifactType getArtifactType() {
        return this.artifactType;
    }

    public String getRank() {
        return this.rank;
    }

    public int getCodingSkill() {
        return this.codingSkill;
    }

    public int getMentalStrength() {
        return this.mentalStrength;
    }

    public int getHealth() {
        return health;
    }
}
