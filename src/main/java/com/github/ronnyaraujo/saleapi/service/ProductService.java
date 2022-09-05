package com.github.ronnyaraujo.saleapi.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.github.ronnyaraujo.saleapi.exceptions.ObjectNotFoundException;
import com.github.ronnyaraujo.saleapi.model.CategoryModel;
import com.github.ronnyaraujo.saleapi.model.ProductModel;
import com.github.ronnyaraujo.saleapi.repository.ProductRepository;

@Service
public class ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryService categoryService;

    public List<ProductModel> findAll() {
        LOGGER.info("Getting all products");
        return productRepository.findAll();
    }

    public ProductModel findById(Long id) throws ObjectNotFoundException{
        LOGGER.info("Getting product by ID: {}", id);
        Optional<ProductModel> product = productRepository.findById(id);
        return product.orElseThrow(() -> new ObjectNotFoundException("Product not found Id: " + id));
    }

    public ProductModel createProduct(ProductModel product) throws Exception {
        LOGGER.info("Creating product {}", product.getName());

        for (CategoryModel cat : product.getCategories()) {
            CategoryModel category = categoryService.findById(cat.getId());
            category.getProducts().add(product);
            LOGGER.info(cat.toString());
            categoryService.createCategory(category);
        }
        
        return productRepository.save(product);
    }

    public ProductModel updateProduct(Long id, ProductModel product) throws ObjectNotFoundException{
        LOGGER.info("Update product {}, Id = {}", product.getName(), id);
        
        ProductModel productUpdate = findById(id);

        productUpdate.setName(product.getName());
        productUpdate.setDescription(product.getDescription());
        productUpdate.setPrice(product.getPrice());
        productUpdate.setImgUrl(product.getImgUrl());
        productUpdate.setCategories(product.getCategories());

        productRepository.save(productUpdate);

        return productUpdate;
    }

    public void deleteProduct(Long id) throws DataIntegrityViolationException, ObjectNotFoundException{
        LOGGER.info("Delete product Id = {}", id);
        findById(id);
        try {
            productRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Product cannot be deleted, it is associated as an Order Item");
        }
    }

    public Page<ProductModel> search(String id, Integer page, Integer linesPerPage, String orderBy, String direction)
            throws NumberFormatException, Exception {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
        CategoryModel category = categoryService.findById(Long.parseLong(id));
        return productRepository.findByCategories(category, pageRequest);
    }
}
