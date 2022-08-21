package com.example.booking.Exception;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EnumUserRoleExceptionHandler implements ConstraintValidator<EnumUserRole, String> {
    private List<String> values;

    @Override
    public void initialize(EnumUserRole constraintAnnotation) {

            values = Stream.of(constraintAnnotation.enumClass().getEnumConstants())
                    .map(Enum::name)
                    .collect(Collectors.toList());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null){
            return true;
        }
            return  values.contains(value.toString());
        }
    }

