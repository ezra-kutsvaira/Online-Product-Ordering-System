package dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.sql.SQLException;

import exceptions.DaoException;
import model.CartItem;
import util.DBConnection;

public class OrderDAO {
	
	public int createOrder(String customerName, List<CartItem> cartItems) {
		String insertOrder =  "INSERT INTO orders(customer_name, order_date, total_amount) VALUES (?, ?, ?)";
		String insertItem = "INSERT INTO order_items (order_id, product_id, quantity, unit_price) VALUES (? , ? , ? , ?)";
		String updateStock = "UPDATE products SET stock = stock - ? WHERE id = ? AND stock >= ?";
		
		//Calculate the total cost of items 
		BigDecimal total = cartItems.stream()
				.map(CartItem::getLineTotal)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		
		try ( Connection connection = DBConnection.getConnection()){
			//Disabling the auto commit of queries
			connection.setAutoCommit(false);
			
			//Generating the order IDs 
			try {
				int orderId;
				try(PreparedStatement preparedStatement = connection.prepareStatement(insertOrder, Statement.RETURN_GENERATED_KEYS)){
					preparedStatement.setString(1, customerName);
					preparedStatement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
					preparedStatement.setBigDecimal(3, total);
					preparedStatement.executeUpdate();
					
					try (ResultSet keys = preparedStatement.getGeneratedKeys()) {
                        if (!keys.next()) {
                            throw new DaoException("Failed to create order: no generated key");
                        }
                        orderId = keys.getInt(1);
                    }
				}
				
				try(PreparedStatement itemPreparedStatement = connection.prepareStatement(insertItem);
					PreparedStatement stockPreparedStatement = connection.prepareStatement(updateStock)){
					
					for (CartItem item : cartItems) {
                        itemPreparedStatement.setInt(1, orderId);
                        itemPreparedStatement.setInt(2, item.getProductId());
                        itemPreparedStatement.setInt(3, item.getQuantity());
                        itemPreparedStatement.setBigDecimal(4, item.getUnitPrice());
                        itemPreparedStatement.addBatch();

                        stockPreparedStatement.setInt(1, item.getQuantity());
                        stockPreparedStatement.setInt(2, item.getProductId());
                        stockPreparedStatement.setInt(3, item.getQuantity());
                        stockPreparedStatement.addBatch();
                    }
					
					itemPreparedStatement.executeBatch();
                    int[] stockResult =  stockPreparedStatement.executeBatch();
                    for (int affected : stockResult) {
                        if (affected == 0) {
                            throw new DaoException("Insufficient stock for one or more items");
				}
			}
		}
		
				connection.commit();
                return orderId;
            } catch (Exception e) {
                connection.rollback();
                if (e instanceof DaoException daoException) {
                    throw daoException;
                }
                throw new DaoException("Failed to create order", e);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new DaoException("Order transaction failed", e);
        }
		
	}

}
