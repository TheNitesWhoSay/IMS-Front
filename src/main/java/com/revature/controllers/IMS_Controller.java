package com.revature.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;

import com.revature.ims_backend.entities.Address;
import com.revature.ims_backend.entities.Client;
import com.revature.ims_backend.entities.ClientType;
import com.revature.ims_backend.entities.StateAbbreviation;
import com.revature.business.ProductHelper;
import com.revature.ims_backend.entities.Category;
import com.revature.ims_backend.entities.Product;

@Controller
public class IMS_Controller implements ServletContextAware, InitializingBean {

	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private BusinessDelegate bd; // Using spring magic, this should be per-session
	
	@Override
	public void afterPropertiesSet() throws Exception {

		List<Category> categories = bd.getCategories();
		Map<String, Category> categoryLookup = new HashMap<String, Category>();
		for ( Category category : categories ) {
			categoryLookup.put(category.getDescription(), category);
		}
		
		servletContext.setAttribute("listOfCategories", categories);
		servletContext.setAttribute("categoryLookup", categoryLookup);
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	@RequestMapping(value="test.do", method=RequestMethod.GET)
	public String test() {
		return "test";
	}
	
	@RequestMapping(value="manageProducts.do", method=RequestMethod.GET)
	public String manageProducts(HttpServletRequest req) {
		req.setAttribute("product", new Product());
		return "manage-products";
	}
	
	@RequestMapping(value="editProduct.do", method=RequestMethod.POST)
	public ModelAndView editProduct(@Valid Product product,
			BindingResult bindingResult, ModelMap map,
			HttpServletRequest req, HttpServletResponse resp)
	{
		ProductHelper.addCategoriesToProduct(product, req.getParameterValues("categories"),
				servletContext.getAttribute("categoryLookup"));
		
		System.out.println(product);
		
		return new ModelAndView("manage-products");
	}
}
