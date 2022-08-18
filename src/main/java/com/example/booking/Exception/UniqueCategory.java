package com.example.booking.Exception;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueCategoryExceptionHandler.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueCategory {
    String message() default "Category Name Not Unique";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default{};
}