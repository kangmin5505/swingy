package me.kangmin.swingy.enums.menu;

import me.kangmin.swingy.enums.Move;

public enum MoveMenu implements Menu<MoveMenu> {
    UP(Move.UP.getDescription()),
    DOWN(Move.DOWN.getDescription()),
    LEFT(Move.LEFT.getDescription()),
    RIGHT(Move.RIGHT.getDescription()),
    ;

    private final String description;

    MoveMenu(String description) {
        this.description = description;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
}
