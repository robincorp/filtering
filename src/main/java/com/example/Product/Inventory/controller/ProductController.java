package com.example.Product.Inventory.controller;

import com.example.Product.Inventory.Repository.ProductRepository;
import com.example.Product.Inventory.dto.Requestdto;
import com.example.Product.Inventory.dto.Responsedto;
import com.example.Product.Inventory.entity.Product;
import com.example.Product.Inventory.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Pageable;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductRepository productrepository;
    @Autowired
    private ProductService productService;

    @PostMapping("/import")
    public ResponseEntity<Responsedto> createProduct( @RequestBody Requestdto reqDto) {
        Responsedto response = productService.createProduct(reqDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/Getall")
    public ResponseEntity<List<Product>> getAllEmployees() {
        List<Product> product = productService.getAllProduct();
        return ResponseEntity.ok(product);
    }

    @GetMapping("/Particular_product/{id}")
    public ResponseEntity<?> getProductById(@PathVariable long id) {
        return productService.getProductById(id);
    }

@PutMapping("/update/{id}")
public ResponseEntity<String> updateProduct(
        @PathVariable Long id,
        @RequestBody Requestdto requestDto) {


    productService.updateProduct(id, requestDto);

    return ResponseEntity.ok("Product updated successfully.");
}

    @DeleteMapping("/Delete/{id}")
    public ResponseEntity<Responsedto> deleteProduct(@PathVariable Long id) {
        Responsedto response = productService.deleteProduct(id);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "asc") String sort,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

        List<Product> products = productService.searchProducts(name, category, sort, page, size);
        return ResponseEntity.ok(products);
    }
}
