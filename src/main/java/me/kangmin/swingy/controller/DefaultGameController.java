package me.kangmin.swingy.controller;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public class DefaultGameController implements GameController {
    private static final Validator validator;
    static {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    public class InputRequest {

        @Min(1)
        Integer input;

        public InputRequest(Integer input) {
            this.input = input;
        }
    }

    public void test(int number) {
        InputRequest inputRequest = new InputRequest(number);
        Set<ConstraintViolation<Object>> validate = validator.validate(inputRequest);
        if (!validate.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }
}
