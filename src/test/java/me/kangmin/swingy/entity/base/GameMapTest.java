package me.kangmin.swingy.entity.base;

import me.kangmin.swingy.entity.Player;
import me.kangmin.swingy.enums.PlayerType;
import me.kangmin.swingy.helper.ExperienceToLevelUp;
import me.kangmin.swingy.helper.GameMapSize;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static me.kangmin.swingy.entity.base.GameMap.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class GameMapTest {
    @Test
    @DisplayName("플레이어 레벨이 오르면 다음 스테이지 맵 사이즈가 증가해야 함")
    void getMapSize() {
        Player player = new Player("kangmin", PlayerType.ENGINEERING);
        GameMap gameMap = new GameMap(player);
        GameMapSize[] gameMapSizes = GameMapSize.values();

        ExperienceToLevelUp[] experienceToLevelUps = ExperienceToLevelUp.values();

        assertAll(
                IntStream.range(0, Level.MAX_LEVEL + 1).mapToObj(
                        i -> () -> {
                            player.increaseExperience(experienceToLevelUps[i].getExperience());
                            assertThat(gameMap.getMapSize()).isEqualTo(gameMapSizes[i].toInt());
                        }
                )
        );
    }

    @Test
    @DisplayName("각 스테이지 별로 맵을 읽어서 플레이어, 보스, 부하들의 위치를 설정해야 함")
    void generateMap() {
        Player player = new Player("kangmin", PlayerType.ENGINEERING);
        GameMap gameMap = new GameMap(player);
        ExperienceToLevelUp[] experienceToLevelUps = ExperienceToLevelUp.values();

        assertAll(
                IntStream.range(0, Level.MAX_LEVEL + 1).mapToObj(i -> () -> {
                    player.increaseExperience(experienceToLevelUps[i].getExperience());
                    gameMap.nextStage();
                    Coordinate playerCoordinate = null;
                    Coordinate enemyBossCoordinate = null;
                    final List<Coordinate> enemyCoordinates = new ArrayList<>();

                    String originalFileName = String.format("%s%d.%s", FILE_NAME, player.getLevel(), EXTENSION);
                    String path = String.format("%s/%s", DIRECTORY_NAME, originalFileName);
                    URL resource = this.getClass().getClassLoader().getResource(path);

                    List<String> lines = Files.readAllLines(Paths.get(resource.toURI()));
                    for (int y = 0; y < lines.size(); ++y) {
                        String line = lines.get(y);
                        for (int x = 0; x < line.length(); ++x) {
                            switch (line.charAt(x)) {
                                case PLAYER_SYMBOL:
                                    playerCoordinate = new Coordinate(x, y);
                                    break;
                                case SUB_SUBJECT_SYMBOL:
                                    enemyCoordinates.add(new Coordinate(x, y));
                                    break;
                                case MAIN_SUBJECT_SYMBOL:
                                    enemyBossCoordinate = new Coordinate(x, y);
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                    assertThat(gameMap.getPlayerCoordinate()).isEqualTo(playerCoordinate);
                    assertThat(gameMap.getMainSubjectCoordinate()).isEqualTo(enemyBossCoordinate);
                    assertThat(gameMap.getSubSubjectCoordinates()).containsExactlyInAnyOrderElementsOf(enemyCoordinates);
                })
        );
    }
}