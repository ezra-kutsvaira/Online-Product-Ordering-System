package service;

import java.math.BigDecimal;
import java.util.List;

import dao.ProductDAO;
import exceptions.NotFoundException;
import exceptions.ValidationException;
import model.Product;

public class ProductService {
	
	private final ProductDAO productDAO = new ProductDAO ();
	
	//finding all the products
	public List<Product> getAllProducts(){
		return productDAO.findAll();
	}
	
	//product by Id
	public Product getProductById(int id) {
		return productDAO.findById(id)
				.orElseThrow(() -> new NotFoundException("Product id not found" + id));
	}
	
	public void createProduct(Product product) {
		validate(product);
		boolean created = productDAO.add(product);
		if(!created) {
			throw new ValidationException("Product was not created");
		}
	}
	
	
	public void updateProduct(Product product) {
		validate(product);
		if(product.getId() <= 0) {
			throw new ValidationException("Product ID must be provided for update");
		}
		
		boolean updated = productDAO.update(product);
		if(!updated) {
			throw new NotFoundException("Product not found for update");
		}
	}
	
	
	 public void deleteProduct(int id) {
	        boolean deleted = productDAO.delete(id);
	        if (!deleted) {
	            throw new NotFoundException("Product not found for delete");
	        }
	    }
	
	
	//Product creation validation
	private void validate(Product product) {
		if(product.getName() == null || product.getName().isBlank()) {
			throw new ValidationException("Product name is required");
		}
		
		if (product.getDescription() == null || product.getDescription().isBlank()) {
            throw new ValidationException("Product description is required");
        }
		
        if (product.getPrice() == null || product.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("Product price must be greater than zero");
        }
        
        if (product.getStock() < 0) {
            throw new ValidationException("Product stock cannot be negative");
        }
		
	}
	
}
