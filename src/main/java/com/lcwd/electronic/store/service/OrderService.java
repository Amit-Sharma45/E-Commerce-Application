package com.lcwd.electronic.store.service;

import com.lcwd.electronic.store.dtos.CreateOrderRequest;
import com.lcwd.electronic.store.dtos.OrderDto;
import com.lcwd.electronic.store.payload.PageableResponse;

import java.util.List;

public interface OrderService {

    //    create order
    OrderDto createOrder(CreateOrderRequest orderDto);

    //    Remove order
    void removeOrder(String orderId);

    //    Get orders of User
    List<OrderDto> getOrdersOfUser(String userId);

    //    get orders
    PageableResponse<OrderDto> getOrders(int pageNumber, int pageSize, String sortBy, String sortDir);
}
