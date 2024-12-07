package com.example.outsourcing.review.entity;

import com.example.outsourcing.common.entity.BaseEntity;
import com.example.outsourcing.order.entity.Order;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "contents", nullable = false)
    private String contents;

    @Column(name = "star_rate", nullable = false)
    private Double star;

    @ManyToOne
    @JoinColumn(name = "order_id",unique = true)
    private Order order;

    @Column(name = "created_at", updatable = false, nullable = false)
    LocalDateTime createdAt;



    public Review(Double star, String contents) {
        this.star = star;
        this.contents = contents;
    }
}
