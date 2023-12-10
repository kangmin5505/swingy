package me.kangmin.swingy.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import me.kangmin.swingy.enums.ResponseCode;
import me.kangmin.swingy.exception.ExceptionMessage;
import me.kangmin.swingy.exception.GameException;

import java.util.Set;

public interface Request {

    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    ResponseCode getResponseCode();
    default <T extends Request> ResponseCode getResponseCode(Request request, Integer input) {
        Set<ConstraintViolation<T>> validate = validator.validate((T) request);
        if (!validate.isEmpty()) {
            throw new GameException(ExceptionMessage.VALIDATION);
        }
        return ResponseCode.values()[input - 1];
    }

    default <T extends Request> ResponseCode validate(Request request) {
        Set<ConstraintViolation<T>> validate = validator.validate((T) request);
        return validate.isEmpty() ? ResponseCode.SUCCESS : ResponseCode.FAILURE;
    }
}
