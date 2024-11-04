package com.example.Product.Inventory.dto;

import com.example.Product.Inventory.entity.Product;
import lombok.Data;

@Data
public class Requestdto {

    private String name;
    private String category;
    private double price;
    private int quantityInStock;

    public Product getproductdetails() {

        Product productinventory = new Product();
        productinventory.setName(this.name);
        productinventory.setCategory(this.category);
        productinventory.setPrice(this.price);
        productinventory.setQuantityInStock(this.quantityInStock);

        return productinventory;
    }
}
