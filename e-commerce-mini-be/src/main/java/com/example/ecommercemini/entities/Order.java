package com.example.ecommercemini.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;

    private String orderNumber;

    @Column(nullable = false,columnDefinition = "varchar(20) default 'PENDING'")
    private String orderStatus;

    @Column(nullable = false,columnDefinition = "varchar(10) default 'NOT PAID'")
    private String paymentStatus;

    @Column(nullable = false)
    private double orderAmount;

    @Column(nullable = false)
    private String orderName;

    @Column(nullable = false)
    private String shippingAddress;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String province;

    @Column(length=10,nullable = false)
    private String shippingPhone;

    @Column(nullable = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate deliveredDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<OrderItem> orderItems ;
}
