package com.example.outsourcing.order.entity;


import com.example.outsourcing.menu.entity.Menu;
import com.example.outsourcing.order.enums.DeliveryState;
import com.example.outsourcing.store.entity.Store;
import com.example.outsourcing.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

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
    @JoinColumn(name = "store_id")
    Store store;

    @Column(name = "order_price", nullable = false)
    Integer orderPrice;

//    @OneToOne
//    @JoinColumn(name = "review_id")
//    Review review;

    @Setter
    @Column(name = "state", nullable = false)
    @Enumerated(EnumType.STRING)
    DeliveryState state;

    @Column
    LocalTime orderTime = LocalTime.now();


    public Order(User user, Store store, Menu menu, Integer orderPrice, DeliveryState state) {
        this.user = user;
        this.store = store;
        this.menu = menu;
        this.orderPrice = orderPrice;
        this.state = state;
    }


    public LocalTime stringToLocaltime(String openTime) {
        String[] openTimeSplit = openTime.split(":");

        int hour = Integer.parseInt(openTimeSplit[0]);
        int minute = Integer.parseInt(openTimeSplit[1]);

        return LocalTime.of(hour, minute);
    }
}