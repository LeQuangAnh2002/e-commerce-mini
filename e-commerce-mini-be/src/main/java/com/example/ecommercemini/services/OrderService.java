package com.example.ecommercemini.services;

import com.example.ecommercemini.dtos.CreateOrderRequest;
import com.example.ecommercemini.dtos.OrderDto;
import com.example.ecommercemini.dtos.PageableResponse;
import com.example.ecommercemini.dtos.UpdateOrderRequest;

import java.util.List;

public interface OrderService {
    PageableResponse<OrderDto> getAllOrders(int pageNumber, int pageSize, String sortBy, String sortDir);

    OrderDto getOrderById(int orderId);

    // get order for user
    List<OrderDto> getOrdersByUser(int userId);

    // create order
    OrderDto createOrder(CreateOrderRequest orderRequest);

    // update order
    OrderDto updateOrder(int orderId, UpdateOrderRequest orderRequest);

    // remove order
    void removeOrder(int orderId);
}
