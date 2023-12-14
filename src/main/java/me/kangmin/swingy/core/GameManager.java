package me.kangmin.swingy.core;

import me.kangmin.swingy.controller.GameController;
import me.kangmin.swingy.enums.ViewMode;
import me.kangmin.swingy.repository.FileGameRepository;
import me.kangmin.swingy.service.GameService;
import me.kangmin.swingy.view.ConsoleGameView;
import me.kangmin.swingy.view.GUIGameView;
import me.kangmin.swingy.view.GameView;

public class GameManager {
    private final GameSetting gameSetting;
    private final GameController gameController;
    private GameView gameView;


    public void run() {
        this.gameView.run();
    }

    public void procChangeViewMode() {
        this.gameSetting.changeViewMode();
        this.setGameView(this.gameSetting.getViewMode());
        this.run();
    }

    // ========== constructor ==========
    public GameManager(ViewMode viewMode) {
        this.gameSetting = new DefaultGameSetting(viewMode);
        GameService gameService = new GameService(new FileGameRepository());
        this.gameController = new GameController(gameService);

        this.setGameView(this.gameSetting.getViewMode());
    }

    // ========== private methods ==========
    private void setGameView(ViewMode viewMode) {
        this.gameView = viewMode == ViewMode.CONSOLE ?
                new ConsoleGameView(this, this.gameController) :
                new GUIGameView(this, this.gameController);
    }

}
