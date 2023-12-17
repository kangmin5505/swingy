package me.kangmin.swingy.service;

import me.kangmin.swingy.dto.SubjectResultDto;
import me.kangmin.swingy.entity.Artifact;
import me.kangmin.swingy.entity.Game;
import me.kangmin.swingy.entity.base.GameMap;
import me.kangmin.swingy.entity.base.Stage;
import me.kangmin.swingy.enums.Page;
import me.kangmin.swingy.enums.PlayerType;
import me.kangmin.swingy.enums.SubjectType;
import me.kangmin.swingy.repository.GameRepository;
import me.kangmin.swingy.view.menu.element.*;

import java.util.List;

public class GameService {

    private final GameRepository gameRepository;
    private Game game;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public List<PlayerType> getNewPlayerList() {
        return this.gameRepository.findAllNewPlayer();
    }

    public Page setNewPlayerName(String playerName) {
        this.game.setPlayerName(playerName);
        return Page.GAME_PLAY;
    }

    public Game getGame() {
        return this.game;
    }


    public boolean movePlayer(MoveElement moveElement) {
        GameMap gameMap = this.game.getGameMap();
        return gameMap.movePlayer(moveElement);
    }

    public SubjectResultDto studySubject(Integer idx) {
        boolean isSuccess;
        if (StudyMethodElement.values()[idx] == StudyMethodElement.STUDY) {
            isSuccess = this.game.studySubject();
        } else {
            isSuccess = this.game.cheatSubject();
        }

        SubjectType subjectType = this.game.getSubjectType();
        int stage = this.game.getStage().getStage();
        return new SubjectResultDto(isSuccess, subjectType, stage);
    }

    public Artifact getArtifact() {
        return this.game.getArtifact();
    }

    public Page saveGame(Integer idx) {
        SaveGameElement value = SaveGameElement.values()[idx];

        if (value == SaveGameElement.EXIT) {
            return Page.WELCOME;
        }

        this.game.nextStage();
        if (value == SaveGameElement.SAVE) {
            this.gameRepository.saveGame(this.game);
        }

        return Page.GAME_PLAY;
    }

    public List<Game> getSavedGameList() {
        return this.gameRepository.findAllSavedGame();
    }

    public Page loadGame(Integer idx) {
        int savedGameCount = this.gameRepository.getSavedGameCount();

        boolean isBack = savedGameCount == idx;
        if (isBack) {
            return Page.WELCOME;
        }

        this.setGame(idx);

        return Page.GAME_PLAY;
    }

    private void setGame(int input) {
        this.game = this.gameRepository.findAllSavedGame().get(input);
    }

    public void artifactAction(int idx) {
        if (ArtifactElement.values()[idx] == ArtifactElement.ACQUIRE) {
            this.game.acquireArtifact();
        } else {
            this.game.discardArtifact();
        }
    }

    public Page settingMenu(int idx) {
        return SettingElement.values()[idx].getPage();
    }

    public Page welcomeMenu(int idx) {
        return WelcomeElement.values()[idx].getPage();
    }

    public Page chooseNewPlayer(int idx) {
        PlayerType[] playerTypes = PlayerType.values();

        boolean isBack = playerTypes.length == idx;
        if (isBack) {
            return Page.WELCOME;
        }

        this.game = new Game(playerTypes[idx]);
        return Page.NONE;
    }

    public Page resetData(int idx) {
        ResetDataElement value = ResetDataElement.values()[idx];

        if (value == ResetDataElement.YES) {
            this.gameRepository.resetData();
        }
        this.gameRepository.releaseData();
        return Page.SETTING;
    }
}
