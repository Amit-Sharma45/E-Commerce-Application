package com.lcwd.electronic.store.dtos;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemDto {
    private Integer cartItemId;
    private ProductDto product;
    private Integer quantity;
    private Integer totalPrice;
}
