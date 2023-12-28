package com.lcwd.electronic.store.controllers;

import com.lcwd.electronic.store.dtos.CreateOrderRequest;
import com.lcwd.electronic.store.dtos.OrderDto;
import com.lcwd.electronic.store.helper.AppConstants;
import com.lcwd.electronic.store.helper.UrlConstants;
import com.lcwd.electronic.store.payload.ApiResponse;
import com.lcwd.electronic.store.payload.PageableResponse;
import com.lcwd.electronic.store.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(UrlConstants.ORDER_BASE_URL)
@Slf4j
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * @param request
     * @return OrderDto
     * @author AMIT KUMAR
     * @apiNote for create the order
     * @since 1.0
     */
    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        log.info("Entering the Request to create the order");
        OrderDto order = orderService.createOrder(request);
        log.info("Completed the Request to create the order");
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    /**
     * @param orderId
     * @return ApiResponse
     * @author AMIT KUMAR
     * @apiNote to remove the order
     * @since 1.0
     */
    @DeleteMapping("/{orderId}")
    public ResponseEntity<ApiResponse> removeOrder(@PathVariable String orderId) {
        log.info("Entering the Request to remove the order with orderId : {}", orderId);
        orderService.removeOrder(orderId);
        ApiResponse response = ApiResponse.builder().status(HttpStatus.OK)
                .message(AppConstants.ORDER_REMOVED)
                .success(true)
                .build();
        log.info("Completed the Request to remove the order with orderId : {}", orderId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * @param userId
     * @return List of OrderDto
     * @author AMIT KUMAR
     * @apiNote to get orders of user
     * @since 1.0
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDto>> getOrdersOfUser(@PathVariable String userId) {
        log.info("Entering the Request to get the orders of user with userId : {}", userId);
        List<OrderDto> orders = orderService.getOrdersOfUser(userId);
        log.info("Completed the Request to get the orders of user with userId : {}", userId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    /**
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return PageableResponse of OrderDto
     * @author AMIT KUMAR
     * @apiNote To get the orders
     * @since 1.0
     */
    @GetMapping
    public ResponseEntity<PageableResponse<OrderDto>> getOrders(
            @RequestParam(defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(defaultValue = AppConstants.ORDER_SORT_BY, required = false) String sortBy,
            @RequestParam(defaultValue = AppConstants.SORT_DIR, required = false) String sortDir
    ) {
        log.info("Entering the Request to get the orders");
        PageableResponse<OrderDto> orders = orderService.getOrders(pageNumber, pageSize, sortBy, sortDir);
        log.info("Completed the Request to get the orders");
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}
