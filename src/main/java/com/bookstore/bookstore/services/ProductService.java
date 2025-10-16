package com.bookstore.bookstore.services;

import com.bookstore.bookstore.dto.ProductDTO;
import com.bookstore.bookstore.entities.Product;
import com.bookstore.bookstore.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository repository;

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Optional<Product> result = repository.findById(id);
        Product product = result.get();
        return new ProductDTO(product);
    }
}
