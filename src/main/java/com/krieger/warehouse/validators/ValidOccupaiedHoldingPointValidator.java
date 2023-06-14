package com.krieger.warehouse.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

public class ValidOccupaiedHoldingPointValidator implements ConstraintValidator<ValidOccupiedHoldingPoint, Object> {

    private String HoldingPointField;
    private String OccupiedHoldingPointField;
    private String message;

    public void initialize(final ValidOccupiedHoldingPoint constraintAnnotation) {
        this.OccupiedHoldingPointField = constraintAnnotation.OccupiedHoldingPointField();
        this.HoldingPointField = constraintAnnotation.HoldingPointField();
        this.message = constraintAnnotation.message();
    }

    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        Integer fieldValue = (Integer) new BeanWrapperImpl(value).getPropertyValue(OccupiedHoldingPointField);
        Integer fieldMatchValue = (Integer) new BeanWrapperImpl(value).getPropertyValue(HoldingPointField);

        boolean isValid = fieldValue >= 0 && fieldValue <= fieldMatchValue;

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(HoldingPointField.toString())
                    .addConstraintViolation();
        }

        return isValid;
    }
}

