package com.revature.dto;

import com.revature.ims_backend.entities.Client;

public class ClientDTO {
	
	public Client client;

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public ClientDTO(Client client) {
		super();
		this.client = client;
	}
	
	public ClientDTO() {
		
	}
}
