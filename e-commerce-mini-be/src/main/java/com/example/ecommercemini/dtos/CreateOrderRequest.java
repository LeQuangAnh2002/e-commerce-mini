package com.example.ecommercemini.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrderRequest {
    private int userId;
    private int cartId;
    private String orderStatus;
    private String paymentStatus;

    @NotBlank(message="Please provide a order name")
    private String orderName;

    @NotBlank(message="Please provide a valid address")
    private String shippingAddress;

    @NotBlank(message="Please provide a city")
    private String city;

    @NotBlank(message="Please provide a province")
    private String province;

    @NotBlank(message="Please provide a shipping phone number")
    @Size(min = 10, max = 10, message = "Please provide a valid 10 digit phone number")
    private String shippingPhone;
}
