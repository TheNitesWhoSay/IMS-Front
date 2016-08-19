package com.revature.controllers;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.revature.ims_backend.entities.Category;
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
	
	public List<Category> getCategories() {
		return dataLayer.getCategories();
	}
	
}
