package model;

import java.math.BigDecimal;

public class CartItem {
	private int productId;
	private String productName;
	private BigDecimal unitPrice;
	private int quantity;
	
	//Default Constructor
	public CartItem () {
		
	}

	public CartItem(int productId, String productName, BigDecimal unitPrice, int quantity) {
		this.productId = productId;
		this.productName = productName;
		this.unitPrice = unitPrice;
		this.quantity = quantity;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public BigDecimal getLineTotal () {
		return unitPrice.multiply(BigDecimal.valueOf(quantity));
	}
	
	
	
}
