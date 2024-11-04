package com.example.Product.Inventory.Repository;

import com.example.Product.Inventory.entity.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository {

     boolean save(Product product);

     List<Product> findAll();

     Product findById(long id);

     boolean deleteProduct(Long id);

     void update(Product product);

     List<Product> searchProducts(String name, String category, String sort, int page, int size);
}