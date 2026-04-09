package dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import exceptions.DaoException;
import model.CartItem;
import util.DBConnection;

public class OrderDAO {

    private static final String INSERT_ORDER_SQL = "INSERT INTO orders (customer_name, order_date, total_amount) VALUES (?, ?, ?)";

    private static final String INSERT_ORDER_ITEM_SQL =  "INSERT INTO order_items (order_id, product_id, quantity, unit_price) VALUES (?, ?, ?, ?)";

    private static final String UPDATE_STOCK_SQL = "UPDATE products SET stock = stock - ? WHERE id = ? AND stock >= ?";

    public int createOrder(String customerName, List<CartItem> cartItems) {
        validateOrderInput(customerName, cartItems);

        BigDecimal total = calculateTotal(cartItems);

        try (Connection connection = DBConnection.getConnection()) {
            connection.setAutoCommit(false);

            try {
                int orderId = insertOrder(connection, customerName, total);
                insertOrderItems(connection, orderId, cartItems);
                updateStock(connection, cartItems);

                connection.commit();
                return orderId;
            } catch (Exception e) {
                rollbackQuietly(connection);

                if (e instanceof DaoException daoException) {
                    throw daoException;
                }

                throw new DaoException("Failed to create order", e);
            } finally {
                resetAutoCommit(connection);
            }

        } catch (SQLException e) {
            throw new DaoException("Order transaction failed", e);
        }
    }

    
    private void validateOrderInput(String customerName, List<CartItem> cartItems) {
        if (customerName == null || customerName.trim().isEmpty()) {
            throw new DaoException("Customer name is required");
        }

        if (cartItems == null || cartItems.isEmpty()) {
            throw new DaoException("Cart cannot be empty");
        }
    }

    private BigDecimal calculateTotal(List<CartItem> cartItems) {
        return cartItems.stream()
                .map(CartItem::getLineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private int insertOrder(Connection connection, String customerName, BigDecimal total) throws SQLException {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(INSERT_ORDER_SQL, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, customerName);
            preparedStatement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setBigDecimal(3, total);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException("Failed to create order: no row inserted");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (!generatedKeys.next()) {
                    throw new DaoException("Failed to create order: no generated key");
                }
                return generatedKeys.getInt(1);
            }
        }
    }

    private void insertOrderItems(Connection connection, int orderId, List<CartItem> cartItems) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ORDER_ITEM_SQL)) {
            for (CartItem item : cartItems) {
                preparedStatement.setInt(1, orderId);
                preparedStatement.setInt(2, item.getProductId());
                preparedStatement.setInt(3, item.getQuantity());
                preparedStatement.setBigDecimal(4, item.getUnitPrice());
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
        }
    }

    private void updateStock(Connection connection, List<CartItem> cartItems) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_STOCK_SQL)) {
            for (CartItem item : cartItems) {
                preparedStatement.setInt(1, item.getQuantity());
                preparedStatement.setInt(2, item.getProductId());
                preparedStatement.setInt(3, item.getQuantity());
                preparedStatement.addBatch();
            }

            int[] results = preparedStatement.executeBatch();

            for (int affectedRows : results) {
                if (affectedRows == 0) {
                    throw new DaoException("Insufficient stock for one or more items");
                }
            }
        }
    }

    private void rollbackQuietly(Connection connection) {
        try {
            connection.rollback();
        } catch (SQLException rollbackException) {
            throw new DaoException("Failed to rollback transaction", rollbackException);
        }
    }

    private void resetAutoCommit(Connection connection) {
        try {
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new DaoException("Failed to reset auto-commit", e);
        }
    }
}