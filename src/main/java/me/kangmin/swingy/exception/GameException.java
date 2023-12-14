package me.kangmin.swingy.exception;

import me.kangmin.swingy.controller.response.Response;

public class GameException extends RuntimeException {
    public GameException(Response.Message message) {
        super(message.getMessage());
    }

    public GameException(String message) { super(message);}
}
