package com.example.ecommercemini.dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemDto {
    @NotNull(message = "Product cannot be null")
    private ProductDto product;

    @Min(value = 1, message = "Quantity must be positive")
    private int quantity;

    @DecimalMin(value = "0.0", inclusive = false, message = "Total price must be positive")
    private double totalPrice;
}
