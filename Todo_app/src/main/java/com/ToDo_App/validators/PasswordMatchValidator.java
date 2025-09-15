package com.ToDo_App.validators;

import com.ToDo_App.dto.user.request.RegisterRequestDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatches, RegisterRequestDto> {

    @Override
    public boolean isValid(RegisterRequestDto dto, ConstraintValidatorContext context) {
        boolean isValid = dto.getPassword().equals(dto.getConfirmPassword());
        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Passwords do not match")
                    .addPropertyNode("confirmPassword")
                    .addConstraintViolation();
        }
        return isValid;
    }
}
