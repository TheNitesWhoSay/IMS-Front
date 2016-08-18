package com.revature.controllers;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.ServletContextAware;

@Controller
public class IMS_Controller implements ServletContextAware, InitializingBean {

	@Autowired
	private ServletContext servletContext;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	@RequestMapping(value="test.do", method=RequestMethod.GET)
	public String test() {
		System.out.println("Responding to test...");
		return "test";
	}
}
