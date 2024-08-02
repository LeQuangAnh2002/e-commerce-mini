package com.example.ecommercemini.repositories;

import com.example.ecommercemini.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByEmail(String email);

    // This method finds users whose first name contains the specified keyword
    Page<User> findByFirstNameContaining(String keyword, Pageable pageable);

    User findUserByEmailIgnoreCase(String email);
}
