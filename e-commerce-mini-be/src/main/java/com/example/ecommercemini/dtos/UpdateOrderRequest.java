package com.example.ecommercemini.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateOrderRequest {
    @NotBlank(message = "Please provide a order name")
    private String orderName;

    @NotBlank(message = "Please provide a valid address")
    private String shippingAddress;

    @NotBlank(message = "Please provide a city")
    private String city;

    @NotBlank(message = "Please provide a province")
    private String province;

    @NotBlank(message = "Please provide a shipping phone number")
    @Size(min = 10, max = 10, message = "Please provide a valid 10 digit phone number")
    private String shippingPhone;

    @NotBlank(message = "Please provide a order status")
    private String orderStatus;

    @NotBlank(message = "Please provide a payment status")
    private String paymentStatus;


    private LocalDate deliveredDate;
}
