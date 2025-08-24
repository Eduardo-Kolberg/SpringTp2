package com.infnet.tp2.service;

import com.infnet.tp2.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final List<Product> products = new ArrayList<>();
    private Long currentId = 1L;

    public List<Product> findAll() {
        return products;
    }

    public Optional<Product> findById(Long id) {
        return products.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst();
    }

    public Product createProduct(Product product) {
        product.setId(currentId++);
        products.add(product);
        return product;
    }

    public Optional<Product> update(Long id, Product product) {
        Optional<Product> existingProduct = findById(id);
        if (existingProduct.isPresent()) {
            product.setId(id);
            products.set(products.indexOf(existingProduct.get()), product);
            return Optional.of(product);
        }
        return Optional.empty();
    }

    public boolean delete(Long id) {
        return products.removeIf(product -> product.getId().equals(id));
    }
}
