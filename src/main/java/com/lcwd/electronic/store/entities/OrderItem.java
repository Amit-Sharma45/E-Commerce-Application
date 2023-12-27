package com.lcwd.electronic.store.entities;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderItemId;

    private Integer quantity;

    private Integer totalPrice;
    @OneToOne
    private Product product;
    @ManyToOne
    private Order order;
}
