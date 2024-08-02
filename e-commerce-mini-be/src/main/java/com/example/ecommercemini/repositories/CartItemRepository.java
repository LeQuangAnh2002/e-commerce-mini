package com.example.ecommercemini.repositories;

import com.example.ecommercemini.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem,Integer> {
}
