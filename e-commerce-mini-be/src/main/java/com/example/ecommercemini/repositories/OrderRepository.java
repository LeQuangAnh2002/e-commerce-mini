package com.example.ecommercemini.repositories;

import com.example.ecommercemini.entities.Order;
import com.example.ecommercemini.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Integer> {
    List<Order> findByUser(User user);
}
