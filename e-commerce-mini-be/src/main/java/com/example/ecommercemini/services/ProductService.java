package com.example.ecommercemini.services;

import com.example.ecommercemini.dtos.PageableResponse;
import com.example.ecommercemini.dtos.ProductDto;

public interface ProductService {
    PageableResponse<ProductDto> searchProductByTitle(String title,int pageNumber, int pageSize,String sortBy,String sortDir);

    ProductDto getProductById(int productId);

    PageableResponse<ProductDto> getAllProducts(int pageNumber, int pageSize, String sortBy, String sortDir);

    PageableResponse<ProductDto> getAllProductsLive(int pageNumber, int pageSize, String sortBy, String sortDir);

    PageableResponse<ProductDto> getAllProductsByCategory(int categoryId,int pageNumber, int pageSize, String sortBy, String sortDir);

    ProductDto createProduct(ProductDto productDto);

    ProductDto createProductWithCategory(ProductDto productDto, int categoryId);

    ProductDto updateProduct(ProductDto productDto, int productId);

    // Update category of product
    ProductDto updateProductCategory(int categoryId,int productId);

    // Deletes a product with the specified product Id
    void deleteProduct(int productId);



}
