package com.krieger.warehouse.validators;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidOccupaiedHoldingPointValidator.class)
@Documented
public @interface ValidOccupiedHoldingPoint {
    String message() default "The OccupiedHoldingPoint musst be between 0 to HoldingPoint value.";

    String HoldingPointField();

    String OccupiedHoldingPointField();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        ValidOccupiedHoldingPoint[] value();
    }
}
