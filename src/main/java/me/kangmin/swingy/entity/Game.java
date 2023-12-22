package me.kangmin.swingy.entity;

import me.kangmin.swingy.entity.base.GameMap;
import me.kangmin.swingy.entity.base.Stage;
import me.kangmin.swingy.enums.PlayerType;
import me.kangmin.swingy.enums.SubjectType;
import me.kangmin.swingy.view.menu.element.MoveElement;

import java.io.Serializable;


public class Game implements Serializable {

    private static final long serialVersionUID = -134238050549244309L;
    private Long id;
    private final Player player;
    private final Stage stage;
    private final GameMap gameMap;

    public void nextStage() {
        this.stage.nextStage();
        this.gameMap.nextStage();
    }

    // ========== constructor ==========
    public Game(PlayerType playerType) {
        this.player = new Player(playerType);
        this.stage = new Stage();
        this.gameMap = new GameMap(this.player, this.stage);
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
        int subSubjectCnt = this.gameMap.getSubSubjectCoordinates().size();
        int stageLevel = this.stage.getStageLevel();

        if (this.gameMap.getSubjectType() == SubjectType.MAIN && stageLevel > 0) {
            double winRatio = (double) (stageLevel - subSubjectCnt) / stageLevel;
            int codingSkill = this.player.getStat().getCodingSkill();
            int mentalStrength = this.player.getStat().getMentalStrength();
            winRatio += codingSkill * 0.0015 + mentalStrength * 0.001;

            if (Math.random() > winRatio) {
                return false;
            }
        } else {
            int damage = (stageLevel - player.getLevel()) * 2 + 2;
            this.player.decreaseHealth(damage);

            if (this.player.isDie()) {
                return false;
            }
        }
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
        int stage = this.stage.getStageLevel();

        return Artifact.getArtifact(stage);
    }


    public void acquireArtifact() {
        Artifact artifact = Artifact.acquireArtifact();
        this.player.addArtifact(artifact);
    }

    public void discardArtifact() {
        Artifact.discardArtifact();
    }

    public SubjectType getSubjectType() {
        return this.gameMap.getSubjectType();
    }

    public boolean movePlayer(MoveElement moveElement) {
        return this.gameMap.movePlayer(moveElement);
    }
}
