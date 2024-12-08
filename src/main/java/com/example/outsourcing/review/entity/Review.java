package com.example.outsourcing.review.entity;

import com.example.outsourcing.common.entity.BaseEntity;
import com.example.outsourcing.order.entity.Order;
import com.example.outsourcing.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(nullable = false)
    private Integer star;
    @Column(nullable = false)
    private String contents;

    private Long userId;
    private Long storeId;

    @ManyToOne
    @JoinColumn(name = "order_id",unique = true)
    private Order order;

    public Review(Integer star, String contents, Order order){
        this.star = star;
        this.contents = contents;
        this.order = order;
        this.userId = order.getUser().getId();
        this.storeId = order.getStore().getId();
    }

}
