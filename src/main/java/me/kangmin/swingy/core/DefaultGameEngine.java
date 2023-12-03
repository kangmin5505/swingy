package me.kangmin.swingy.core;

import me.kangmin.swingy.view.GameView;

public class DefaultGameEngine implements GameEngine {
    private GameView gameView;

    public DefaultGameEngine(GameView gameView) {
        this.gameView = gameView;
    }

    @Override
    public void run() {
        gameView.run();
    }
}
