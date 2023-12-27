package com.lcwd.electronic.store.dtos;

import com.lcwd.electronic.store.entities.Order;
import com.lcwd.electronic.store.entities.Product;
import lombok.*;

import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDto {

    private Integer orderItemId;
    private Integer quantity;
    private Integer totalPrice;
    private ProductDto product;
//    private OrderDto order;
}
