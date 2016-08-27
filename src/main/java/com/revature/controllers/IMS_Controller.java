package com.revature.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.revature.business.FileUploader;
import com.revature.converters.StringToCategory;
import com.revature.ims_backend.entities.Category;
import com.revature.ims_backend.entities.Product;
import com.revature.ims_backend.entities.ProductImage;
import com.revature.ims_backend.entities.Stock;
import com.revature.logging.Log;

@Controller
public class IMS_Controller implements ServletContextAware, InitializingBean
{

	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private BusinessDelegate bd; // Using spring magic, this should be per-session
	
	@Override
	public void afterPropertiesSet() throws Exception {
		
		BusinessDelegate temp = new BusinessDelegate();
		cacheCategories(temp);
		cacheProducts(temp);
		temp.destroy();
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	public void cacheCategories(BusinessDelegate businessDelegate) {
		
		Set<Category> categories = businessDelegate.getCategories();
		Map<String, Category> categoryLookup = new HashMap<String, Category>();
		for ( Category category : categories ) {
			categoryLookup.put(category.getDescription(), category);
		}
		StringToCategory.setCategories(categoryLookup);
		servletContext.setAttribute("listOfCategories", categories);
		servletContext.setAttribute("categoryLookup", categoryLookup);
	}
	public void cacheCategories() {
		cacheCategories(bd);
	}
	
	public void cacheProducts(BusinessDelegate businessDelegate) {
		
		Set<Product> products = businessDelegate.getProducts();
		servletContext.setAttribute("listOfProducts", products);
	}
	public void cacheProducts() {
		cacheProducts(bd);
	}
	
	public void initCache() {
		// Unsynchronized check for performance
		if ( servletContext.getAttribute("cacheInitialized") == null ) {
			synchronized ( servletContext ) {
				// Synchronized check to avoid race conditions
				if ( servletContext.getAttribute("cacheInitialized") == null ) {
					cacheCategories();
					cacheProducts();
					servletContext.setAttribute("cacheInitialized", true);
				}
			}
		}
	}

	@RequestMapping(value="test.do", method=RequestMethod.GET)
	public String test() {
		initCache();
		return "test";
	}
	
	@RequestMapping(value="manageInventory.do", method=RequestMethod.GET)
	public String manageInventory(HttpServletRequest req) {
		req.setAttribute("stock", new Stock());
		return "manage-inventory";
	}
	
	@RequestMapping(value="manageProducts.do", method=RequestMethod.GET)
	public String manageProducts(HttpServletRequest req) {
		req.setAttribute("product", new Product());
		return "manage-products";
	}
	
	@RequestMapping(value="manageCategories.do", method=RequestMethod.GET)
	public String manageCategories(HttpServletRequest req) {
		req.setAttribute("category", new Category());
		return "manage-categories";
	}
	
	@RequestMapping(value="generateReports.do", method=RequestMethod.GET)
	public String generateReports(HttpServletRequest req) {
		req.setAttribute("dailyInventoryValues", bd.getDailyInventoryValues());
		return "generate-reports";
	}
	
	
	@RequestMapping(value="editStock.do", method=RequestMethod.POST)
	public ModelAndView editStock(@Valid Stock stock,
			BindingResult bindingResult, ModelMap map,
			HttpServletRequest req, HttpServletResponse resp)
	{
		int upc = Integer.parseInt(req.getParameter("product"));
		bd.updateStock(upc, stock.getNumInStock());
		cacheProducts();
		return new ModelAndView("manage-inventory");
	}
	
	@RequestMapping(value="editProduct.do", method=RequestMethod.POST)
	public ModelAndView editProduct(@Valid Product product,
			BindingResult bindingResult, ModelMap map,
			HttpServletRequest req, HttpServletResponse resp)
	{	
		if (bindingResult.hasErrors()) {
			return new ModelAndView("manage-products");
		}
		/*
		ProductHelper.addCategoriesToProduct(product, req.getParameterValues("categories"),
				servletContext.getAttribute("categoryLookup"));
		*/
		bd.insertProduct(product);
		cacheProducts();
		return new ModelAndView("manage-products");
	}
	
	@RequestMapping(value="editCategory.do", method=RequestMethod.POST)
	public ModelAndView editCategory(@Valid Category category,
			BindingResult bindingResult, ModelMap map,
			HttpServletRequest req, HttpServletResponse resp)
	{
		Log.info(category);
		if ( !bd.insertCategory(category) )
			Log.info("insert failed");
		cacheCategories();
		return new ModelAndView("manage-categories");
	}
	
	public void saveFile(MultipartFile file, String fileName)
	{    
	    File convFile = new File("/tmp/" + fileName);
	    try {
	    	convFile.createNewFile();
		    FileOutputStream fos = new FileOutputStream(convFile); 
		    fos.write(file.getBytes());
		    fos.close();
	    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="addImage.do", method=RequestMethod.POST)
	public String uploadFileHandler(
			@RequestParam("upc") String upc,
			@RequestParam("file") MultipartFile file,
			HttpServletRequest req) {

		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();
				int fileSize = bytes.length;
				String fileExtension =
						file.getOriginalFilename().substring(
								file.getOriginalFilename().lastIndexOf("."));
				String fileName = upc + fileExtension;
				saveFile(file, fileName);
				String url = FileUploader.uploadFile(fileName, new File(fileName));
				ProductImage productImage = new ProductImage(0, url);
				Product product = bd.getProductByUpc(Integer.parseInt(upc));
				product.setImage(productImage);
				bd.insertImage(product, productImage);
				cacheProducts();
				Log.info("upc: " + upc);
				Log.info("fileName: " + fileName);
				Log.info("File length: " + fileSize);
			} catch (Exception e) {
				Log.info("File upload failed: " + e.getMessage());
			}
		} else {
			Log.info("File uploaded was empty!");
		}
		req.setAttribute("product", new Product());
		return "manage-products";
	}

}
