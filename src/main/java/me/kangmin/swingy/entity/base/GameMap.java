package me.kangmin.swingy.entity.base;

import me.kangmin.swingy.entity.Player;
import me.kangmin.swingy.enums.SubjectType;
import me.kangmin.swingy.view.menu.element.MoveElement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameMap implements Serializable {
    private static final long serialVersionUID = 5408268936227484672L;
    private final Stage stage;
    private int mapSize;
    private final Player player;
    private Coordinate playerCoordinate;
    private Coordinate mainSubjectCoordinate;
    private List<Coordinate> subSubjectCoordinates;

    public void nextStage() {
        this.mapSize = this.calculateMapSize();
        this.setEntityCoordinates();
    }

    public boolean movePlayer(MoveElement moveElement) {
        if (this.isCanMovePlayer(moveElement)) {
            this.playerCoordinate.moveBy(moveElement.getDeltaX(), moveElement.getDeltaY());
        }
        return this.isCollisionToSubject();
    }

    public void removeSubject() {
        subSubjectCoordinates.remove(this.playerCoordinate);
    }

    // ========== constructor ==========
    public GameMap(Player player, Stage stage) {
        this.player = player;
        this.stage = stage;
        this.mapSize = this.calculateMapSize();
        this.setEntityCoordinates();
    }

    private void setEntityCoordinates() {
        this.subSubjectCoordinates = new ArrayList<>();

        this.playerCoordinate = new Coordinate(this.mapSize / 2, this.mapSize / 2);
        this.mainSubjectCoordinate = new Coordinate(this.mapSize - 1, 0);


        for (int i = 0; i < this.stage.getStageLevel(); i++) {
            this.subSubjectCoordinates.add(this.getSubSubjectCoordinate());
        }
    }

    private Coordinate getSubSubjectCoordinate() {
        Random random = new Random();
        int x;
        int y;

        do {
            x = random.nextInt(this.mapSize);
            y = random.nextInt(this.mapSize);
        } while ((x == this.mainSubjectCoordinate.getX() && y == this.mainSubjectCoordinate.getY()) ||
                (x == this.playerCoordinate.getX() && y == this.playerCoordinate.getY()));

        return new Coordinate(x, y);
    }

    private boolean isCollisionToSubject() {
        return this.isCollisionWithSubSubject() || this.isCollisionWithMainSubject();
    }

    private boolean isCollisionWithMainSubject() {
        return this.mainSubjectCoordinate.equals(this.playerCoordinate);
    }

    private boolean isCollisionWithSubSubject() {
        return this.subSubjectCoordinates.stream()
                                         .anyMatch(coordinate -> coordinate.equals(this.playerCoordinate));
    }

    // ========== private method ==========
    private int calculateMapSize() {
        int playerLevel = this.player.getLevel();
        return (playerLevel - 1) * 5 + 10 - (playerLevel % 2);
    }

    private boolean isCanMovePlayer(MoveElement moveElement) {
        int movedPlayerX = this.playerCoordinate.getX() + moveElement.getDeltaX();
        int movedPlayerY = this.playerCoordinate.getY() + moveElement.getDeltaY();

        return movedPlayerX >= 0 && movedPlayerX < this.mapSize &&
                movedPlayerY >= 0 && movedPlayerY < this.mapSize;
    }

    // ========== getter ==========

    public int getMapSize() {
        return this.mapSize;
    }

    public Coordinate getPlayerCoordinate() {
        return this.playerCoordinate;
    }

    public Coordinate getMainSubjectCoordinate() {
        return this.mainSubjectCoordinate;
    }

    public List<Coordinate> getSubSubjectCoordinates() {
        return this.subSubjectCoordinates;
    }

    public SubjectType getSubjectType() {
        return this.mainSubjectCoordinate.equals(this.playerCoordinate) ?
                SubjectType.MAIN : SubjectType.SUB;
    }
}
