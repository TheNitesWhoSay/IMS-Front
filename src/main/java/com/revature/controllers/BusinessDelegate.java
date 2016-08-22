package com.revature.controllers;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.revature.ims_backend.entities.Category;
import com.revature.ims_backend.entities.Client;
import com.revature.ims_backend.entities.ClientType;
import com.revature.ims_backend.entities.Product;
import com.revature.ims_backend.entities.StateAbbreviation;
import com.revature.ims_backend.entities.Stock;
import com.revature.persist.DataLayer;

@Component
@Scope(value="request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class BusinessDelegate implements DisposableBean {
	private DataLayer dataLayer = new DataLayer();
	
	public BusinessDelegate() {
		System.out.println(this);
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

	protected void finalize() throws Throwable {
		if ( dataLayer != null ) {
			dataLayer.close();
			dataLayer = null;
		}
	}

	public Client getClient(int cid) {
		return dataLayer.getClient(cid);
	}

	public List<Client> getAllClients() {
		return dataLayer.getAllClients();
	}

	public void insertClient(Client client) {
		dataLayer.beginTransaction();
		dataLayer.insertClient(client);
		dataLayer.commitOrRollback();
	}
	
	public List<StateAbbreviation> getStates() {
		return dataLayer.getStates();
	}

	public List<ClientType> getClientTypes() {
		return dataLayer.getClientTypes();
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

	public void updateClient(Client client) {
		dataLayer.beginTransaction();
		dataLayer.updateClient(client);
		dataLayer.commitOrRollback();
	}

	public void destroy() throws Exception {
		try {
			finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
}
