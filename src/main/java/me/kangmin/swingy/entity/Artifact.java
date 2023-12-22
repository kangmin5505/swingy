package me.kangmin.swingy.entity;

import me.kangmin.swingy.entity.base.artifacts.ArtifactRank;
import me.kangmin.swingy.entity.base.artifacts.Headset;
import me.kangmin.swingy.entity.base.artifacts.Hoodie;
import me.kangmin.swingy.entity.base.artifacts.Keyboard;
import me.kangmin.swingy.enums.ArtifactType;

import java.io.Serializable;
import java.util.Random;

public abstract class Artifact implements Serializable {
    private static final long serialVersionUID = 2653493730014838370L;
    private long id;
    private final ArtifactType artifactType;
    private final String rank;
    private final int codingSkill;
    private final int mentalStrength;
    private final int health;
    private static Artifact tempArtifactHolder;

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

    public static Artifact getArtifact(int stage) {
        if (Math.random() >= 0.3 + (stage * 0.1)) {
            return null;
        }

        ArtifactRank rank = getArtifactRank(stage);
        tempArtifactHolder = createArtifact(rank);

        return tempArtifactHolder;
    }

    private static Artifact createArtifact(ArtifactRank rank) {
        int randomNumber = new Random().nextInt(3);
        switch (randomNumber) {
            case 0:
                return new Headset(rank);
            case 1:
                return new Keyboard(rank);
            default:
                return new Hoodie(rank);
        }
    }

    private static ArtifactRank getArtifactRank(int stage) {
        if (stage < 3) {
            return ArtifactRank.COMMON;
        } else if (stage < 6) {
            return ArtifactRank.RARE;
        } else {
            return ArtifactRank.LEGENDARY;
        }
    }
    public static Artifact acquireArtifact() {
        Artifact artifact = tempArtifactHolder;
        tempArtifactHolder = null;

        return artifact;
    }

    public static void discardArtifact() {
        tempArtifactHolder = null;
    }
}
