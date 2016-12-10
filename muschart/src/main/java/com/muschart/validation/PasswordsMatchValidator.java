package com.muschart.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.muschart.dto.RegisterDTO;
import com.muschart.validation.constraints.PasswordsMatch;

public class PasswordsMatchValidator implements ConstraintValidator<PasswordsMatch, RegisterDTO> {

    @Override
    public void initialize(PasswordsMatch constraintAnnotation) {
    }

    @Override
    public boolean isValid(RegisterDTO register, ConstraintValidatorContext content) {
        return (register.getPassword() == null) || (register.getConfirmPassword() == null)
                || (register.getPassword().equals(register.getConfirmPassword()));
    }

}