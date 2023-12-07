package me.kangmin.swingy.entity;

import me.kangmin.swingy.entity.base.Level;
import me.kangmin.swingy.enums.PlayerType;
import me.kangmin.swingy.helper.ExperienceToLevelUp;
import me.kangmin.swingy.helper.GameMapSize;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class GameTest {

    @Test
    @DisplayName("다음 단계 넘어갈 시 맵 사이즈가 증가하는지 확인")
    void nextStage() {
        Game game = new Game(PlayerType.ENGINEERING);
        Player player = game.getPlayer();

        GameMapSize[] gameMapSizes = GameMapSize.values();

        assertAll(
                IntStream.range(0, Level.MAX_LEVEL + 1).mapToObj(
                        i -> () -> {
                            player.increaseExperience(ExperienceToLevelUp.values()[i].getExperience());
                            assertThat(game.getStage()).isEqualTo(i);
                            assertThat(game.getMapSize()).isEqualTo(gameMapSizes[i].toInt());
                            game.nextStage();
                        }
                )
        );
    }
}