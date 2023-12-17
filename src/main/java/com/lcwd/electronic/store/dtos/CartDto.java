package com.lcwd.electronic.store.dtos;


import com.lcwd.electronic.store.entities.User;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDto {
    private String cartId;
    private Date createdAt;
    private User user;

}
