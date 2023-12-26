package com.lcwd.electronic.store.dtos;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddItemToCartRequest {
    private String productId;

    private Integer quantity;
}
