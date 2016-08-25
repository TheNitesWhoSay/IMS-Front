package com.revature.converters;

import org.springframework.core.convert.converter.Converter;

import com.revature.ims_backend.entities.StateAbbreviation;

public class StateAbbreviationToString implements Converter<StateAbbreviation, String> {
	
	@Override
	public String convert(StateAbbreviation abbr) {
		return abbr.getAbbreviation();
	}
	
}
