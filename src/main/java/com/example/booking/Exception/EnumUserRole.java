package com.example.booking.Exception;

import javax.validation.*;
import java.lang.annotation.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(ElementType.FIELD)
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = EnumUserRoleExceptionHandler.class)

public @interface EnumUserRole {
    Class<? extends Enum<?>> enumClass();
    String message() default "Must Match With student,admin,lecturer";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}