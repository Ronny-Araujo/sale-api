package com.github.ronnyaraujo.saleapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.ronnyaraujo.saleapi.model.ProductModel;
import com.github.ronnyaraujo.saleapi.service.ProductService;

@RestController
@RequestMapping("sale-api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
	public ResponseEntity<List<ProductModel>> findAll() {
		List<ProductModel> productsList = productService.findAll();
		return ResponseEntity.ok().body(productsList);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<ProductModel> findById(@PathVariable Long id) throws Exception{
		ProductModel product = productService.findById(id);
		return ResponseEntity.ok().body(product);
	}

	@PostMapping
    public ResponseEntity<ProductModel> createProduct(@RequestBody ProductModel product) throws Exception {
        ProductModel productCreated = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(productCreated);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductModel> updateUser(@PathVariable Long id, @RequestBody ProductModel product) throws Exception{
        ProductModel productUpdate = productService.updateProduct(id, product);
        return ResponseEntity.status(HttpStatus.OK).body(productUpdate);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

     /*
    /sale-api/products/category?id=1&page=0&linesPerPage=24&orderBy=name&direction=ASC
    */
    @GetMapping(value = "category")
    public ResponseEntity<Page<ProductModel>> findPage(
                @RequestParam(value = "id", defaultValue = "") String id,
                @RequestParam(value = "page", defaultValue = "0") Integer page, 
                @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage, 
                @RequestParam(value = "orderBy", defaultValue = "name") String orderBy, 
                @RequestParam(value = "direction", defaultValue = "ASC") String direction) throws NumberFormatException, Exception {

        
        Page<ProductModel> productsList = productService.search(id, page, linesPerPage, orderBy, direction);        
        return ResponseEntity.ok().body(productsList);
    }
}
