package com.example.ecommercemini.dtos;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {
    private CategoryDto category;

    @NotBlank(message = "Please provide a brand name")
    private String brand;

    @NotBlank(message = "Please provide a product name")
    @Size(max = 255, message = "Product Name must be less than 255 characters")
    private String title;

    @NotBlank(message = "Please provide a short descripiton")
    @Size(max = 200, message = "Short description must be less than 200 characters")
    private String shortDescription;

    @NotBlank(message = "Please provide a descripiton")
    private String description;

    @Min(value = 0, message = "Unit Price must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Unit Price should be only upto 2 decimal places")
    private double unitPrice;

    @Min(value = 0, message = "Discounted Price must be greater than or equal to 0")
    @Digits(integer = 10, fraction = 2, message = "Discounted Price should be only upto 2 decimal places")
    private double discountedPrice;

    @Min(value = 0, message = "Quantity must be greater than or equal to 0")
    @Digits(integer = 10, fraction = 0, message = "Quantity should be a whole number")
    private int quantity;

    private String productImage;
    private boolean live;
    private boolean stock;

}
