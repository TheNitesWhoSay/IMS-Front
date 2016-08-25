package com.revature.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;

import com.revature.ims_backend.entities.Client;
import com.revature.ims_backend.entities.ClientType;
import com.revature.ims_backend.entities.StateAbbreviation;
import com.revature.persist.DataLayer;

@Controller
public class ClientController implements ServletContextAware, InitializingBean {
	
	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private BusinessDelegate bd;
	
	private List<StateAbbreviation> states;
	private List<ClientType> clientTypes;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		DataLayer dl = new DataLayer();
		states = dl.getStates();
		clientTypes = dl.getClientTypes();
		dl.close();
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
			Client c = bd.getClient(cid);
			
			if (c == null) return new ModelAndView("redirect:/clients");
			
			mv.addObject("client", c);
			return mv;
			
		} catch (NumberFormatException e) {
			return new ModelAndView("redirect:/clients");
		}
	}
	
	@RequestMapping(value="clients", method=RequestMethod.GET)
	public ModelAndView viewClients() {
		ModelAndView mv = new ModelAndView("/clients");
		mv.addObject("clients", bd.getAllClients());
		return mv;
	}
	
	@RequestMapping(value="clients/new", method=RequestMethod.GET)
	public ModelAndView newClient() {
		ModelAndView mv = new ModelAndView("/edit-client");
		Client c = new Client();
		c.setId(0);
		mv.addObject("client", c);
		
		mv.addObject("states", states);
		mv.addObject("clientTypes", clientTypes);
		
		return mv;
	}
	
	@RequestMapping(value="clients/id/{id}/edit", method=RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id") String id) {
		try {
			int cid = Integer.parseInt(id);
			ModelAndView mv = new ModelAndView("/edit-client");
			Client c = bd.getClient(cid);
			
			if (c == null) return new ModelAndView("redirect:/clients");
			
			mv.addObject("client", c);
			mv.addObject("states", states);
			mv.addObject("clientTypes", clientTypes);
			return mv;
			
		} catch (NumberFormatException e) {
			return new ModelAndView("redirect:/clients");
		}
	}
	
	@RequestMapping(value="clients/new", method=RequestMethod.POST)
	public ModelAndView createClient(@Valid Client client, BindingResult bindingResult, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
	
		if (bindingResult.hasErrors()) {
			mv.setViewName("/edit-client");
			mv.addObject("client", client);
			mv.addObject("states", states);
			mv.addObject("clientTypes", clientTypes);
			return mv;
		}
		else {
			bd.insertClient(client);
			return new ModelAndView("redirect:/clients");
		}
	}
	
	@ModelAttribute("client")
	@RequestMapping(value="clients/id/{id}/edit", method=RequestMethod.POST)
	public ModelAndView updateClient(@Valid Client client, BindingResult bindingResult, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();

		if (bindingResult.hasErrors()) {
			mv.setViewName("/edit-client");
			mv.addObject("client", client);
			mv.addObject("states", states);
			mv.addObject("clientTypes", clientTypes);
			return mv;
		}
		else {
			bd.updateClient(client);
			return new ModelAndView("redirect:/clients");
		}
	}
	
}
