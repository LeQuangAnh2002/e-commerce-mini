package com.example.ecommercemini.controllers;

import com.example.ecommercemini.dtos.*;
import com.example.ecommercemini.services.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@CrossOrigin("*")
public class OrderController {
    private final OrderService orderService;
    @PostMapping()
    public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        OrderDto orderDto = this.orderService.createOrder(request);
        return new ResponseEntity<OrderDto>(orderDto, HttpStatus.CREATED);
    }

    // update order
    @PutMapping("/{orderId}")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable int orderId,
                                                @Valid @RequestBody UpdateOrderRequest request) {
        System.out.println(request.getDeliveredDate());
        OrderDto orderDto = this.orderService.updateOrder(orderId, request);
        return new ResponseEntity<OrderDto>(orderDto, HttpStatus.OK);
    }

    // remove order
    @DeleteMapping("/{orderId}")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> removeOrder(@PathVariable int orderId) {
        this.orderService.removeOrder(orderId);
        ApiResponse response = ApiResponse.builder().message("Order removed successfully").status(HttpStatus.OK.value())
                .timestamp(System.currentTimeMillis()).build();
        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
    }

    // get all orders

    @GetMapping()
    public ResponseEntity<PageableResponse<OrderDto>> getAllOrders(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "createdAt", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {

        PageableResponse<OrderDto> orders = this.orderService.getAllOrders(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<PageableResponse<OrderDto>>(orders, HttpStatus.OK);
    }

    // get order by order id
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable int orderId) {
        OrderDto orderDto = this.orderService.getOrderById(orderId);
        return new ResponseEntity<OrderDto>(orderDto, HttpStatus.OK);
    }

    // get order by user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDto>> getOrdersByUser(@PathVariable int userId) {
        List<OrderDto> orders = this.orderService.getOrdersByUser(userId);
        return new ResponseEntity<List<OrderDto>>(orders, HttpStatus.OK);
    }
}
