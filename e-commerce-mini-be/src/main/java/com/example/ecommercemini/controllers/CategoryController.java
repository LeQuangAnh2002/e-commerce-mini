package com.example.ecommercemini.controllers;

import com.example.ecommercemini.dtos.ApiResponse;
import com.example.ecommercemini.dtos.CategoryDto;
import com.example.ecommercemini.dtos.PageableResponse;
import com.example.ecommercemini.dtos.ProductDto;
import com.example.ecommercemini.services.CategoryService;
import com.example.ecommercemini.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
@CrossOrigin("*")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final ProductService productService;

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable int categoryId){
        CategoryDto categoryDto = categoryService.getCategoryById(categoryId);
        return new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<PageableResponse<CategoryDto>> getAllCategories(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "categoryTitle", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        PageableResponse<CategoryDto> response = categoryService.getAllCategories(pageNumber, pageSize, sortBy,
                sortDir);
        return new ResponseEntity<PageableResponse<CategoryDto>>(response, HttpStatus.OK);
    }
    @GetMapping("/{categoryId}/products")
    public ResponseEntity<PageableResponse<ProductDto>> getProductsByCategoryId(@PathVariable int categoryId,
                                                                                @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
                                                                                @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
                                                                                @RequestParam(value = "sortBy", defaultValue = "brand", required = false) String sortBy,
                                                                                @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        PageableResponse<ProductDto> response = this.productService.getAllProductsByCategory(categoryId, pageNumber,
                pageSize, sortBy, sortDir);
        return new ResponseEntity<PageableResponse<ProductDto>>(response, HttpStatus.OK);
    }
    @PostMapping
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto categoryResposne = this.categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(categoryResposne, HttpStatus.CREATED);
    }

    // Create product with category
    @PostMapping("/{categoryId}/products")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDto> createProductWithCategory(@Valid @RequestBody ProductDto productDto,
                                                                @PathVariable int categoryId) {
        ProductDto productResponse = this.productService.createProductWithCategory(productDto, categoryId);
        return new ResponseEntity<ProductDto>(productResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,
                                                      @PathVariable("categoryId") int categoryId) {
        CategoryDto updatedCategory = this.categoryService.updateCategory(categoryDto, categoryId);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    @PutMapping("/{categoryId}/products/{productId}")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDto> updateCategoryOfProduct(@PathVariable int productId,
                                                              @PathVariable("categoryId") int categoryId) {
        ProductDto updatedProduct = this.productService.updateProductCategory(categoryId, productId);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/{categoryId}")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("categoryId") int categoryId) {
        this.categoryService.deleteCategory(categoryId);
        ApiResponse response = ApiResponse.builder().message("Category deleted successfully")
                .status(HttpStatus.OK.value()).timestamp(System.currentTimeMillis()).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

