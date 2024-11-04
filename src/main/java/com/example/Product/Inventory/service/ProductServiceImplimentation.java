package com.example.Product.Inventory.service;

import com.example.Product.Inventory.ProductDao.ProductDao;
import com.example.Product.Inventory.Repository.ProductRepository;
import com.example.Product.Inventory.dto.Requestdto;
import com.example.Product.Inventory.dto.Responsedto;
import com.example.Product.Inventory.entity.Product;
import com.example.Product.Inventory.facade.ProductFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductServiceImplimentation implements ProductService {

    @Autowired
    private ProductFacade productFacade;
    @Autowired ProductRepository productrepository;
    @Autowired
    ProductDao productDao;

    public Responsedto createProduct(Requestdto reqDto) {
        return productFacade.createProduct(reqDto);
    }

    @Override
    public List<Product> getAllProduct() {
        return productrepository.findAll();
    }

    public ResponseEntity<Object> getProductById(long id) {
        Product product = productrepository.findById(id);
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.ok("Product not found with ID: " + id);
        }
    }
    public Responsedto updateProduct(Long id, Requestdto reqDto) {
        return productFacade.updateProduct(id, reqDto);
    }

    public Responsedto deleteProduct(Long id) {
        return productFacade.deleteProduct(id);
    }

    public List<Product> searchProducts(String name, String category, String sort, int page, int size) {
        return productrepository.searchProducts(name, category, sort, page, size);
    }
}
