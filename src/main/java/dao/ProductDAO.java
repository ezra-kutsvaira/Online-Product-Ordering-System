package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import model.Product;
import util.DBConnection;

//Handles the CRUD Operations for products using the JDBC Operations 
public class ProductDAO {
	 
	//Adding Products
	public boolean add(Product product) throws SQLException{
		String sql = "INSERT INTO products(name, description, price, stock) VALUES (?, ?, ?, ?)";
		try(Connection connection = DBConnection.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql)){
				preparedStatement.setString(1, product.getName());
				preparedStatement.setString(2, product.getDescription());
				preparedStatement.setBigDecimal(3, product.getPrice());
				preparedStatement.setInt(4, product.getStock());
				
				return preparedStatement.executeUpdate() > 0;
		
		}	
		
	}
	

}
