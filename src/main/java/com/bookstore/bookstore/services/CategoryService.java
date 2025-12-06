package com.bookstore.bookstore.services;

import com.bookstore.bookstore.dto.CategoryDTO;
import com.bookstore.bookstore.entities.Category;
import com.bookstore.bookstore.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository repository;

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
        Optional<Category> result = repository.findById(id);
        return new CategoryDTO(result.get());
    }

    @Transactional(readOnly = true)
    public Page<CategoryDTO> findAll(Pageable pageable) {
        Page<Category> result = repository.findAll(pageable);
        return result.map(CategoryDTO::new);
    }

    @Transactional
    public CategoryDTO add(CategoryDTO dto) {
        Category category = new Category();
        dtoToEntity(dto, category);
        return new CategoryDTO(category);
    }

    @Transactional
    public CategoryDTO update(CategoryDTO dto, Long id) {
        Category category = repository.findById(id).get();
        dtoToEntity(dto, category);
        return new CategoryDTO(category);
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    private void dtoToEntity(CategoryDTO dto, Category category) {
        category.setName(dto.getName());
        category = repository.save(category);
    }
}
