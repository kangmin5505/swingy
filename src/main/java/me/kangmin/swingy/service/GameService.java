package me.kangmin.swingy.service;

import me.kangmin.swingy.entity.Game;
import me.kangmin.swingy.enums.PlayerType;

import java.util.List;

public interface GameService {
    List<PlayerType> getNewPlayers();

    void setNewGamePlayer(String playerName, int ordinal);

    Game getGame();

    boolean move(int ordinal);

    boolean studySubject();

    boolean cheatSubject();
}
