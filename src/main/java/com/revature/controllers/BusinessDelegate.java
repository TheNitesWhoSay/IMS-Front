package com.revature.controllers;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.revature.ims_backend.entities.Category;
import com.revature.ims_backend.entities.Client;
import com.revature.ims_backend.entities.ClientType;
import com.revature.ims_backend.entities.OrderLine;
import com.revature.ims_backend.entities.Product;
import com.revature.ims_backend.entities.ProductImage;
import com.revature.ims_backend.entities.PurchaseOrder;
import com.revature.ims_backend.entities.StateAbbreviation;
import com.revature.ims_backend.entities.Stock;
import com.revature.persist.DataLayer;

@Component
@Scope(value="request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class BusinessDelegate implements DisposableBean {
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
	
	public boolean insertImage(Product product, ProductImage productImage) {
		dataLayer.beginTransaction();
		dataLayer.insertProductImage(productImage);
		dataLayer.updateProduct(product);
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
	
	/**
	 * Calculates total value of the inventory
	 * not described within the invoices
	 * @return The total value of the inventory minus the net value described in invoices
	 */
	public double getTotalUnaccountedInventoryValue() {
		
		double totalInventoryValue = 0;
		Set<Product> products = dataLayer.getProducts();
		for ( Product product : products ) {
			double unitCost = product.getUnitCost();
			double numInStock = (double)product.getStock().getNumInStock();
			double productStockValue = unitCost*numInStock;
			totalInventoryValue += productStockValue;
		}
		
		double totalValueIn = 0;
		double totalValueOut = 0;
		Set<PurchaseOrder> purchaseOrders = dataLayer.getPurchaseOrders();
		for ( PurchaseOrder purchaseOrder : purchaseOrders ) {
			ClientType clientType = purchaseOrder.getClient().getClientType();
			if ( clientType.isRetailer() ) {
				totalValueOut += purchaseOrder.getSubTotal();
			} else if ( clientType.isSupplier() ) {
				totalValueIn += purchaseOrder.getSubTotal();
			}
		}
		double netAccountedValue = totalValueIn - totalValueOut;
		double totalUnaccountedValue = totalInventoryValue - netAccountedValue;
		
		return totalUnaccountedValue;
	}
	
	public LocalDate toLocalDate(Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}
	
	public List<Double> getDailyInventoryValues() {
		
		List<Double> dailyInventoryValue = new ArrayList<Double>();
		double totalUnaccountedValue = getTotalUnaccountedInventoryValue();
		
		Set<PurchaseOrder> purchaseOrders = dataLayer.getPurchaseOrders();
		if ( purchaseOrders.size() <= 0 )
			return null;
		
		List<PurchaseOrder> chronologicalPurchaseOrders = new ArrayList<PurchaseOrder>();
		
		while ( purchaseOrders.size() > 0 ) {
			PurchaseOrder earliest = purchaseOrders.iterator().next();
			for ( PurchaseOrder purchaseOrder : purchaseOrders ) {
				Date purchaseDate = purchaseOrder.getPurchaseDate();
				if ( purchaseDate.before(earliest.getPurchaseDate()) ) {
					earliest = purchaseOrder;
				}
			}
			chronologicalPurchaseOrders.add(earliest);
			purchaseOrders.remove(earliest);
		}
		
		LocalDate startDate = toLocalDate(
				chronologicalPurchaseOrders.get(0).getPurchaseDate()).minusDays(1);
		LocalDate endDate = toLocalDate(new Date());
		
		Iterator<PurchaseOrder> i = chronologicalPurchaseOrders.iterator();
		PurchaseOrder currOrder = i.next();
		
		double runningValue = totalUnaccountedValue;
		for ( LocalDate currDate = startDate;
			  currDate.compareTo(endDate) < 0;
			  currDate = currDate.plusDays(1) )
		{
			while ( currOrder != null &&
					toLocalDate(currOrder.getPurchaseDate()).compareTo(currDate) == 0 )
			{
				if ( currOrder.getClient().getClientType().isSupplier() ) {
					runningValue += currOrder.getSubTotal();
				} else if ( currOrder.getClient().getClientType().isRetailer() ) {
					runningValue -= currOrder.getSubTotal();
				}
				if ( i.hasNext() ) {
					currOrder = i.next();
				} else {
					currOrder = null;
				}
			}
			dailyInventoryValue.add(runningValue);
		}
		return dailyInventoryValue;
	}

	public void destroy() throws Exception {
		try {
			finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

//	public List<Product> getInventoryLevels() {
//		List<Stock> inventoryLevels = new ArrayList<Stock>();
//		inventoryLevels.addAll(dataLayer.getInventoryLevels());
//		for ( Stock stock : inventoryLevels ) {
//			if ( stock.getProduct() != null ) {
//				stock.getProduct().setStock(null);
//				stock.getProduct().setCategories(null);
//			}
//		}
//		return inventoryLevels;
//	}
	
	public PurchaseOrder getInvoice(int id) {
		return dataLayer.getInvoice(id);
	}
	
	public boolean processInvoice(PurchaseOrder po) {
		po.setPurchaseDate(new Date());
		int i = 1;
		for (OrderLine line: po.getOrderLines()) {
			line.setLineNumber(i);
			line.setProduct(this.getProductByUpc(line.getProduct().getUpc()));
			int upc = line.getProduct().getUpc();
			int stock = line.getProduct().getStock().getNumInStock();
			if (po.getClient().getClientType().getType().equals("Supplier"))
				this.updateStock(upc, stock + line.getQuantityOrdered());
			else
				this.updateStock(upc, stock + line.getQuantityOrdered());
			i++;
		}
		dataLayer.beginTransaction();
		dataLayer.insertPurchaseOrder(po);
		dataLayer.commitOrRollback();
		return true;
	}
	
}
