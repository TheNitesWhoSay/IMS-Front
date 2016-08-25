package com.revature.converters;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import com.revature.controllers.BusinessDelegate;
import com.revature.ims_backend.entities.StateAbbreviation;
import com.revature.persist.DataLayer;

public class StringToStateAbbreviation implements Converter<String, StateAbbreviation>, InitializingBean {
	
	private static HashMap<String, StateAbbreviation> states = new HashMap<>();	

	@Override
	public StateAbbreviation convert(String abbr) {
		return states.get(abbr);
	}



	@Override
	public void afterPropertiesSet() throws Exception {
		DataLayer dl = new DataLayer();
		List<StateAbbreviation> stateList = dl.getStates();
		stateList.forEach(s -> states.put(s.getAbbreviation(), s));
		dl.close();
	}
}
