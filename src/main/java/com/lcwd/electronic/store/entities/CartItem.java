package com.lcwd.electronic.store.entities;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cart_items")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cartItemId;
    @OneToOne
    private Product product;
    private Integer quantity;
    private Integer totalPrice;
    @ManyToOne(fetch = FetchType.LAZY)
    private Cart cart;
}
