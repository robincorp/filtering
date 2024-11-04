
package com.example.Product.Inventory.ProductDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import com.example.Product.Inventory.Repository.ProductRepository;
import com.example.Product.Inventory.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Repository;

@Repository
public class ProductDao implements ProductRepository {

    @Autowired DataSource dataSource;


    public boolean save(Product product) {
        String sql = "INSERT INTO Products (name, category, price, quantity_in_stock) VALUES (?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, product.getName());
            stmt.setString(2, product.getCategory());
            stmt.setDouble(3, product.getPrice());
            stmt.setInt(4, product.getQuantityInStock());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM Products";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Product product = new Product();
                product.setProductID(rs.getLong("productid"));
                product.setName(rs.getString("name"));
                product.setCategory(rs.getString("category"));
                product.setPrice(rs.getDouble("price"));
                product.setQuantityInStock(rs.getInt("quantity_in_stock"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public Product findById(long id) {
        String sql = "SELECT * FROM Products WHERE productid = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Product product = new Product();
                product.setProductID(rs.getLong("productid"));
                product.setName(rs.getString("name"));
                product.setCategory(rs.getString("category"));
                product.setPrice(rs.getDouble("price"));
                product.setQuantityInStock(rs.getInt("quantity_in_stock"));
                return product;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteProduct(Long id) {
        String sql = "DELETE FROM Products WHERE productid = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Product> searchProducts(String name, String category, String sort, int page, int size) {
        List<Product> products = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM Products WHERE 1=1");

        if (name != null && !name.isEmpty()) {
            sql.append(" AND name LIKE ?");
        }
        if (category != null && !category.isEmpty()) {
            sql.append(" AND category = ?");
        }

        if ("desc".equalsIgnoreCase(sort)) {
            sql.append(" ORDER BY price DESC");
        } else {
            sql.append(" ORDER BY price ASC");
        }

        sql.append(" LIMIT ? OFFSET ?");

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

            int index = 1;
            if (name != null && !name.isEmpty()) {
                pstmt.setString(index++, "%" + name + "%");
            }
            if (category != null && !category.isEmpty()) {
                pstmt.setString(index++, category);
            }
            pstmt.setInt(index++, size);
            pstmt.setInt(index++, (page - 1) * size);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setProductID(rs.getLong("productid"));
                product.setName(rs.getString("name"));
                product.setCategory(rs.getString("category"));
                product.setPrice(rs.getDouble("price"));
                product.setQuantityInStock(rs.getInt("quantity_in_stock"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exception as needed
        }

        return products;
    }

public void update(Product product) {
    String sql = "UPDATE products SET name = ?, category = ?, price = ?, quantity_in_stock = ? WHERE productid = ?";
    try (Connection connection = dataSource.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

        preparedStatement.setString(1, product.getName());
        preparedStatement.setString(2, product.getCategory());
        preparedStatement.setDouble(3, product.getPrice());
        preparedStatement.setInt(4, product.getQuantityInStock());
        preparedStatement.setLong(5, product.getProductID());
        preparedStatement.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
}

