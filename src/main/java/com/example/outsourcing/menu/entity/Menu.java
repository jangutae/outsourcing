package com.example.outsourcing.menu.entity;

import com.example.outsourcing.menu.enums.StateType;
import com.example.outsourcing.store.entity.Store;
import com.example.outsourcing.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "menus")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @Column(name = "menu_name", nullable = false)
    private String menuName;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Setter
    @Column(name = "state", nullable = false)
    @Enumerated(EnumType.STRING)
    private StateType state;

    public Menu(User user, Store store, String menuName, Integer price, StateType state) {
        this.user = user;
        this.store = store;
        this.menuName = menuName;
        this.price = price;
        this.state = state;
    }

    public void update(String menuName, Integer price) {
        this.menuName = menuName;
        this.price = price;
    }
}
