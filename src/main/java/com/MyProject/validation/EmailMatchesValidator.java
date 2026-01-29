package com.MyProject.validation;

import com.MyProject.entity.UserDto;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailMatchesValidator implements ConstraintValidator<EmailMatches, UserDto> {

	@Override
	public boolean isValid(UserDto userDto, ConstraintValidatorContext context) {
		
		boolean valid= userDto.getEmail().equals(userDto.getConfirmEmail());
		
		if(!valid) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("Az email címek nem egyeznek")
			.addPropertyNode("confirmEmail")
			.addConstraintViolation();
			
		}
		return valid;
	}

}
