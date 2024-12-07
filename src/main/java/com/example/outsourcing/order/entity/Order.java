package com.example.outsourcing.order.entity;


import com.example.outsourcing.common.entity.BaseEntity;
import com.example.outsourcing.menu.entity.Menu;
import com.example.outsourcing.review.entity.Review;
import com.example.outsourcing.store.entity.Store;
import com.example.outsourcing.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;


    @Column(name = "order_time")
    private LocalTime orderTime = LocalTime.now();


    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviewLiST = new ArrayList<>();


    @Setter
    @Column(name = "state", nullable = false)
    @Enumerated(EnumType.STRING)
    private DeliveryState state;

    public Order(User user, Store store, Menu menu,  DeliveryState state) {
        this.user = user;
        this.store = store;
        this.menu = menu;
        this.state = state;
    }

    public enum DeliveryState {
        ORDER_COMPLETE,
        ORDER_ACCEPT,
        STILL_COOKING,
        COOK_DONE,
        STILL_DELIVERING,
        DELIVERY_COMPLETE
    }

    public boolean isOverMinPrice(Menu menu) {
        return this.menu.getPrice() > menu.getStore().getMinPrice();
    }

    public boolean isNotOpened(Menu menu) {
        return this.orderTime.isBefore(menu.getStore().getOpenTime());
    }

    public boolean isAlreadyClosed(Menu menu) {
        return this.orderTime.isAfter(menu.getStore().getCloseTime());
    }
}