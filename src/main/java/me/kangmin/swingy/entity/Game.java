package me.kangmin.swingy.entity;

import me.kangmin.swingy.entity.base.GameMap;
import me.kangmin.swingy.entity.base.Stage;
import me.kangmin.swingy.entity.base.artifacts.ArtifactRank;
import me.kangmin.swingy.entity.base.artifacts.Headset;
import me.kangmin.swingy.entity.base.artifacts.Hoodie;
import me.kangmin.swingy.entity.base.artifacts.Keyboard;
import me.kangmin.swingy.enums.PlayerType;
import me.kangmin.swingy.enums.SubjectType;

import java.io.Serializable;
import java.util.Random;


public class Game implements Serializable {

    private static final long serialVersionUID = -134238050549244309L;
    private Long id;
    private final Player player;
    private final Stage stage;
    private final GameMap gameMap;
    private Artifact artifact;

    public void nextStage() {
        this.stage.nextStage();
        this.gameMap.nextStage();
    }

    // ========== constructor ==========
    public Game(PlayerType playerType) {
        this.player = new Player(playerType);
        this.stage = new Stage();
        this.gameMap = new GameMap(this.player);
    }

    // ========== getter ==========
    public Stage getStage() {
        return this.stage;
    }

    public Player getPlayer() {
        return this.player;
    }

    public GameMap getGameMap() {
        return this.gameMap;
    }

    public boolean studySubject() {
        // TODO : 알고리즘
//        Stat stat = this.player.getStat();
//        int codingSkill = stat.getCodingSkill();
//        int mentalStrength = stat.getMentalStrength();
//        int experience = this.player.getExperience();
//        int neededExperience = this.player.getNeededExperience();
//        int stageLevel = this.stage.getStage();
//
//        int experienceRate = (int)(((double) experience / neededExperience) * 100);
//        if (experienceRate + codingSkill < stageLevel * 25) {
//           double v = ((double) 84L - (double) mentalStrength) / 84L;
//            Random random = new Random();
//            boolean b = random.nextDouble() < v;
//            if (b) {
//                this.player.decreaseHealth();
//            }
//        }
//        return this.player.isDie();
        this.finishSubject();
        return true;
    }

    public boolean cheatSubject() {
        boolean isSuccess = Math.random() <= 0.5;

        if (isSuccess) {
            this.finishSubject();
            return true;
        }
        return this.studySubject();
    }

    private void finishSubject() {
        this.player.studySubject(this.stage, this.gameMap.getSubjectType());
        this.gameMap.removeSubject();
    }

    public void setPlayerName(String input) {
        this.player.setName(input);
    }

    public Artifact getArtifact() {
        int stage = this.stage.getStage();
        if (Math.random() >= 0.3 + (stage * 0.1)) {
            return null;
        }

        ArtifactRank rank = this.getArtifactRank(stage);
        artifact = this.createArtifact(rank);

        return artifact;
    }

    private Artifact createArtifact(ArtifactRank rank) {
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

    private ArtifactRank getArtifactRank(int stage) {
        if (stage < 3) {
            return ArtifactRank.COMMON;
        } else if (stage < 6) {
            return ArtifactRank.RARE;
        } else {
            return ArtifactRank.LEGENDARY;
        }
    }

    public void acquireArtifact() {
        this.player.addArtifact(this.artifact);
    }

    public void discardArtifact() {
        this.artifact = null;
    }

    public SubjectType getSubjectType() {
        return this.gameMap.getSubjectType();
    }
}
