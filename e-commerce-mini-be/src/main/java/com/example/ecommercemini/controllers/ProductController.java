package com.example.ecommercemini.controllers;

import com.example.ecommercemini.dtos.ApiResponse;
import com.example.ecommercemini.dtos.PageableResponse;
import com.example.ecommercemini.dtos.ProductDto;
import com.example.ecommercemini.entities.Product;
import com.example.ecommercemini.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ProductController {

    private final ProductService productService;


    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable int productId){
        ProductDto productDto = productService.getProductById(productId);
        return new ResponseEntity<ProductDto>(productDto, HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<PageableResponse<ProductDto>> getAllProducts(
            @RequestParam(value = "pageNumber", defaultValue = "0" , required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10" , required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title" , required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc" , required = false) String sortDir

    ){
        PageableResponse<ProductDto> response = productService.getAllProducts(pageNumber,pageSize,sortBy,sortDir);
        return new ResponseEntity<PageableResponse<ProductDto>>(response, HttpStatus.OK);
    }
    @GetMapping("/live")
    public ResponseEntity<PageableResponse<ProductDto>> getAllProductsLive(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        PageableResponse<ProductDto> response = this.productService.getAllProductsLive(pageNumber, pageSize, sortBy,
                sortDir);
        return new ResponseEntity<PageableResponse<ProductDto>>(response, HttpStatus.OK);
    }
    @GetMapping("/search/{title}")
    public ResponseEntity<PageableResponse<ProductDto>> getSearchProductByTitle(
      @PathVariable String title,
      @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
      @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
      @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
      @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ){
        PageableResponse<ProductDto> response = productService.searchProductByTitle(title, pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<PageableResponse<ProductDto>>(response,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto){
        ProductDto responseProductDto = productService.createProduct(productDto);
        return new ResponseEntity<>(responseProductDto,HttpStatus.CREATED);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@Valid @RequestBody ProductDto productDto, @PathVariable int productId){
        ProductDto updateProductDto = productService.updateProduct(productDto,productId);
        return new ResponseEntity<>(updateProductDto,HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable int productId) {
        this.productService.deleteProduct(productId);
        ApiResponse response = ApiResponse.builder().message("Product deleted successfully")
                .status(HttpStatus.OK.value()).timestamp(System.currentTimeMillis()).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
