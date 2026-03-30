package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Order {
	private int id;
	private String customerName;
	private LocalDateTime orderDate;
	private BigDecimal totalAmount;
	
	
	public Order(int id, String customerName, LocalDateTime orderDate, BigDecimal totalAmount) {
		
		this.id = id;
		this.customerName = customerName;
		this.orderDate = orderDate;
		this.totalAmount = totalAmount;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getCustomerName() {
		return customerName;
	}


	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}


	public LocalDateTime getOrderDate() {
		return orderDate;
	}


	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}


	public BigDecimal getTotalAmount() {
		return totalAmount;
	}


	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	
	
	

}
