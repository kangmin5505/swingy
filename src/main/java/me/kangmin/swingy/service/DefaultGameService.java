package me.kangmin.swingy.service;

import me.kangmin.swingy.entity.Game;
import me.kangmin.swingy.entity.base.GameMap;
import me.kangmin.swingy.enums.Move;
import me.kangmin.swingy.enums.PlayerType;
import me.kangmin.swingy.repository.GameRepository;

import java.util.List;

public class DefaultGameService implements GameService {

    private final GameRepository gameRepository;
    private Game game;

    public DefaultGameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public List<PlayerType> getNewPlayers() {
        return this.gameRepository.findAllNewPlayer();
    }

    @Override
    public void setNewGamePlayer(String playerName, int ordinal) {
        this.game = new Game(playerName, PlayerType.values()[ordinal]);
    }

    @Override
    public Game getGame() {
        return this.game;
    }

    @Override
    public boolean move(int ordinal) {
        GameMap gameMap = this.game.getGameMap();
        Move move = Move.values()[ordinal];
        return gameMap.move(move);
    }

    @Override
    public boolean studySubject() {
        return this.game.studySubject();
    }

    @Override
    public boolean cheatSubject() {
        return this.game.cheatSubject();
    }
}
