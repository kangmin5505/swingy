package me.kangmin.swingy.entity;

import me.kangmin.swingy.entity.base.Level;
import me.kangmin.swingy.entity.base.Stage;
import me.kangmin.swingy.entity.base.Stat;
import me.kangmin.swingy.enums.ArtifactType;
import me.kangmin.swingy.enums.PlayerType;
import me.kangmin.swingy.enums.SubjectType;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Player implements Serializable {

    private static final long serialVersionUID = -1097161337268327065L;
    private Long id;
    private String name;
    private final String type;
    private final Level level;
    private final Stat stat;
    private final Map<ArtifactType, Artifact> artifacts = new HashMap<>();

    public void decreaseHealth(int damage) {
        this.stat.decreaseHealth(damage);
    }

    public boolean isDie() {
        return this.stat.isDie();
    }

    // ========== constructor ==========
    public Player(PlayerType playerType) {
        this.type =  playerType.getType();
        this.stat = new Stat(playerType);
        this.level = new Level(this.stat);
    }

    // ========== getter ==========
    public String getType() {
        return this.type;
    }

    public int getLevel() {
        return this.level.getLevel();
    }

    public int getExperience() {
        return this.level.getExperience();
    }

    public int getTotalNeededExperience() {
        return this.level.getTotalNeededExperience();
    }

    public Stat getStat() {
        return this.stat;
    }


    public String getName() {
        return this.name;
    }

    public void studySubject(Stage stage, SubjectType subjectType) {
        this.level.studySubject(stage, subjectType);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addArtifact(Artifact artifact) {
        this.artifacts.put(artifact.getArtifactType(), artifact);
        this.stat.addArtifact(artifact);
    }

    public Map<ArtifactType, Artifact> getArtifacts() {
        return this.artifacts;
    }
}
