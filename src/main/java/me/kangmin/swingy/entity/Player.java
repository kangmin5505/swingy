package me.kangmin.swingy.entity;

import me.kangmin.swingy.entity.base.Level;
import me.kangmin.swingy.entity.base.Stage;
import me.kangmin.swingy.entity.base.Stat;
import me.kangmin.swingy.enums.PlayerType;
import me.kangmin.swingy.enums.SubjectType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Player implements Serializable {

    private Long id;
    private final String name;
    private final String type;
    private final Level level;
    private final Stat stat;
    private final List<Artifact> artifacts = new ArrayList<>();

    public void decreaseHealth() {
        this.stat.decreaseHealth();
    }

    public boolean isDie() {
        return this.stat.isDie();
    }

    // ========== constructor ==========
    public Player(String playerName, PlayerType playerType) {
        this.name = playerName;
        this.type =  playerType.getType();
        this.level = new Level();
        this.stat = new Stat(playerType);
    }

    // ========== getter ==========

    public Long getId() {
        return this.id;
    }

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

    public List<Artifact> getArtifacts() {
        return this.artifacts;
    }

    public String getName() {
        return this.name;
    }

    public void studySubject(Stage stage, SubjectType subjectType) {
        this.level.studySubject(stage, subjectType);
    }
}
