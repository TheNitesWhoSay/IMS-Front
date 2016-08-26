package com.revature.dto;

import com.revature.ims_backend.entities.PurchaseOrder;

public class PurchaseOrderDTO {
	private PurchaseOrder invoice;

	public PurchaseOrder getInvoice() {
		return invoice;
	}

	public void setInvoice(PurchaseOrder invoice) {
		this.invoice = invoice;
	}

	public PurchaseOrderDTO(PurchaseOrder invoice) {
		super();
		this.invoice = invoice;
	}

	public PurchaseOrderDTO() {
		super();
	}
	
	
}
