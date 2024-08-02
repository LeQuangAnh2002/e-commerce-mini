package com.example.ecommercemini.services.impl;

import com.example.ecommercemini.dtos.AddItemToCartRequest;
import com.example.ecommercemini.dtos.CartDto;
import com.example.ecommercemini.entities.Cart;
import com.example.ecommercemini.entities.CartItem;
import com.example.ecommercemini.entities.Product;
import com.example.ecommercemini.entities.User;
import com.example.ecommercemini.exception.BadApiRequestException;
import com.example.ecommercemini.exception.ResourceNotFoundException;
import com.example.ecommercemini.repositories.CartItemRepository;
import com.example.ecommercemini.repositories.CartRepository;
import com.example.ecommercemini.repositories.ProductRepository;
import com.example.ecommercemini.repositories.UserRepository;
import com.example.ecommercemini.services.CartService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceIpml implements CartService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ModelMapper modelMapper;
    @Override
    public CartDto getCartByUser(int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("No user found "));
        Cart cart = this.cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("No cart found"));
        return modelMapper.map(cart, CartDto.class);
    }

    @Override
    public CartDto addItemToCart(int userId, AddItemToCartRequest request) {
        int quantity = request.getQuantity();
        int productId = request.getProductId();

        if (quantity <= 0){
            throw  new BadApiRequestException("Quantity should not be less than 1 ");
        }
        Product product = productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("No product found"));

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("No user found"));

        Cart cart = null;

        try {
            cart = cartRepository.findByUser(user).get();

        }catch (NoSuchElementException e){
            cart = new Cart();
        }

        // PERFORM CART OPERATIONS
        AtomicReference<Boolean> updated = new AtomicReference<>(false);
        List<CartItem> items = cart.getItems();
        items.stream().map( item -> {
            if (item.getProduct().getProductId() == productId){
                item.setQuantity(quantity);
                item.setTotalPrice((product.getDiscountedPrice() == 0) ? quantity * product.getUnitPrice() : quantity * product.getDiscountedPrice());
                updated.set(true);
            }
            return item;
        }).collect(Collectors.toList());

        if (!updated.get()){
            CartItem cartItem = CartItem.builder()
                    .quantity(quantity)
                    .totalPrice((product.getDiscountedPrice() == 0) ? quantity * product.getUnitPrice()
                            : quantity * product.getDiscountedPrice())
                    .product(product)
                    .cart(cart)
                    .build();
        }
        cart.setUser(user);
        Cart updatedCart = cartRepository.save(cart);
        return modelMapper.map(updatedCart, CartDto.class);
    }

    @Override
    public void removeItemFromCart(int userId, int cartItemId) {
        CartItem cartItem = this.cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart Item Not Found"));
        cartItemRepository.delete(cartItem);
    }

    @Override
    public void clearCart(int userId) {
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("No user found"));
        Cart cart = this.cartRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("No cart found"));
        cart.getItems().clear();
        cartRepository.save(cart);
    }
}
