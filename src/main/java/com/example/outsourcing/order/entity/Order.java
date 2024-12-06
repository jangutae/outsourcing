package com.example.outsourcing.order.entity;


import com.example.outsourcing.common.entity.BaseEntity;
import com.example.outsourcing.menu.entity.Menu;
import com.example.outsourcing.order.enums.DeliveryState;
import com.example.outsourcing.review.entity.Review;
import com.example.outsourcing.store.entity.Store;
import com.example.outsourcing.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.mapping.ToOne;

import java.time.LocalTime;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    Menu menu;

    @ManyToOne
    @JoinColumn(name = "store_id")
    Store store;

    @Column(name = "order_price", nullable = false)
    Integer orderPrice;

    @Column(name = "order_time")
    LocalTime orderTime = LocalTime.now();


//    @OneToOne
//    @JoinColumn(name = "review_id")
//    Review review;


    @Setter
    @Column(name = "state", nullable = false)
    @Enumerated(EnumType.STRING)
    DeliveryState state;

    public Order(User user, Store store, Menu menu, Integer orderPrice, DeliveryState state) {
        this.user = user;
        this.store = store;
        this.menu = menu;
        this.orderPrice = orderPrice;
        this.state = state;
    }


    public LocalTime stringToLocaltime(String time) {
        String[] openTimeSplit = time.split(":");

        int hour = Integer.parseInt(openTimeSplit[0]);
        int minute = Integer.parseInt(openTimeSplit[1]);

        return LocalTime.of(hour, minute);
    }
}