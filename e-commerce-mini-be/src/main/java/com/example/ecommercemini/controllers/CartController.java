package com.example.ecommercemini.controllers;

import com.example.ecommercemini.dtos.AddItemToCartRequest;
import com.example.ecommercemini.dtos.ApiResponse;
import com.example.ecommercemini.dtos.CartDto;
import com.example.ecommercemini.services.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CartController {
    private final CartService cartService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<CartDto> getCartForUser(@PathVariable int userId){
        CartDto cartDto = cartService.getCartByUser(userId);

        return new ResponseEntity<CartDto>(cartDto, HttpStatus.OK);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<CartDto> addItemToCart(@PathVariable int userId,
                                                 @Valid @RequestBody AddItemToCartRequest request){
        CartDto cartDto = cartService.addItemToCart(userId,request);
        return new ResponseEntity<CartDto>(cartDto,HttpStatus.CREATED);
    }
    @DeleteMapping("/{userId}/item/{itemId}")
    public ResponseEntity<ApiResponse> removeFromCart(@PathVariable int userId, @PathVariable int itemId){
        cartService.removeItemFromCart(userId,itemId);
        ApiResponse response = ApiResponse.builder().message("Item removed successfully").status(HttpStatus.OK.value()).timestamp(System.currentTimeMillis()).build();

        return new ResponseEntity<ApiResponse>(response,HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable int userId){
        cartService.clearCart(userId);
        ApiResponse response = ApiResponse.builder().message("Cart cleared successfully").status(HttpStatus.OK.value()).timestamp(System.currentTimeMillis()).build();

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

}
