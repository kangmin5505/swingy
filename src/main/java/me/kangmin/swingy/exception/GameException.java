package me.kangmin.swingy.exception;

public class GameException extends RuntimeException {
    public GameException(ExceptionMessage message) {
        super(message.getMessage());
    }
}
