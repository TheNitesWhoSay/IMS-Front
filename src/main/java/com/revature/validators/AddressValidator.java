package com.revature.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.revature.ims_backend.entities.Address;

public class AddressValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Address.class.equals(clazz);
	}

	@Override
	public void validate(Object arg0, Errors arg1) {
		
		
	}
	
}
