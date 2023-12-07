package me.kangmin.swingy.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import me.kangmin.swingy.enums.ResponseCode;

public class MoveRequest implements Request {

    @Min(0)
    @Max(4)
    @NotNull
    private final Integer input;

    public MoveRequest(Integer input) {
        this.input = input;
    }
    @Override
    public ResponseCode getResponseCode() {
        return this.getResponseCode(this, this.input);
    }

}
