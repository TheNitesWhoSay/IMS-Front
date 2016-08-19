package com.revature.converters;

import org.springframework.core.convert.converter.Converter;

import com.revature.ims_backend.entities.ClientType;

public class ClientTypeToString implements Converter<ClientType, String> {

	@Override
	public String convert(ClientType ct) {
		return ct.getType();
	}
	
}
