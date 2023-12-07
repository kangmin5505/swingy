package me.kangmin.swingy.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import me.kangmin.swingy.enums.ResponseCode;

public class AllocatedSubjectRequest implements Request {
    @Min(1)
    @Max(2)
    @NotNull
    private final Integer input;

    public AllocatedSubjectRequest(Integer input) {
        this.input = input;
    }

    @Override
    public ResponseCode getResponseCode() {
        return this.getResponseCode(this, this.input);
    }
}
