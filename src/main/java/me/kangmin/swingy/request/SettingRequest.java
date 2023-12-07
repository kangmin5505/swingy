package me.kangmin.swingy.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import me.kangmin.swingy.enums.ResponseCode;

public class SettingRequest implements Request {
    @Min(1)
    @Max(3)
    @NotNull
    private final Integer input;

    public SettingRequest(Integer input) {
        this.input = input;
    }

    @Override
    public ResponseCode getResponseCode() {
        return getResponseCode(this, this.input);
    }
}
