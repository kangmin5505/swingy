package me.kangmin.swingy.controller.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import me.kangmin.swingy.controller.request.enums.RequestHandlerCode;

public class LoadGameRequest implements Request {
    @Min(1)
    @NotNull
    private final Integer input;
    private final RequestHandlerCode requestHandlerCode = RequestHandlerCode.LOAD_GAME;

    public LoadGameRequest(Integer input) {
        this.input = input;
    }

    @Override
    public RequestHandlerCode getRequestHandlerCode() {
        return this.requestHandlerCode;
    }

    @Override
    public Integer getInput() {
        return this.input;
    }
}
