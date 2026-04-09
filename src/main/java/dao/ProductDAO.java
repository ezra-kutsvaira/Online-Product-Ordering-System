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


public class ProductDAO {

    private static final String INSERT_PRODUCT_SQL =
            "INSERT INTO products (name, description, price, stock) VALUES (?, ?, ?, ?)";

    private static final String UPDATE_PRODUCT_SQL =
            "UPDATE products SET name = ?, description = ?, price = ?, stock = ? WHERE id = ?";

    private static final String DELETE_PRODUCT_SQL =
            "DELETE FROM products WHERE id = ?";

    private static final String FIND_PRODUCT_BY_ID_SQL =
            "SELECT id, name, description, price, stock FROM products WHERE id = ?";

    private static final String FIND_ALL_PRODUCTS_SQL =
            "SELECT id, name, description, price, stock FROM products ORDER BY id DESC";

    public boolean add(Product product) {
        validateProduct(product);

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCT_SQL)) {

            setProductParametersForInsert(preparedStatement, product);
            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DaoException("Failed to add product", e);
        }
    }

    public boolean update(Product product) {
        validateProduct(product);

        if (product.getId() <= 0) {
            throw new DaoException("Product id must be valid for update");
        }

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PRODUCT_SQL)) {

            setProductParametersForUpdate(preparedStatement, product);
            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DaoException("Failed to update product", e);
        }
    }

    public boolean delete(int id) {
        if (id <= 0) {
            throw new DaoException("Product id must be valid for delete");
        }

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PRODUCT_SQL)) {

            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DaoException("Failed to delete product", e);
        }
    }

    public Optional<Product> findById(int id) {
        if (id <= 0) {
            throw new DaoException("Product id must be valid");
        }

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_PRODUCT_BY_ID_SQL)) {

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapRow(resultSet));
                }
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw new DaoException("Failed to fetch product by id", e);
        }
    }

    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_PRODUCTS_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                products.add(mapRow(resultSet));
            }

            return products;

        } catch (SQLException e) {
            throw new DaoException("Failed to fetch products", e);
        }
    }

    private void validateProduct(Product product) {
        if (product == null) {
            throw new DaoException("Product cannot be null");
        }

        if (product.getName() == null || product.getName().trim().isEmpty()) {
            throw new DaoException("Product name is required");
        }

        if (product.getPrice() == null) {
            throw new DaoException("Product price is required");
        }

        if (product.getStock() < 0) {
            throw new DaoException("Product stock cannot be negative");
        }
    }

    private void setProductParametersForInsert(PreparedStatement preparedStatement, Product product) throws SQLException {
        preparedStatement.setString(1, product.getName());
        preparedStatement.setString(2, product.getDescription());
        preparedStatement.setBigDecimal(3, product.getPrice());
        preparedStatement.setInt(4, product.getStock());
    }

    private void setProductParametersForUpdate(PreparedStatement preparedStatement, Product product) throws SQLException {
        preparedStatement.setString(1, product.getName());
        preparedStatement.setString(2, product.getDescription());
        preparedStatement.setBigDecimal(3, product.getPrice());
        preparedStatement.setInt(4, product.getStock());
        preparedStatement.setInt(5, product.getId());
    }

    private Product mapRow(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getInt("id"));
        product.setName(resultSet.getString("name"));
        product.setDescription(resultSet.getString("description"));
        product.setPrice(resultSet.getBigDecimal("price"));
        product.setStock(resultSet.getInt("stock"));
        return product;
    }
}