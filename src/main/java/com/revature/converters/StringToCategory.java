package com.revature.converters;

import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.convert.converter.Converter;

import com.revature.ims_backend.entities.Category;

public class StringToCategory implements Converter<String, Category>, InitializingBean {

	private static Map<String, Category> categories;
	
	@Override
	public Category convert(String category) {
		return categories.get(category);
	}
	
	public static void setCategories(Map<String, Category> categories) {
		StringToCategory.categories = categories;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		
	}
}
