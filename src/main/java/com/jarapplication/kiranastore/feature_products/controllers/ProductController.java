package com.jarapplication.kiranastore.feature_products.controllers;

import com.jarapplication.kiranastore.constants.HttpStatusCode;
import com.jarapplication.kiranastore.feature_products.models.Product;
import com.jarapplication.kiranastore.feature_products.service.ProductServiceImp;
import com.jarapplication.kiranastore.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/v1/api/products")
public class ProductController {

    private final ProductServiceImp productService;

    @Autowired
    public ProductController(ProductServiceImp productService) {
        this.productService = productService;
    }

    /**
     * Retrive products by types by pages
     * @param category
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/type")
    public ApiResponse getProductsByType(
            @RequestParam String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<Product> result = productService.findByType(category, page, size).getContent();
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(result);
        apiResponse.setStatus(HttpStatusCode.OK);
        return apiResponse;
    }

    /**
     * Add new product
     * @param product
     * @return
     */
    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse addProduct(@RequestBody Product product) {
        Product result = productService.save(product);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(result);
        apiResponse.setStatus(HttpStatusCode.CREATED);
        return apiResponse;
    }

    /**
     * Get all products by pages
     * @param page
     * @param size
     * @return
     */
    @GetMapping
    public ApiResponse getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<Product> result = productService.getAllProducts(page, size).getContent();
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(result);
        apiResponse.setStatus(HttpStatusCode.OK);
        return apiResponse;
    }
}
