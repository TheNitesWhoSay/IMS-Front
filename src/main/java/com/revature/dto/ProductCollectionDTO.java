package com.revature.dto;

import java.util.List;

import com.revature.ims_backend.entities.Product;

public class ProductCollectionDTO {
	
	private int size;
	private List<Product> products;
	public int getLength() {
		return size;
	}
	public void setSize(int length) {
		this.size = length;
	}
	public List<Product> getProducts() {
		return products;
	}
	public void setProducts(List<Product> products) {
		this.products = products;
	}
	public ProductCollectionDTO() {
		super();
	}
	public ProductCollectionDTO(List<Product> products) {
		super();
		this.products = products;
		this.size = products.size();
	}
	
	
}
