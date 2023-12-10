package me.kangmin.swingy.entity.base;

import me.kangmin.swingy.entity.Player;
import me.kangmin.swingy.enums.Move;
import me.kangmin.swingy.enums.StudyType;
import me.kangmin.swingy.enums.SubjectType;
import me.kangmin.swingy.exception.ExceptionMessage;
import me.kangmin.swingy.exception.GameException;

import java.io.IOException;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class GameMap implements Serializable {
    static final String DIRECTORY_NAME = "maps";
    static final String FILE_NAME = "map_level";
    static final String EXTENSION = "txt";

    public static final char PLAYER_SYMBOL = '@';
    public static final char MAIN_SUBJECT_SYMBOL = '$';
    public static final char SUB_SUBJECT_SYMBOL = '#';
    private int mapSize;
    private final Player player;
    private Coordinate playerCoordinate;
    private Coordinate mainSubjectCoordinate;
    private List<Coordinate> subSubjectCoordinates;

    public void nextStage() {
        this.mapSize = this.calculateMapSize();
        this.setEntityCoordinates();
    }

    public boolean move(Move move) {
        if (canMove(move)) {
            this.playerCoordinate.moveBy(move.getDeltaX(), move.getDeltaY());
        }
        return this.isCollisionToEntities();
    }

    public void removeSubject() {
        subSubjectCoordinates.remove(this.playerCoordinate);
    }

    // ========== constructor ==========
    public GameMap(Player player) {
        this.player = player;
        this.mapSize = this.calculateMapSize();
        this.setEntityCoordinates();
    }

    private void setEntityCoordinates() {
        this.subSubjectCoordinates = new ArrayList<>();
        String path = this.getFileMapPath();
        URL resource = this.getClass().getClassLoader().getResource(path);
        if (resource == null) {
            throw new GameException(ExceptionMessage.CREATE_MAP);
        }

        try {
            List<String> lines = Files.readAllLines(Paths.get(resource.toURI()));
            for (int y = 0; y < lines.size(); ++y) {
                String line = lines.get(y);
                for (int x = 0; x < line.length(); ++x) {
                    this.setEntityCoordinate(line, x, y);
                }
            }
        } catch (IOException | URISyntaxException | NullPointerException e) {
            throw new GameException(ExceptionMessage.CREATE_MAP);
        }
    }

    private void setEntityCoordinate(String line, int x, int y) {
        Coordinate coordinate = new Coordinate(x, y);

        switch (line.charAt(x)) {
            case PLAYER_SYMBOL:
                this.playerCoordinate = coordinate;
                break;
            case SUB_SUBJECT_SYMBOL:
                this.subSubjectCoordinates.add(coordinate);
                break;
            case MAIN_SUBJECT_SYMBOL:
                this.mainSubjectCoordinate = coordinate;
                break;
            default:
                break;
        }
    }
    private boolean isCollisionToEntities() {
        return this.isCollisionWithEnemy() || this.isCollisionWithEnemyBoss();
    }

    private boolean isCollisionWithEnemyBoss() {
        return this.mainSubjectCoordinate.equals(this.playerCoordinate);
    }

    private boolean isCollisionWithEnemy() {
        return this.subSubjectCoordinates.stream()
                                         .anyMatch(coordinate -> coordinate.equals(this.playerCoordinate));
    }

    // ========== private method ==========
    private int calculateMapSize() {
        int playerLevel = this.player.getLevel();
        return (playerLevel - 1) * 5 + 10 - (playerLevel % 2);
    }

    private String getFileMapPath() {
        String originalFileName = String.format("%s%d.%s", FILE_NAME, this.player.getLevel(), EXTENSION);
        return String.format("%s/%s", DIRECTORY_NAME, originalFileName);
    }

    private boolean canMove(Move move) {
        int movedPlayerX = this.playerCoordinate.getX() + move.getDeltaX();
        int movedPlayerY = this.playerCoordinate.getY() + move.getDeltaY();

        return movedPlayerX >= 0 && movedPlayerX < this.mapSize &&
                movedPlayerY >= 0 && movedPlayerY < this.mapSize;
    }

    // ========== getter ==========

    public int getMapSize() {
        return this.calculateMapSize();
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
