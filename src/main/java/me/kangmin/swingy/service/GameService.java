package me.kangmin.swingy.service;

import me.kangmin.swingy.dto.SubjectResultDto;
import me.kangmin.swingy.entity.Artifact;
import me.kangmin.swingy.entity.Game;
import me.kangmin.swingy.enums.PlayerType;
import me.kangmin.swingy.enums.Step;
import me.kangmin.swingy.enums.SubjectType;
import me.kangmin.swingy.exception.GameException;
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

    public Step setNewPlayerName(String playerName) {
        this.game.setPlayerName(playerName);

        return Step.GAME_PLAY;
    }

    public Game getGame() {
        return this.game;
    }

    public boolean movePlayer(MoveElement moveElement) {
        return this.game.movePlayer(moveElement);

    }

    public SubjectResultDto studySubject(Integer idx) {
        boolean isSuccess;
        if (StudyMethodElement.values()[idx] == StudyMethodElement.STUDY) {
            isSuccess = this.game.studySubject();
        } else {
            isSuccess = this.game.cheatSubject();
        }

        SubjectType subjectType = this.game.getSubjectType();
        int stageLevel = this.game.getStage().getStageLevel();

        return new SubjectResultDto(isSuccess, subjectType, stageLevel);
    }

    public Artifact getArtifact() {
        return this.game.getArtifact();
    }

    public List<Game> getSavedGames() {
        return this.gameRepository.findAllSavedGame();
    }

    public Step loadGame(Integer idx) {
        int savedGameCount = this.gameRepository.getSavedGameCount();

        boolean isBack = (savedGameCount == idx);
        if (isBack) {
            return Step.WELCOME;
        }

        this.setGame(idx);

        return Step.GAME_PLAY;
    }

    private void setGame(int input) {
        this.game = this.gameRepository.findGameByIndex(input)
                                       .orElseThrow(() -> new GameException("게임이 존재하지 않습니다."));
    }

    public void artifactAction(int idx) {
        if (ArtifactElement.values()[idx] == ArtifactElement.ACQUIRE) {
            this.game.acquireArtifact();
        } else {
            this.game.discardArtifact();
        }
    }

    public Step settingMenu(int idx) {
        return SettingElement.values()[idx].getPage();
    }

    public Step welcomeMenu(int idx) {
        return WelcomeElement.values()[idx].getPage();
    }

    public Step chooseNewPlayer(int idx) {
        PlayerType[] playerTypes = PlayerType.values();

        boolean isBack = (playerTypes.length == idx);
        if (isBack) {
            return Step.WELCOME;
        }

        this.game = new Game(playerTypes[idx]);

        return Step.NONE;
    }

    public Step resetData(int idx) {
        ResetDataElement value = ResetDataElement.values()[idx];
        if (value == ResetDataElement.YES) {
            this.gameRepository.resetData();
        }

        return Step.SETTING;
    }

    public void nextStage() {
        this.game.nextStage();
    }

    public Step saveGame(int idx) {
        SaveGameElement value = SaveGameElement.values()[idx];
        this.gameRepository.saveGame(this.game);

        return (value == SaveGameElement.EXIT) ? Step.WELCOME : Step.GAME_PLAY;
    }
}
