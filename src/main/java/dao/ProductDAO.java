package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import exceptions.DaoException;
import model.Product;
import util.DBConnection;

//Handles the CRUD Operations for products using the JDBC Operations 
public class ProductDAO {
	 
	//Adding Products
	public boolean add(Product product){
		String sql = "INSERT INTO products(name, description, price, stock) VALUES (?, ?, ?, ?)";
		try(Connection connection = DBConnection.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql)){
			preparedStatement.setString(1, product.getName());
			preparedStatement.setString(2, product.getDescription());
			preparedStatement.setBigDecimal(3, product.getPrice());
			preparedStatement.setInt(4, product.getStock());
			
				return preparedStatement.executeUpdate() > 0;
		} catch  (SQLException e ) {
			throw new DaoException("Failed to add product", e);
		}
		
	}
	
		
	public boolean update(Product product) {
		String sql = "UPDATE products SET name = ? , description = ? , price = ? , stock = ? WHERE id = ?";
		try (Connection connection = DBConnection.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(sql)){
			 preparedStatement.setString(1, product.getName());
			 preparedStatement.setString(2, product.getDescription());
			 preparedStatement.setBigDecimal(3, product.getPrice());
			 preparedStatement.setInt(4, product.getStock());
			 preparedStatement.setInt(5, product.getId());
			 
			 return preparedStatement.executeUpdate() > 0;
		}catch(SQLException e) {
			throw new DaoException("Failed to update product" , e );
		}
	}
	
	
	public boolean delete(int id) {
		String sql = "DELETE FROM products WHERE id = ?";
		try(Connection connection = DBConnection.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql)){
			preparedStatement.setInt(1, id);
			return preparedStatement.executeUpdate() > 0;
		}catch (SQLException e) {
			throw new DaoException("Failed to delete product" , e );
		}
	}
	
	
	public Optional<Product> findById(int id){
		String sql = "SELECT id, name, description, price , stock FROM products WHERE id = ?";
		try(Connection connection = DBConnection.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql)){
			preparedStatement.setInt(1, id);
			try (ResultSet resultSet = preparedStatement.executeQuery()){
				if(resultSet.next()) {
					return Optional.of(mapRow(resultSet));
				}
			}
			return Optional.empty();
		}catch (SQLException e) {
			throw new DaoException("Failed to fetch product by id" , e );
		}
	}
	
	 public List<Product> findAll() {
	        String sql = "SELECT id, name, description, price, stock FROM products ORDER BY id DESC";
	        List<Product> products = new ArrayList<>();
	        try (Connection con = DBConnection.getConnection();
	             PreparedStatement ps = con.prepareStatement(sql);
	             ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                products.add(mapRow(rs));
	            }
	            return products;
	        } catch (SQLException e) {
	            throw new DaoException("Failed to fetch products", e);
	        }
	    }


	private Product mapRow(ResultSet resultSet) throws SQLException {
		 Product p = new Product();
	        p.setId(resultSet.getInt("id"));
	        p.setName(resultSet.getString("name"));
	        p.setDescription(resultSet.getString("description"));
	        p.setPrice(resultSet.getBigDecimal("price"));
	        p.setStock(resultSet.getInt("stock"));
	        return p;
		
	}

}
