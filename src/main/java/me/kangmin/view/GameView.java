package me.kangmin.view;

public interface GameView {
    int WIDTH = 1280;
    double RATIO = (double) 9 / 16;
    int HEIGHT =  (int) (WIDTH * RATIO);
    int MAIN_PANEL_WIDTH = WIDTH * 3 / 4;
    int SUB_PANEL_WIDTH = WIDTH - MAIN_PANEL_WIDTH;

    void showWindow();
}
