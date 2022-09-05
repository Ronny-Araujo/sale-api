package com.github.ronnyaraujo.saleapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.ronnyaraujo.saleapi.model.CategoryModel;
import com.github.ronnyaraujo.saleapi.service.CategoryService;

@RestController
@RequestMapping("sale-api/category")
public class CategoryController {

    @Autowired 
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryModel>> getCategories() {
        List<CategoryModel> categories = categoryService.getCategories();
        return ResponseEntity.ok().body(categories);
    }

    @GetMapping(value = "/{id}")
	public ResponseEntity<CategoryModel> findById(@PathVariable Long id) throws Exception{
		CategoryModel category = categoryService.findById(id);
		return ResponseEntity.ok().body(category);
	} 

    @PostMapping
    public ResponseEntity<CategoryModel> createCategory(@RequestBody CategoryModel categoryModel) {
        CategoryModel categoryCreated = categoryService.createCategory(categoryModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryCreated);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) throws Exception {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
