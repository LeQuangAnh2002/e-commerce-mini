package com.example.ecommercemini.services.impl;

import com.example.ecommercemini.dtos.CreateOrderRequest;
import com.example.ecommercemini.dtos.OrderDto;
import com.example.ecommercemini.dtos.PageableResponse;
import com.example.ecommercemini.dtos.UpdateOrderRequest;
import com.example.ecommercemini.entities.*;
import com.example.ecommercemini.exception.BadApiRequestException;
import com.example.ecommercemini.exception.ResourceNotFoundException;
import com.example.ecommercemini.repositories.CartRepository;
import com.example.ecommercemini.repositories.OrderRepository;
import com.example.ecommercemini.repositories.ProductRepository;
import com.example.ecommercemini.repositories.UserRepository;
import com.example.ecommercemini.services.OrderService;
import com.example.ecommercemini.utils.Helper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    @Override
    public PageableResponse<OrderDto> getAllOrders(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Order> page = orderRepository.findAll(pageable);
        return Helper.getPageableResponse(page,OrderDto.class);
    }

    @Override
    public OrderDto getOrderById(int orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found !"));

        return modelMapper.map(order,OrderDto.class);
    }

    @Override
    public List<OrderDto> getOrdersByUser(int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found "));
        List<Order> orders = orderRepository.findByUser(user);
        List<OrderDto> orderDtos = orders.stream().map(order -> modelMapper.map(order,OrderDto.class)).collect(Collectors.toList());

        return orderDtos;
    }

    @Override
    public OrderDto createOrder(CreateOrderRequest orderRequest) {
        User user = userRepository.findById(orderRequest.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User Not Found "));

        Cart cart = cartRepository.findById(orderRequest.getCartId()).orElseThrow( () -> new ResourceNotFoundException("Cart Not Found"));
//        Lấy ra danh sách các CartItem từ đối tượng Cart.
        List<CartItem> cartItems = cart.getItems();
        if (cartItems.size() <= 0){
            throw  new BadApiRequestException("No items found in cart ");
        }
        String orderNumber = "ORD-" + System.currentTimeMillis() / 1000L + "-" + new Random().nextInt(1000);
        AtomicReference<Double> totalOrderAmount = new AtomicReference<>((double) 0);

        Order order = Order.builder()
                .orderNumber(orderNumber)
                .orderName(orderRequest.getOrderName())
                .shippingPhone(orderRequest.getShippingPhone()).orderStatus(orderRequest.getOrderStatus())
                .paymentStatus(orderRequest.getPaymentStatus()).shippingAddress(orderRequest.getShippingAddress())
                .city(orderRequest.getCity()).province(orderRequest.getProvince())
                .user(user).build();

        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cartItems){
            Product product = cartItem.getProduct();
            int requestedQuantity = cartItem.getQuantity();
            int availableQuantity = product.getQuantity();

            if (requestedQuantity > availableQuantity){
                continue;
            }
            OrderItem orderItem = OrderItem.builder().quantity(requestedQuantity).product(product)
                    .totalPrice(requestedQuantity * (product.getDiscountedPrice() != 0 ? product.getDiscountedPrice() : product.getUnitPrice()))
                    .build();

            totalOrderAmount.set(totalOrderAmount.get() + orderItem.getTotalPrice());
            product.setQuantity(availableQuantity - requestedQuantity);
            productRepository.save(product);

            orderItems.add(orderItem);

        }
        if (orderItems.isEmpty()) {
            throw new BadApiRequestException("Insufficient stock! No items available for order");
        }

        order.setOrderItems(orderItems);
        order.setOrderAmount(totalOrderAmount.get());

        // Clear cart after order is made
        cart.getItems().clear();
        this.cartRepository.save(cart);

        Order savedOrder = this.orderRepository.save(order);

        // Update stock field for products with quantity 0
        for (OrderItem orderItem : orderItems) {
            Product product = orderItem.getProduct();
            int remainingQuantity = product.getQuantity();

            if (remainingQuantity == 0) {
                product.setStock(false);
                this.productRepository.save(product);
            }
        }

        return modelMapper.map(savedOrder, OrderDto.class);
    }

    @Override
    public OrderDto updateOrder(int orderId, UpdateOrderRequest orderRequest) {
        Order order = this.orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        order.setOrderStatus(orderRequest.getOrderStatus());
        order.setPaymentStatus(orderRequest.getPaymentStatus());
        order.setOrderName(orderRequest.getOrderName());
        order.setShippingPhone(orderRequest.getShippingPhone());
        order.setShippingAddress(orderRequest.getShippingAddress());
        order.setCity(orderRequest.getCity());
        order.setProvince(orderRequest.getProvince());
        order.setDeliveredDate(orderRequest.getDeliveredDate());
        this.orderRepository.save(order);
        return modelMapper.map(order, OrderDto.class);
    }

    @Override
    public void removeOrder(int orderId) {
        Order order = this.orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        this.orderRepository.delete(order);
    }
}
