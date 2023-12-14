package me.kangmin.swingy.controller.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import me.kangmin.swingy.controller.request.enums.RequestHandlerCode;

public class WelcomeRequest implements Request {
    @Min(1)
    @Max(4)
    @NotNull
    private final Integer input;
    private final RequestHandlerCode requestHandlerCode = RequestHandlerCode.WELCOME_MENU;

    public WelcomeRequest(Integer input) {
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
