package com.krieger.warehouse.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidOccupaiedHoldingPointValidator.class)
@Documented
public @interface ValidOccupiedHoldingPoint {
    String message() default "The Occupied Holding Point must be between 0 to Holding Point value.";

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
