package com.example.ecommercemini.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private String orderNumber;
    private String orderStatus;
    private String paymentStatus;
    private double orderAmount;

    @NotBlank(message="Please provide a order name")
    private String orderName;

    @NotBlank(message="Please provide a shipping address")
    private String shippingAddress;

    @NotBlank(message="Please provide a order city")
    private String city;

    @NotBlank(message="Please provide a province")
    private String province;

    @NotBlank(message="Please provide a shipping phone number")
    @Size(min = 10, max = 10, message = "Please provide a valid 10 digit phone number")
    private String shippingPhone;

    private List<OrderItemDto> orderItems;

    private UserDto user;
}
