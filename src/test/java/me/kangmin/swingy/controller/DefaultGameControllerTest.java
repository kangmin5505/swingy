package me.kangmin.swingy.controller;

import me.kangmin.swingy.enums.ResponseCode;
import me.kangmin.swingy.repository.FileGameRepository;
import me.kangmin.swingy.repository.GameRepository;
import me.kangmin.swingy.service.DefaultGameService;
import me.kangmin.swingy.service.GameService;
import me.kangmin.swingy.request.SettingRequest;
import me.kangmin.swingy.request.WelcomeRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DefaultGameControllerTest {

    GameController gameController;

    @BeforeEach
    void setUp() {
        GameRepository gameRepository = new FileGameRepository();
        GameService gameService = new DefaultGameService(gameRepository);
        gameController = new DefaultGameController(gameService);
    }

    @Test
    @DisplayName("1 ~ 4 사이의 숫자를 넣으면 ResponseCode.ONE ~ ResponseCode.FOUR가 반환되어야 함")
    void welcomeRequest() {
        assertAll(
                IntStream.range(1, 5).mapToObj(i -> () -> {
                    ResponseCode result = gameController.getResponseCode(WelcomeRequest.class, String.valueOf(i));
                    assertEquals(ResponseCode.values()[i - 1], result);
                })
        );
    }

    @Test
    @DisplayName("1 ~ 4 사이의 숫자가 아니면 ResponseCode.FAILURE가 반환되어야 함")
    void welcomeRequest2() {
        assertAll(
                () -> assertThat(ResponseCode.FAILURE).isEqualTo(gameController.getResponseCode(WelcomeRequest.class, "0")),
                () -> assertThat(ResponseCode.FAILURE).isEqualTo(gameController.getResponseCode(WelcomeRequest.class, "5")),
                () -> assertThat(ResponseCode.FAILURE).isEqualTo(gameController.getResponseCode(WelcomeRequest.class, "s"))
        );
    }

    @Test
    @DisplayName("1 ~ 3 사이의 숫자를 넣으면 ResponseCode.ONE ~ ResponseCode.FOUR가 반환되어야 함")
    void settingRequest() {
        assertAll(
                IntStream.range(1, 4).mapToObj(i -> () -> {
                    ResponseCode result = gameController.getResponseCode(SettingRequest.class, String.valueOf(i));
                    assertEquals(ResponseCode.values()[i - 1], result);
                })
        );
    }

    @Test
    @DisplayName("1 ~ 3 사이의 숫자가 아니면 ResponseCode.FAILURE가 반환되어야 함")
    void settingRequest2() {
        assertAll(
                () -> assertThat(ResponseCode.FAILURE).isEqualTo(gameController.getResponseCode(SettingRequest.class, "0")),
                () -> assertThat(ResponseCode.FAILURE).isEqualTo(gameController.getResponseCode(SettingRequest.class, "5")),
                () -> assertThat(ResponseCode.FAILURE).isEqualTo(gameController.getResponseCode(SettingRequest.class, "s"))
        );
    }
}