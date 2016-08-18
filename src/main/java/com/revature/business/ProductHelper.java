package com.revature.business;

import java.util.Map;

import com.revature.ims_backend.entities.Category;
import com.revature.ims_backend.entities.Product;

public class ProductHelper {

	public static void addCategoriesToProduct(Product product, String[] categories, Object categoryMap) {
		@SuppressWarnings("unchecked")
		Map<String, Category> categoryLookup = (Map<String, Category>)categoryMap;
		for ( String val : categories ) {
			if ( val != null ) {
				Category category = categoryLookup.get(val);
				if ( category != null ) 
					product.addCategory(category);
			}
		}
	}
}
