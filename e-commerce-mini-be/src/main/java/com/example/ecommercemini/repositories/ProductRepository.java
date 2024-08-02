package com.example.ecommercemini.repositories;

import com.example.ecommercemini.entities.Category;
import com.example.ecommercemini.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Integer> {
    // search by title
    Page<Product> findByTitleContaining(String title, Pageable pageable);

    // Products which are live
    Page<Product> findByLiveTrue(Pageable pageable);

    // Find By Category
    Page<Product> findByCategoryAndLiveTrue(Category category, Pageable pageable);
}
