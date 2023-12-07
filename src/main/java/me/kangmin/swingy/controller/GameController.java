package me.kangmin.swingy.controller;

import me.kangmin.swingy.dto.SubjectDto;
import me.kangmin.swingy.dto.GameInfoDto;
import me.kangmin.swingy.dto.PlayerDtos;
import me.kangmin.swingy.enums.ResponseCode;
import me.kangmin.swingy.request.Request;
import me.kangmin.swingy.request.SetNewGamePlayerRequest;

public interface GameController {
    <T extends Request> ResponseCode getResponseCode(Class<T> clazz, String input);

    PlayerDtos getNewPlayers();


    GameInfoDto getGameInfo();

    boolean move(ResponseCode input);

    SubjectDto doSubject(ResponseCode input);

    ResponseCode setNewGamePlayer(SetNewGamePlayerRequest setNewGamePlayerRequest);
}