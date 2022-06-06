package com.invoice.management.app.validation;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import java.lang.annotation.*;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmptyListValidator.class)
@Documented
public @interface MinOneElementList {

    String message() default "List must have minimum 1 element";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}

class EmptyListValidator implements ConstraintValidator<MinOneElementList, List<Long>> {

    @Override
    public void initialize(MinOneElementList constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(List<Long> ids, ConstraintValidatorContext context) {
        if(ids != null && ids.size() > 0) {
            return true;
        } else {
            return false;
        }
    }
}
