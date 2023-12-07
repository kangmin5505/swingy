package me.kangmin.swingy.enums;

public enum Move {
    UP(0, -1, "위로 이동"),
    DOWN(0, 1, "아래로 이동"),
    LEFT(-1, 0, "왼쪽으로 이동"),
    RIGHT(1, 0, "오른쪽으로 이동")
    ;

    private final int deltaX;
    private final int deltaY;
    private final String description;

    Move(int deltaX, int deltaY, String description) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public int getDeltaX() {
        return deltaX;
    }

    public int getDeltaY() {
        return deltaY;
    }
}
