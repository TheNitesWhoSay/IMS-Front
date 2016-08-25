package com.revature.converters;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import com.revature.controllers.BusinessDelegate;
import com.revature.ims_backend.entities.ClientType;
import com.revature.persist.DataLayer;

public class StringToClientType implements Converter<String, ClientType>, InitializingBean {
	
	private static HashMap<String, ClientType> clientTypes = new HashMap<>();

	@Override
	public ClientType convert(String ct) {
		return clientTypes.get(ct);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		DataLayer dl = new DataLayer();
		List<ClientType> clientTypeList = dl.getClientTypes();
		for (ClientType ct: clientTypeList) {
			clientTypes.put(ct.getType(), ct);
		}
		dl.close();
	}
	
}
