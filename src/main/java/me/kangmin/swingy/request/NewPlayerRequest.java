package me.kangmin.swingy.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import me.kangmin.swingy.enums.ResponseCode;

public class NewPlayerRequest implements Request {

    @Min(1)
    @Max(8)
    @NotNull
    private final Integer input;

    public NewPlayerRequest(Integer input) {
        this.input = input;
    }

    @Override
    public ResponseCode getResponseCode() {
        return this.getResponseCode(this, this.input);
    }
}
