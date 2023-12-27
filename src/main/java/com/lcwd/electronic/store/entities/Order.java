package com.lcwd.electronic.store.entities;

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
@Entity
@Table(name = "orders")
public class Order {

    @Id
    private String orderId;
    //PENDING, DISPATCHED, DELIVERED
    private String orderStatus;
    //NOT-PAID, PAID
// boolean - false=NOTPAID // true=PAID
    private String paymentStatus;
    private Integer orderAmount;
    private String billingAddress;
    private String billingPhone;
    private String billingName;
    private Date orderedDate;
    private Date deliveredDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<OrderItem> items = new ArrayList<>();
}
