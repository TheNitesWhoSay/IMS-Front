package com.revature.converters;

import java.util.HashMap;
import java.util.List;

import org.springframework.core.convert.converter.Converter;

import com.revature.ims_backend.entities.ClientType;
import com.revature.ims_backend.entities.StateAbbreviation;
import com.revature.persist.DataLayer;

public class StringToClientType implements Converter<String, ClientType> {
	
	private static HashMap<String, ClientType> clientTypes = new HashMap<>();
	
	public StringToClientType() {
		DataLayer dl = new DataLayer();
		List<ClientType> clientTypeList = dl.getClientTypes();
		for (ClientType ct: clientTypeList) {
			clientTypes.put(ct.getType(), ct);
		}
	}

	@Override
	public ClientType convert(String ct) {
		return clientTypes.get(ct);
	}
	
}
