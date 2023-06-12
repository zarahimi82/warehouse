package com.krieger.warehouse.validators;

import com.krieger.warehouse.exception.ObjectNotValidException;
import jakarta.validation.*;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ObjectValidator<T> {
    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();

    public void validate(T objectToValidate)
    {
        Set<ConstraintViolation<T>> violationSet =validator.validate(objectToValidate);
        if (!violationSet.isEmpty())
        {
            var errorMessages = violationSet.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());
            throw  new ObjectNotValidException(errorMessages);
        }
    }
}
