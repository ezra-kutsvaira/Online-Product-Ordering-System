package service;

import java.util.List;

import dao.OrderDAO;
import exceptions.ValidationException;
import model.CartItem;

public class OrderService {
	private final OrderDAO orderDAO = new OrderDAO();
	
	public int placeOrder(String customerName, List<CartItem> cartItems) {
		if(customerName == null || customerName.isBlank()) {
			throw new ValidationException("Customer name is required");
		}
		
		if(cartItems == null || cartItems.isEmpty()) {
			throw new ValidationException("Cart is empty");
		}
		
		return orderDAO.createOrder(customerName.trim(), cartItems);
	}
	

}
