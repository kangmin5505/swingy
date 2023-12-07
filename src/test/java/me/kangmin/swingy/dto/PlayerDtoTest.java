package me.kangmin.swingy.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class PlayerDtoTest {

    @Test
    @DisplayName("assert 에러 없이 객체를 생성할 수 있어야 함")
    void builderTest() {
        PlayerDto playerDto = PlayerDto.builder()
                                        .type("type")
                                        .codingSkill(1)
                                        .mentalStrength(1)
                                        .health(1)
                                        .level(1)
                                        .build();
    }

    @Test
    @DisplayName("assert 에러가 발생해야 함")
    void builderTest2() {
        assertThrows(AssertionError.class, () -> {
            PlayerDto noType = PlayerDto.builder()
                                        .codingSkill(1)
                                        .mentalStrength(1)
                                        .health(1)
                                        .level(1)
                                        .build();
        });
        assertThrows(AssertionError.class, () -> {
            PlayerDto noCodingSkill = PlayerDto.builder()
                                               .type("type")
                                               .mentalStrength(1)
                                               .health(1)
                                               .level(1)
                                               .build();
        });
        assertThrows(AssertionError.class, () -> {
            PlayerDto noMentalStrength = PlayerDto.builder()
                                                  .type("type")
                                                  .codingSkill(1)
                                                  .health(1)
                                                  .level(1)
                                                  .build();
        });
        assertThrows(AssertionError.class, () -> {
            PlayerDto noHealth = PlayerDto.builder()
                                          .type("type")
                                          .codingSkill(1)
                                          .mentalStrength(1)
                                          .level(1)
                                          .build();
        });
    }
}