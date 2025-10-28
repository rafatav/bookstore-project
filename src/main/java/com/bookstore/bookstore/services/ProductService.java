package com.bookstore.bookstore.services;

import com.bookstore.bookstore.dto.ProductDTO;
import com.bookstore.bookstore.entities.Product;
import com.bookstore.bookstore.repositories.ProductRepository;
import com.bookstore.bookstore.services.exceptions.DatabaseException;
import com.bookstore.bookstore.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository repository;

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        try {
            Optional<Product> result = repository.findById(id);
            Product product = result.get();
            return new ProductDTO(product);
        } catch (NoSuchElementException e) {
            throw new ResourceNotFoundException("Resource Not Found");
        }
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(ProductDTO::new);
    }

    @Transactional
    public ProductDTO insert(ProductDTO dto) {
        Product product = new Product();
        dtoToEntity(dto, product);
        repository.save(product);
        return new ProductDTO(product);
    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO dto) {
        try {
            Product product = repository.getReferenceById(id);
            dtoToEntity(dto, product);
            product = repository.save(product);
            return new ProductDTO(product);
        } catch (EntityNotFoundException e) {
            throw new DatabaseException("Data Not Found");
        }
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    private void dtoToEntity(ProductDTO dto, Product product) {
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setImgUrl(dto.getImgUrl());
    }
}
