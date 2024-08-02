package com.example.ecommercemini.repositories;

import com.example.ecommercemini.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
}
