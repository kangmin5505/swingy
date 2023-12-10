package me.kangmin.swingy.entity;

import me.kangmin.swingy.entity.base.GameMap;
import me.kangmin.swingy.entity.base.Stage;
import me.kangmin.swingy.enums.PlayerType;

import java.io.Serializable;


public class Game implements Serializable {
    private Long id;
    private final Player player;
    private final Stage stage;
    private final GameMap gameMap;

    public void nextStage() {
        this.stage.nextStage();
        this.gameMap.nextStage();
    }

    // ========== constructor ==========
    public Game(String playerName, PlayerType playerType) {
        this.player = new Player(playerName, playerType);
        this.stage = new Stage();
        this.gameMap = new GameMap(this.player);
    }

    // ========== getter ==========
    public Stage getStage() {
        return this.stage;
    }

    public int getMapSize() {
        return this.gameMap.getMapSize();
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
}
