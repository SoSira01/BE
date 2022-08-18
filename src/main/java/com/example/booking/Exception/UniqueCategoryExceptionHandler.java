package com.example.booking.Exception;

import com.example.booking.repositories.CategoryRepository;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class UniqueCategoryExceptionHandler implements ConstraintValidator<UniqueCategory,String> {
    private  final CategoryRepository categoryRepository;

    public UniqueCategoryExceptionHandler(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public boolean isValid(String categoryName, ConstraintValidatorContext context) {
        return !categoryRepository.existsCategoryByCategoryName(categoryName);
    }

}
