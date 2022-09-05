package com.github.ronnyaraujo.saleapi.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.github.ronnyaraujo.saleapi.exceptions.ObjectNotFoundException;
import com.github.ronnyaraujo.saleapi.model.CategoryModel;
import com.github.ronnyaraujo.saleapi.repository.CategoryRepository;

@Service
public class CategoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryService.class);

    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryModel> getCategories() {
        LOGGER.info("Getting all categories");
        List<CategoryModel> categories = categoryRepository.findAll();
        return categories;
    }

    public CategoryModel findById(Long id) throws Exception{
        LOGGER.info("Getting category by ID: {}", id);
        
		Optional<CategoryModel> category = categoryRepository.findById(id);
        return category.orElseThrow(() -> new ObjectNotFoundException("Category not found Id: " + id));
    }

    public CategoryModel createCategory(CategoryModel categoryModel) {
        CategoryModel categorySaved = categoryRepository.save(categoryModel);
        LOGGER.info("Created category {} with id = {}", categoryModel.getName(), categorySaved.getId());
        return categorySaved;
    }

    public void deleteCategory(Long id) throws Exception{
        LOGGER.info("Deleted category Id = {}", id);
        findById(id);       
        try {
            categoryRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Category has registered products");
        }
    }
}
