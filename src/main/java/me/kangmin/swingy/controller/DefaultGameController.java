package me.kangmin.swingy.controller;

import me.kangmin.swingy.dto.SubjectDto;
import me.kangmin.swingy.dto.GameInfoDto;
import me.kangmin.swingy.dto.PlayerDtos;
import me.kangmin.swingy.enums.ResponseCode;
import me.kangmin.swingy.enums.SubjectType;
import me.kangmin.swingy.exception.GameException;
import me.kangmin.swingy.request.SetNewGamePlayerRequest;
import me.kangmin.swingy.service.GameService;
import me.kangmin.swingy.request.Request;

public class DefaultGameController implements GameController {

    private final GameService gameService;

    public DefaultGameController(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public <T extends Request> ResponseCode getResponseCode(Class<T> clazz, String input) {
        try {
            Request request = clazz.getConstructor(Integer.class).newInstance(Integer.valueOf(input));

            return request.getResponseCode();
        } catch (Exception e) {
            return ResponseCode.FAILURE;
        }
    }

    @Override
    public PlayerDtos getNewPlayers() {
        return PlayerDtos.of(gameService.getNewPlayers());
    }


    @Override
    public ResponseCode setNewGamePlayer(SetNewGamePlayerRequest setNewGamePlayerRequest) {
        String playerName = setNewGamePlayerRequest.getPlayerName();
        ResponseCode input = setNewGamePlayerRequest.getInput();

        try {
            Request.validate(playerName);
        } catch (GameException e) {
            return ResponseCode.FAILURE;
        }

        this.gameService.setNewGamePlayer(playerName, input.ordinal());
        return ResponseCode.SUCCESS;
    }

    @Override
    public GameInfoDto getGameInfo() {
        return new GameInfoDto(this.gameService.getGame());
    }

    @Override
    public boolean move(ResponseCode input) {
        return this.gameService.move(input.ordinal());
    }

    @Override
    public SubjectDto doSubject(ResponseCode input) {
        if (SubjectType.STUDY.ordinal() == input.ordinal()) {
            boolean isSuccess = this.gameService.studySubject();
            return new SubjectDto(SubjectType.STUDY, isSuccess);
        }

        boolean isSuccess = this.gameService.cheatSubject();
        return new SubjectDto(SubjectType.CHEAT, isSuccess);
    }
}
