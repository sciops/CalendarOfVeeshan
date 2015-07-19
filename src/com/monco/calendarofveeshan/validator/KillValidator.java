package com.monco.calendarofveeshan.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.monco.calendarofveeshan.Kill;

public class KillValidator implements Validator{

	@Override
	public boolean supports(Class clazz) {
		//just validate the instances
		return Kill.class.isAssignableFrom(clazz);

	}

	@Override
	public void validate(Object target, Errors errors) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName",
				"required.userName", "Field name is required.");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address",
				"required.address", "Field name is required.");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password",
				"required.password", "Field name is required.");
			
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword",
				"required.confirmPassword", "Field name is required.");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "sex", 
				"required.sex", "Field name is required.");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "favNumber", 
				"required.favNumber", "Field name is required.");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(
				errors, "javaSkills", "required.javaSkills","Field name is required.");
		
		Kill kill = (Kill)target;
		
		if( !(
				(kill.getKillClass().equals("FFA"))||
				(kill.getKillClass().equals("Class R"))||
				(kill.getKillClass().equals("Class C"))
				)){
			errors.rejectValue("killClass", "required.killClass");
		}
		
	}
	
}
