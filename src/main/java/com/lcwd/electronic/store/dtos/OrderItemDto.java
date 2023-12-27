package com.lcwd.electronic.store.dtos;

import com.lcwd.electronic.store.entities.Order;
import com.lcwd.electronic.store.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {

    private Integer orderItemId;
    private Integer quantity;
    private Integer totalPrice;
    private ProductDto product;
//    private OrderDto order;
}
