package com.revature.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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
	
	private HashMap<String,StateAbbreviation> states = new LinkedHashMap<String,StateAbbreviation>();
	private HashMap<String,ClientType> clientTypes = new HashMap<String,ClientType>();
	
	@Override
	public void afterPropertiesSet() throws Exception {
		DataLayer dl = new DataLayer();
		List<StateAbbreviation> stateList = dl.getStates();
		List<ClientType> clientTypeList = dl.getClientTypes();
		
		for (StateAbbreviation sa: stateList) {
			states.put(sa.getAbbreviation(), sa);
		}
		for (ClientType ct: clientTypeList) {
			clientTypes.put(ct.getType(), ct);
		}
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
			DataLayer dl = new DataLayer();
			Client c = dl.getClient(cid);
			
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
		DataLayer dl = new DataLayer();
		mv.addObject("clients", dl.getAllClients());
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
			DataLayer dl = new DataLayer();
			Client c = dl.getClient(cid);
			
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
		// TODO: Find a cleaner way to validate lookup tables
		if (bindingResult.getFieldError("address.state") != null && bindingResult.getFieldError("address.state").isBindingFailure()
				&& bindingResult.getFieldError("clientType") != null && bindingResult.getFieldError("clientType").isBindingFailure()) {
			String stateParam = request.getParameter("address.state");
			String clientTypeParam = request.getParameter("clientType");
			client.getAddress().setState(states.get(stateParam));
			client.setClientType(clientTypes.get(clientTypeParam));
		}
		
		if (bindingResult.getErrorCount() > 2 || client.getAddress().getState() == null || client.getClientType() == null) {
			mv.setViewName("/edit-client");
			mv.addObject("client", client);
			mv.addObject("states", states);
			mv.addObject("clientTypes", clientTypes);
			return mv;
		}
		else {
			DataLayer dl = new DataLayer();
			dl.beginTransaction();
			dl.insertClient(client);
			dl.commitOrRollback();
			return new ModelAndView("redirect:/clients");
		}
	}
	
	@RequestMapping(value="clients/id/{id}/edit", method=RequestMethod.POST)
	public ModelAndView updateClient(@Valid Client client, BindingResult bindingResult, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		// TODO: Find a cleaner way to validate lookup tables
		if (bindingResult.getFieldError("address.state") != null && bindingResult.getFieldError("address.state").isBindingFailure()
				&& bindingResult.getFieldError("clientType") != null && bindingResult.getFieldError("clientType").isBindingFailure()) {
			String stateParam = request.getParameter("address.state");
			String clientTypeParam = request.getParameter("clientType");
			client.getAddress().setState(states.get(stateParam));
			client.setClientType(clientTypes.get(clientTypeParam));
		}
		
		if (bindingResult.getErrorCount() > 2 || client.getAddress().getState() == null || client.getClientType() == null) {
			mv.setViewName("/edit-client");
			mv.addObject("client", client);
			mv.addObject("states", states);
			mv.addObject("clientTypes", clientTypes);
			return mv;
		}
		else {
			System.out.println(client.getAddress().getId());
			DataLayer dl = new DataLayer();
			dl.beginTransaction();
			dl.updateClient(client);
			dl.commitOrRollback();
			return new ModelAndView("redirect:/clients");
		}
	}
	
	
	
}
