package com.example.ecommercemini.repositories;

import com.example.ecommercemini.entities.Cart;
import com.example.ecommercemini.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,Integer> {
    Optional<Cart> findByUser(User user);
}
