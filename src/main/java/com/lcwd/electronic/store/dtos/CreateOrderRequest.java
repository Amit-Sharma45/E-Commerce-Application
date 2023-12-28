package com.lcwd.electronic.store.dtos;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrderRequest {
    @NotBlank(message = "Cart id is Required !!")
    private String cartId;
    private String userId;
    private String orderStatus = "Pending";
    private String paymentStatus = "NotPaid";
    @NotBlank(message = "billing Address is required !!")
    private String billingAddress;
    private String billingPhone;
    @NotBlank(message = "billing name is required !!")
    private String billingName;
}
