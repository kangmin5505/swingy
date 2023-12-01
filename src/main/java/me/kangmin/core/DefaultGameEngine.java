package me.kangmin.core;

import me.kangmin.view.GameView;

public class DefaultGameEngine implements GameEngine {
    private GameView gameView;

    public DefaultGameEngine(GameView gameView) {
        this.gameView = gameView;
    }

    @Override
    public void run() {
        gameView.showWindow();
    }
}
