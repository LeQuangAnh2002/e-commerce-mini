package com.example.ecommercemini.services;

import com.example.ecommercemini.dtos.AddItemToCartRequest;
import com.example.ecommercemini.dtos.CartDto;

public interface CartService {

    CartDto getCartByUser(int userId);

    CartDto addItemToCart(int userId, AddItemToCartRequest request);

    void removeItemFromCart(int userId, int cartItemId);

    void clearCart(int userId);

}
