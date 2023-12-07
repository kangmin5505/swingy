package me.kangmin.swingy.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import me.kangmin.swingy.enums.ResponseCode;

public class WelcomeRequest implements Request {
    @Min(1)
    @Max(4)
    @NotNull
    private final Integer input;

    public WelcomeRequest(Integer input) {
        this.input = input;
    }

    @Override
    public ResponseCode getResponseCode() {
        return getResponseCode(this, this.input);
    }
}
