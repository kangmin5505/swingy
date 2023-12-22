package me.kangmin.swingy.dto;

import me.kangmin.swingy.entity.Game;
import me.kangmin.swingy.entity.Player;
import me.kangmin.swingy.entity.base.Coordinate;
import me.kangmin.swingy.entity.base.GameMap;
import me.kangmin.swingy.entity.base.Stage;

import java.util.List;

public class GameInfoDto {
    private final Player player;
    private final GameMap gameMap;
    private final Stage stage;

    public GameInfoDto(Game game) {
        this.player = game.getPlayer();
        this.gameMap = game.getGameMap();
        this.stage = game.getStage();
    }

    public Coordinate getPlayerCoordinate() {
        return this.gameMap.getPlayerCoordinate();
    }

    public Coordinate getEnemyBossCoordinate() {
        return this.gameMap.getMainSubjectCoordinate();
    }

    public List<Coordinate> getEnemyCoordinates() {
        return this.gameMap.getSubSubjectCoordinates();
    }

    public int getMapSize() {
        return this.gameMap.getMapSize();
    }

    public int getStage() {
        return this.stage.getStageLevel();
    }

    public String getBossName() {
        return this.stage.getBossName();
    }

    public Player getPlayer() {
        return this.player;
    }

    public int getTotalStage() {
        return this.stage.getTotalStage();
    }
}
