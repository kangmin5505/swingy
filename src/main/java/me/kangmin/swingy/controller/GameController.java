package me.kangmin.swingy.controller;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import me.kangmin.swingy.controller.request.Request;
import me.kangmin.swingy.controller.request.enums.RequestHandlerCode;
import me.kangmin.swingy.controller.request.handler.IntegerArgumentRequestHandler;
import me.kangmin.swingy.controller.request.handler.NoArgumentRequestHandler;
import me.kangmin.swingy.controller.request.handler.StringArgumentRequestHandler;
import me.kangmin.swingy.controller.response.Response;
import me.kangmin.swingy.controller.response.enums.ResponseCode;
import me.kangmin.swingy.dto.GameInfoDto;
import me.kangmin.swingy.dto.PlayersDto;
import me.kangmin.swingy.dto.SubjectDto;
import me.kangmin.swingy.dto.SubjectResultDto;
import me.kangmin.swingy.entity.Artifact;
import me.kangmin.swingy.enums.Page;
import me.kangmin.swingy.exception.GameException;
import me.kangmin.swingy.service.GameService;
import me.kangmin.swingy.view.menu.element.MoveElement;

import java.util.Map;
import java.util.Set;

public class GameController {

    private final GameService gameService;
    private final static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private final Map<RequestHandlerCode, IntegerArgumentRequestHandler<?>> integerArgumentRequestHandler = Map.of(
            RequestHandlerCode.WELCOME_MENU, this::handleWelcomeMenu,
            RequestHandlerCode.SETTING_MENU, this::handleSettingMenu,
            RequestHandlerCode.CHOOSE_NEW_PLAYER, this::handleChooseNewPlayer,
            RequestHandlerCode.MOVE_PLAYER, this::handleMovePlayer,
            RequestHandlerCode.STUDY_SUBJECT, this::handleStudySubject,
            RequestHandlerCode.ARTIFACT_ACTION, this::handleArtifactAction,
            RequestHandlerCode.SAVE_GAME, this::handleSaveGame,
            RequestHandlerCode.LOAD_GAME, this::handleLoadGame,
            RequestHandlerCode.RESET_DATA, this::handleResetData
    );

    private final Map<RequestHandlerCode, StringArgumentRequestHandler<?>> stringArgumentRequestHandler = Map.of(
            RequestHandlerCode.CREATE_PLAYER_NAME, this::handleSetNewPlayerName
    );
    private final Map<RequestHandlerCode, NoArgumentRequestHandler<?>> noArgumentRequestHandler = Map.of(
            RequestHandlerCode.NEW_PLAYER_LIST, this::handleNewPlayerList,
            RequestHandlerCode.GAME_INFO, this::handleGameInfo,
            RequestHandlerCode.SAVED_GAME_LIST, this::handleSavedGameList
    );

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }


    @SuppressWarnings("unchecked")
    public <T> Response<T> request(Request request) {
        Set<ConstraintViolation<Request>> validate = validator.validate(request);
        if (!validate.isEmpty()) {
            return Response.<T>builder()
                           .responseCode(ResponseCode.FAILURE)
                           .message(Response.Message.INVALID_INPUT)
                           .build();
        }

        try {
            Class<?> inputClass = request.getClass().getDeclaredField("input").getType();
            T data = null;

            if (inputClass.equals(String.class)) {
                data = (T) stringArgumentRequestHandler.get(request.getRequestHandlerCode()).handle(request.getInput());
            } else if (inputClass.equals(Integer.class)) {
                data = (T) integerArgumentRequestHandler.get(request.getRequestHandlerCode()).handle(request.getInput());
            } else {
                throw new IllegalArgumentException();
            }

            return Response.<T>builder()
                           .responseCode(ResponseCode.SUCCESS)
                           .data(data)
                           .build();
        } catch (GameException | NoSuchFieldException e) {
            return Response.<T>builder()
                           .responseCode(ResponseCode.FAILURE)
                           .message(e.getMessage())
                           .build();
        }
    }

    @SuppressWarnings("unchecked")
    public <T> Response<T> request(RequestHandlerCode requestHandlerCode) {
        try {
            T data = (T) noArgumentRequestHandler.get(requestHandlerCode).handle();
            return Response.<T>builder()
                           .responseCode(ResponseCode.SUCCESS)
                           .data(data)
                           .build();
        } catch (GameException e) {
            return Response.<T>builder()
                           .responseCode(ResponseCode.FAILURE)
                           .message(e.getMessage())
                           .build();
        }
    }

    private Page handleResetData(Integer input) {
        int idx = input - 1;
        return this.gameService.resetData(idx);
    }

    private Page handleLoadGame(Integer input) {
        int idx = input - 1;
        return this.gameService.loadGame(idx);
    }

    private Page handleSaveGame(Integer input) {
        int idx = input - 1;
        return this.gameService.saveGame(idx);
    }

    private Void handleArtifactAction(Integer input) {
        int idx = input - 1;
        this.gameService.artifactAction(idx);
        return null;
    }

    private SubjectDto handleStudySubject(Integer input) {
        int idx = input - 1;

        SubjectResultDto subjectResultDto = this.gameService.studySubject(idx);
        Artifact artifact = this.gameService.getArtifact();

        return new SubjectDto(subjectResultDto, artifact);
    }

    private Boolean handleMovePlayer(Integer input) {
        int idx = input - 1;
        return this.gameService.movePlayer(MoveElement.values()[idx]);
    }

    private GameInfoDto handleGameInfo() {
        return new GameInfoDto(this.gameService.getGame());
    }

    private Page handleSetNewPlayerName(String input) {
        return this.gameService.setNewPlayerName(input);
    }

    private Page handleChooseNewPlayer(Integer input) {
        int idx = input - 1;
        return this.gameService.chooseNewPlayer(idx);
    }
    private PlayersDto handleNewPlayerList() {
        return PlayersDto.ofPlayerTypes(gameService.getNewPlayerList());
    }
    private PlayersDto handleSavedGameList() {
        return PlayersDto.ofGames(gameService.getSavedGameList());
    }

    private Page handleSettingMenu(Integer input) {
        int idx = input - 1;
        return this.gameService.settingMenu(idx);
    }

    private Page handleWelcomeMenu(Integer input) {
        int idx = input - 1;
        return this.gameService.welcomeMenu(idx);
    }
}
