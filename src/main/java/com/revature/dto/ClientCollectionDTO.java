package com.revature.dto;

import java.util.List;

import com.revature.ims_backend.entities.Client;

public class ClientCollectionDTO {
	
	private int size;
	private List<Client> clients;
	public int getLength() {
		return size;
	}
	public void setSize(int length) {
		this.size = length;
	}
	public List<Client> getClients() {
		return clients;
	}
	public void setClients(List<Client> clients) {
		this.clients = clients;
	}
	public ClientCollectionDTO() {
		super();
	}
	public ClientCollectionDTO(List<Client> clients) {
		super();
		this.clients = clients;
		this.size = clients.size();
	}
	
	
}
