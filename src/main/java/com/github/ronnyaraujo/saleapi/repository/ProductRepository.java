package com.github.ronnyaraujo.saleapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.github.ronnyaraujo.saleapi.model.CategoryModel;
import com.github.ronnyaraujo.saleapi.model.ProductModel;

@Repository
public interface ProductRepository extends JpaRepository<ProductModel, Long> {    

    @Transactional(readOnly = true)
    Page<ProductModel> findByCategories(CategoryModel categorias, Pageable pageRequest);
}
