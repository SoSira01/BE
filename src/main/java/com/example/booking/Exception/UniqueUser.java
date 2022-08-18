package com.example.booking.Exception;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueUserExceptionHandler.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueUser {
    String message() default  "Name Not Unique";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default{};
}