package me.kangmin.swingy.controller.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import me.kangmin.swingy.controller.request.enums.RequestHandlerCode;

public class SetNewPlayerNameRequest implements Request {
    public static int MIN_PLAYER_NAME_LENGTH = 1;
    public static int MAX_PLAYER_NAME_LENGTH = 10;
    @Size(min = 1, max = 10)
    @NotNull
    private final String input;
    private final RequestHandlerCode requestHandlerCode = RequestHandlerCode.CREATE_PLAYER_NAME;

    public SetNewPlayerNameRequest(String input) {
        this.input = input;
    }

    @Override
    public RequestHandlerCode getRequestHandlerCode() {
        return this.requestHandlerCode;
    }

    @Override
    public String getInput() {
        return this.input;
    }
}
