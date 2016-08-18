package com.revature.controllers;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;

import com.revature.ims_backend.entities.Address;
import com.revature.ims_backend.entities.Client;
import com.revature.ims_backend.entities.ClientType;
import com.revature.ims_backend.entities.StateAbbreviation;

@Controller
public class ClientController implements ServletContextAware, InitializingBean {
	
	@Autowired
	private ServletContext servletContext;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	@RequestMapping(value="clients/id/{id}", method=RequestMethod.GET)
	public ModelAndView viewSingleClient(@PathVariable("id") String id) {
		try {
			int cid = Integer.parseInt(id);
			ModelAndView mv = new ModelAndView("/single-client");
			Client c = new Client();
			Address a = new Address();
			StateAbbreviation s = new StateAbbreviation();
			ClientType ct = new ClientType();
			ct.setType("Incoming");
			s.setId(1);
			s.setName("Virginia");
			s.setAbbreviation("VA");
			a.setAddress1("11730 Plaza America Drive");
			a.setAddress2("#205");
			a.setCity("Reston");
			a.setState(s);
			a.setZip("20190");
			c.setName("Revature");
			c.setAddress(a);
			c.setEmail("revature@revature.com");
			c.setFaxNumber("");
			c.setPhoneNumber("7777777777");
			c.setPointOfContactName("Avery Rose");
			c.setId(cid);

			mv.addObject("client", c);
			return mv;
		} catch (NumberFormatException e) {
			return new ModelAndView("redirect:/clients");
		}
	}
	
	@RequestMapping(value="clients", method=RequestMethod.GET)
	public String viewClients() {
		return "/clients";
	}
}
