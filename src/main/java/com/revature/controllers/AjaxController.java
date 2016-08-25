package com.revature.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.revature.dto.ClientCollectionDTO;
import com.revature.dto.ClientDTO;
import com.revature.dto.ProductCollectionDTO;
import com.revature.ims_backend.entities.Client;
import com.revature.ims_backend.entities.Product;

@Controller
public class AjaxController {
	
	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private BusinessDelegate bd;
	
	@RequestMapping(value="/api/clients/id/{id}", method=RequestMethod.GET)
	@ResponseBody
	public ClientDTO getSingleClient(@PathVariable("id") String id) {
		try {
			int cid = Integer.parseInt(id);
			ModelAndView mv = new ModelAndView("/single-client");
			Client c = bd.getClient(cid);
			ClientDTO dto = new ClientDTO(c);
			return dto;
			
		} catch (NumberFormatException e) {
			return new ClientDTO();
		}
	}
	
	@RequestMapping(value="/api/clients", method=RequestMethod.GET)
	@ResponseBody
	public ClientCollectionDTO getClients() {
		List<Client> clients = bd.getAllClients();
		return new ClientCollectionDTO(clients);
	}
	
	@RequestMapping(value="/api/products", method=RequestMethod.GET)
	@ResponseBody
	public ProductCollectionDTO getProducts() {
		List<Product> products = new ArrayList<Product>(bd.getProducts());
		return new ProductCollectionDTO(products);
	}
	
	@RequestMapping(value="/api/clients/id/{id}", method=RequestMethod.POST)
	public void editClient(@PathVariable("id") String id, ClientDTO client) {
		bd.updateClient(client.getClient());
	}
	
}
