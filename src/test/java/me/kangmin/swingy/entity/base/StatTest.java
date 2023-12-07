package me.kangmin.swingy.entity.base;

import me.kangmin.swingy.enums.PlayerType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StatTest {

    @Test
    void levelUp() {
        PlayerType[] playerTypes = PlayerType.values();

        for (PlayerType playerType : playerTypes) {
            Stat stat = new Stat(playerType);

            int codingSkill = stat.getCodingSkill();
            int mentalStrength = stat.getMentalStrength();
            int health = stat.getHealth();
            assertThat(codingSkill).isEqualTo(playerType.getCodingSkill());
            assertThat(mentalStrength).isEqualTo(playerType.getMentalStrength());
            assertThat(health).isEqualTo(playerType.getHealth());

            for (int j = 0; j < 7; ++j) {
                stat.levelUp();
            }

            assertThat(Stat.PURE_STAT_LIMIT).isEqualTo(stat.getCodingSkill());
            assertThat(Stat.PURE_STAT_LIMIT).isEqualTo(stat.getMentalStrength());
            assertThat(Stat.PURE_STAT_LIMIT).isEqualTo(stat.getHealth());
        }
    }
}