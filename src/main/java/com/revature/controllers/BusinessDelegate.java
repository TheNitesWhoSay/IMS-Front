package com.revature.controllers;

import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.revature.ims_backend.entities.Category;
import com.revature.ims_backend.entities.Product;
import com.revature.ims_backend.entities.Stock;
import com.revature.persist.DataLayer;

@Component
@Scope(value="session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class BusinessDelegate {

	private DataLayer dataLayer = new DataLayer();
	
	public BusinessDelegate() {
		
	}
	
	public DataLayer getDataLayer() {
		return dataLayer;
	}
	
	public void setDataLayer(DataLayer dataLayer) {
		
		if ( this.dataLayer != dataLayer ) {
			
			if ( this.dataLayer != null )
				this.dataLayer.close();
			
			this.dataLayer = dataLayer;
		}
	}

	@Override
	protected void finalize() throws Throwable {
		if ( dataLayer != null ) {
			dataLayer.close();
			dataLayer = null;
		}
	}
	
	public Set<Category> getCategories() {
		return dataLayer.getCategories();
	}
	
	public Set<Product> getProducts() {
		return dataLayer.getProducts();
	}

	public Product getProductByUpc(int upc) {
		return dataLayer.getProductByUpc(upc);
	}

	public boolean insertProduct(Product product) {
		dataLayer.beginTransaction();
		dataLayer.insertStock(product.getStock());
		for ( Category category : product.getCategories() )
			category.addProduct(product);
		dataLayer.insertProduct(product);
		for ( Category category : product.getCategories() )
			dataLayer.updateCategory(category);
		return dataLayer.commitOrRollback();
	}

	public boolean insertCategory(Category category) {
		dataLayer.beginTransaction();
		dataLayer.insertCategory(category);
		return dataLayer.commitOrRollback();
	}
	
	public boolean insertStock(Stock stock) {
		dataLayer.beginTransaction();
		dataLayer.insertStock(stock);
		return dataLayer.commitOrRollback();
	}

	public boolean updateStock(int productUpc, int stockAmount) {
		dataLayer.beginTransaction();
		Product product = getProductByUpc(productUpc);
		product.getStock().setNumInStock(stockAmount);
		dataLayer.updateProduct(product);
		return dataLayer.commitOrRollback();
	}
	
}
