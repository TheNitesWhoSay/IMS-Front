package com.revature.converters;

import java.util.HashMap;
import java.util.List;

import org.springframework.core.convert.converter.Converter;

import com.revature.ims_backend.entities.StateAbbreviation;
import com.revature.persist.DataLayer;

public class StringToStateAbbreviation implements Converter<String, StateAbbreviation> {
	
	private static HashMap<String, StateAbbreviation> states = new HashMap<>();
	
	public StringToStateAbbreviation() {
		DataLayer dl = new DataLayer();
		List<StateAbbreviation> stateList = dl.getStates();
		stateList.forEach(s -> states.put(s.getAbbreviation(), s));
	}

	@Override
	public StateAbbreviation convert(String abbr) {
		return states.get(abbr);
	}
	
}
