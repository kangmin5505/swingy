package me.kangmin.core;

import me.kangmin.enums.ViewMode;
import me.kangmin.view.ConsoleGameView;
import me.kangmin.view.GUIGameView;
import me.kangmin.view.GameView;

public class GameManager {
    private GameSetting gameSetting;
    private GameEngine gameEngine;
    public GameManager(ViewMode viewMode) {
        GameView gameView = viewMode == ViewMode.CONSOLE ? new ConsoleGameView() : new GUIGameView();
        gameEngine = new DefaultGameEngine(gameView);
    }

    public void run() {
        gameEngine.run();
    }
}
