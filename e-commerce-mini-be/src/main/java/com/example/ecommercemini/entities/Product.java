package com.example.ecommercemini.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String title;

    @Column(length = 150, nullable = false)
    private String shortDescription;

    @Column(length = 1000, nullable = false)
    private String description;

    @Column(nullable = false)
    private double unitPrice;

    @Column(columnDefinition = "double default 0")
    private double discountedPrice;

    @Column(nullable = false)
    private int quantity;

    private String productImage;

    @Column(nullable = false)
    private boolean live;

    @Column(nullable = false)
    private boolean stock;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cateogryId")
    private Category category;

}
