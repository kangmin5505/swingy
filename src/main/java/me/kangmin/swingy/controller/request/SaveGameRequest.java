package me.kangmin.swingy.controller.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import me.kangmin.swingy.controller.request.enums.RequestHandlerCode;

public class SaveGameRequest implements Request {
    @NotNull
    @Min(1)
    @Max(3)
    private final Integer input;
    private final RequestHandlerCode requestHandlerCode = RequestHandlerCode.SAVE_GAME;

    public SaveGameRequest(Integer input) {
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
