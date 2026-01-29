package com.MyProject.validation;

import com.MyProject.entity.UserDto;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, UserDto> {

	@Override
	
	public boolean isValid(UserDto userDto, ConstraintValidatorContext context) {
		
		boolean valid=userDto.getPassword().equals(userDto.getConfirmPassword());

			if(!valid) {
				context.disableDefaultConstraintViolation();
				context.buildConstraintViolationWithTemplate("A jelszavak nem egyeznek")
				.addPropertyNode("confirmPassword")
				.addConstraintViolation();
		}
		
        return valid;

	}

}
