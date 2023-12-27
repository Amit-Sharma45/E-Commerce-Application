package com.lcwd.electronic.store.dtos;

import com.lcwd.electronic.store.entities.OrderItem;
import com.lcwd.electronic.store.entities.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {
    private String orderId;

    private String orderStatus = "Pending";
    private String paymentStatus = "NotPaid";
    private Integer orderAmount;
    private String billingAddress;
    private String billingPhone;
    private String billingName;
    private Date orderedDate = new Date();
    private Date deliveredDate;
//    private UserDto user;

    private List<OrderItemDto> items = new ArrayList<>();
}
