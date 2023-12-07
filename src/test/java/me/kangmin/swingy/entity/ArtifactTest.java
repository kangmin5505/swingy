package me.kangmin.swingy.entity;

import me.kangmin.swingy.entity.base.artifacts.ArtifactRank;
import me.kangmin.swingy.entity.base.artifacts.Headset;
import me.kangmin.swingy.entity.base.artifacts.Hoodie;
import me.kangmin.swingy.entity.base.artifacts.Keyboard;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;

class ArtifactTest {

    @Test
    @DisplayName("artifact 랭크에 따라 값이 증가해야함")
    void calcDegree() throws NoSuchFieldException, IllegalAccessException {
        ArtifactRank[] ranks = ArtifactRank.values();

        for (ArtifactRank rank : ranks) {
            Headset headset = new Headset(rank);
            Hoodie hoodie = new Hoodie(rank);
            Keyboard keyboard = new Keyboard(rank);

            checkDegree(headset, rank, Headset.class);
            checkDegree(hoodie, rank, Hoodie.class);
            checkDegree(keyboard, rank, Keyboard.class);
        }
    }

    private <T> void checkDegree(Artifact artifact, ArtifactRank rank, Class<T> clazz) throws NoSuchFieldException, IllegalAccessException {
        Field codingSkillDegreeField = clazz.getDeclaredField("CODING_SKILL_DEGREE");
        Field mentalStrengthDegreeField = clazz.getDeclaredField("MENTAL_STRENGTH_DEGREE");
        Field healthDegreeField = clazz.getDeclaredField("HEALTH_DEGREE");

        int codingSkillDegree = codingSkillDegreeField.getInt(null);
        int mentalStrengthDegree = mentalStrengthDegreeField.getInt(null);
        int healthDegree = healthDegreeField.getInt(null);

        assertThat(artifact.getCodingSkill()).isEqualTo(codingSkillDegree * rank.getMultiplier());
        assertThat(artifact.getMentalStrength()).isEqualTo(mentalStrengthDegree * rank.getMultiplier());
        assertThat(artifact.getHealth()).isEqualTo(healthDegree * rank.getMultiplier());
    }
}