package me.kangmin.swingy.helper;

public enum GameMapSize {
    ZERO(5),
    ONE(9),
    TWO(15),
    THREE(19),
    FOUR(25),
    FIVE(29),
    SIX(35),
    SEVEN(39)
    ;

    private final int size;

    GameMapSize(int size) {
        this.size = size;
    }

    public int toInt() {
        return size;
    }
}
