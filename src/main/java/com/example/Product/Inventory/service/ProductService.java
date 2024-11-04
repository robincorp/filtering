package com.example.Product.Inventory.service;

import com.example.Product.Inventory.dto.Requestdto;
import com.example.Product.Inventory.dto.Responsedto;
import com.example.Product.Inventory.entity.Product;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {

     Responsedto createProduct(Requestdto reqDto);

     List<Product> getAllProduct();

     ResponseEntity<Object> getProductById(long id);

     Responsedto deleteProduct(Long id);

     List<Product> searchProducts(String name, String category, String sort, int page, int size);

     Responsedto updateProduct(Long id, Requestdto reqDto);
}