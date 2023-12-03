package me.kangmin.swingy.core;

import me.kangmin.swingy.controller.DefaultGameController;
import me.kangmin.swingy.controller.GameController;
import me.kangmin.swingy.enums.ViewMode;
import me.kangmin.swingy.view.ConsoleGameView;
import me.kangmin.swingy.view.GUIGameView;
import me.kangmin.swingy.view.GameView;

public class GameManager {
    private GameSetting gameSetting;
    private GameEngine gameEngine;
    public GameManager(ViewMode viewMode) {
        GameController gameController = new DefaultGameController();
        GameView gameView = viewMode == ViewMode.CONSOLE ?
                                new ConsoleGameView(gameController) :
                                new GUIGameView();
        gameEngine = new DefaultGameEngine(gameView);
    }

    public void run() {
        gameEngine.run();
    }
}
