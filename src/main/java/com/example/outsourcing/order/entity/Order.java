package com.example.outsourcing.order.entity;


import com.example.outsourcing.menu.entity.Menu;
import com.example.outsourcing.order.DeliveryState;
import com.example.outsourcing.review.entity.Review;
import com.example.outsourcing.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @OneToOne
    @JoinColumn(name = "menu_id")
    Menu menu;

    @OneToOne
    @JoinColumn(name = "review_id")
    Review review;

    @Setter
    @Column(name = "state", nullable = false)
    @Enumerated(EnumType.STRING)
    DeliveryState state;


}