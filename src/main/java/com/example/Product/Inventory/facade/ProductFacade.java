package com.example.Product.Inventory.facade;

import com.example.Product.Inventory.ProductDao.ProductDao;
import com.example.Product.Inventory.dto.Requestdto;
import com.example.Product.Inventory.dto.Responsedto;
import com.example.Product.Inventory.entity.Product;
import com.example.Product.Inventory.Repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class ProductFacade {

    @Autowired
    private ProductRepository productRepository;


  @Autowired private final ProductDao productDao;

    public ProductFacade(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Transactional
    public Responsedto createProduct(Requestdto reqDto) {
        StringBuilder missingFields = new StringBuilder();

        if (reqDto.getName() == null || reqDto.getName().isEmpty()) {
            missingFields.append("Name, ");
        }
        if (reqDto.getCategory() == null || reqDto.getCategory().isEmpty()) {
            missingFields.append("Category, ");
        }
        if (reqDto.getPrice() <= 0) {
            missingFields.append("Price, ");
        }
        if (reqDto.getQuantityInStock() <= 0) {
            missingFields.append("Quantity in stock, ");
        }

        if (missingFields.length() > 0) {
            String missingFieldsStr = missingFields.substring(0, missingFields.length() - 2);
            return new Responsedto("Missing or invalid fields - " + missingFieldsStr);
        }

        Product newProduct = reqDto.getproductdetails();
        boolean isSaved = productRepository.save(newProduct);

        if (isSaved) {
            return new Responsedto("Product saved successfully");
        } else {
            return new Responsedto("Failed to save product");
        }
    }
    public Responsedto updateProduct(Long id, Requestdto reqDto) {
        try {
            Optional<Product> existingProduct = Optional.ofNullable(productDao.findById(id));

            if (existingProduct.isPresent()) {
                Product product = existingProduct.get();

                // Update fields based on Requestdto, ensuring non-null checks
                if (reqDto.getName() != null) {
                    product.setName(reqDto.getName());
                }
                if (reqDto.getCategory() != null) {
                    product.setCategory(reqDto.getCategory());
                }
                if (reqDto.getPrice() >= 0) {
                    product.setPrice(reqDto.getPrice());
                }
                if (reqDto.getQuantityInStock() >= 0) {
                    product.setQuantityInStock(reqDto.getQuantityInStock());
                }

                productDao.update(product);
                return new Responsedto("Product updated successfully");

            } else {
                return new Responsedto("Product not found with ID " + id);
            }

        } catch (EmptyResultDataAccessException e) {
            // Handle case where the product is not found
            return new Responsedto("Product not found with ID " + id);
        } catch (Exception e) {
            // Handle other unexpected exceptions
            return new Responsedto("An error occurred while updating the product: " + e.getMessage());
        }
    }


@Transactional
public Responsedto deleteProduct(Long id) {
    boolean isDeleted = productRepository.deleteProduct(id);
    if (isDeleted) {
        return new Responsedto("Product deleted successfully");
    } else {
        return new Responsedto("Product not found with ID: " + id);
    }
}
}





