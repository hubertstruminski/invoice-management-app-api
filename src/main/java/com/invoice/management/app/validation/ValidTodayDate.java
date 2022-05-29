package com.invoice.management.app.validation;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import java.lang.annotation.*;

import java.text.SimpleDateFormat;

import java.util.Date;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TodayDateValidator.class)
@Documented
public @interface ValidTodayDate {

    String message() default "Date field must have today value";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}

class TodayDateValidator implements ConstraintValidator<ValidTodayDate, Date> {

    @Override
    public void initialize(ValidTodayDate constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Date date, ConstraintValidatorContext context) {
        Date now = new Date();

        if(date == null) {
            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate("Date can not be null")
                    .addConstraintViolation();
            return false;
        }
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        return fmt.format(date).equals(fmt.format(now));
    }
}
