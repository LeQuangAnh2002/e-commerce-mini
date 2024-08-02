package com.example.ecommercemini.services.impl;

import com.example.ecommercemini.dtos.PageableResponse;
import com.example.ecommercemini.dtos.ProductDto;
import com.example.ecommercemini.entities.Category;
import com.example.ecommercemini.entities.Product;
import com.example.ecommercemini.exception.ResourceNotFoundException;
import com.example.ecommercemini.repositories.CategoryRepository;
import com.example.ecommercemini.repositories.ProductRepository;
import com.example.ecommercemini.services.ProductService;
import com.example.ecommercemini.utils.Helper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public PageableResponse<ProductDto> searchProductByTitle(String title, int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) :
                (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = this.productRepository.findByTitleContaining(title, pageable);
        return Helper.getPageableResponse(page, ProductDto.class);
    }

    @Override
    public ProductDto getProductById(int productId) {
        Product product = this.productRepository.findById(productId).orElse(null);
        return modelMapper.map(product, ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAllProducts(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) :
                (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = this.productRepository.findAll(pageable);
        return Helper.getPageableResponse(page, ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAllProductsLive(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) :
                (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = this.productRepository.findByLiveTrue(pageable);
        return Helper.getPageableResponse(page, ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAllProductsByCategory(int categoryId, int pageNumber, int pageSize, String sortBy, String sortDir) {
        Category category =  categoryRepository.findById(categoryId).orElse(null);
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) :
                (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = this.productRepository.findByCategoryAndLiveTrue(category, pageable);
        return Helper.getPageableResponse(page,ProductDto.class);
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Product product = modelMapper.map(productDto,Product.class);
        Product saveProduct = productRepository.save(product);
        return modelMapper.map(saveProduct,ProductDto.class);
    }

    @Override
    public ProductDto createProductWithCategory(ProductDto productDto, int categoryId) {
        Category category =  categoryRepository.findById(categoryId).orElse(null);

        Product product = modelMapper.map(productDto, Product.class);
        product.setCategory(category);
        Product savedProduct = this.productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDto.class);
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, int productId) {
        Product product = this.productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("No product found!"));
        product.setProductId(productId);
        product.setBrand(productDto.getBrand());
        product.setTitle(productDto.getTitle());
        product.setShortDescription(productDto.getShortDescription());
        product.setDescription(productDto.getDescription());
        product.setUnitPrice(productDto.getUnitPrice());
        product.setDiscountedPrice(productDto.getDiscountedPrice());
        product.setQuantity(productDto.getQuantity());
        if(productDto.getProductImage()!=null) {
            product.setProductImage(productDto.getProductImage());
        }
        product.setLive(productDto.isLive());
        product.setStock(productDto.isStock());

        Product savedProduct = this.productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDto.class);
    }

    @Override
    public ProductDto updateProductCategory(int categoryId, int productId) {
        Product product = this.productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("No product found!"));
        // fetch category
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Product of given category found"));
        product.setCategory(category);

        Product savedProduct = this.productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDto.class);
    }

    @Override
    public void deleteProduct(int productId) {
        Product product = this.productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("No product found!"));

        // delete product image
        this.productRepository.deleteById(productId);
    }
}
