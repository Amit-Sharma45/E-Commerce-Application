package com.lcwd.electronic.store.controllers;

import com.lcwd.electronic.store.dtos.AddItemToCartRequest;
import com.lcwd.electronic.store.dtos.CartDto;
import com.lcwd.electronic.store.helper.AppConstants;
import com.lcwd.electronic.store.helper.UrlConstants;
import com.lcwd.electronic.store.payload.ApiResponse;
import com.lcwd.electronic.store.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(UrlConstants.CART_BASE_URL)
@Slf4j
public class CartController {
    @Autowired
    private CartService cartService;

    /**
     * @param userId
     * @param request
     * @return CartDto
     * @author AMIT KUMAR
     * @apiNote For Add Item to Cart
     * @since 1.0
     */
    @PostMapping("/{userId}")
    public ResponseEntity<CartDto> addItemToCart(@PathVariable String userId, @RequestBody AddItemToCartRequest request) {
        log.info("Entering the Request to add Item to Cart with userId : {}", userId);
        CartDto cartDto = cartService.addItemToCart(userId, request);
        log.info("Completed the Request to add Item to Cart with userId : {}", userId);
        return new ResponseEntity<>(cartDto, HttpStatus.CREATED);
    }

    /**
     * @param userId
     * @param itemId
     * @return ApiResponse
     * @author AMIT KUMAR
     * @apiNote for Remove Item from Cart
     * @since 1.0
     */
    @DeleteMapping("/{userId}/items/{itemId}")
    public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable String userId, @PathVariable Integer itemId) {
        log.info("Entering the Request to remove Item from Cart with userId : {}", userId);
        cartService.removeItemFromCart(userId, itemId);
        ApiResponse apiResponse = ApiResponse.builder().message(AppConstants.ITEM_DELETED)
                .success(true)
                .status(HttpStatus.OK).build();
        log.info("Completed the Request to remove Item from Cart with userId : {}", userId);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    /**
     * @param userId
     * @return Api Response
     * @author AMIT KUMAR
     * @apiNote for clear the cart
     * @since 1.0
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable String userId) {
        log.info("Entering the Request to clear the Cart with userId : {}", userId);
        cartService.clearCart(userId);
        ApiResponse apiResponse = ApiResponse.builder().message(AppConstants.CART_CLEARED)
                .success(true)
                .status(HttpStatus.OK).build();
        log.info("Completed the Request to clear the Cart with userId : {}", userId);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    /**
     * @param userId
     * @return CartDto
     * @author AMIT KUMAR
     * @apiNote for get the Cart
     * @since 1.0
     */
    @GetMapping("/{userId}")
    public ResponseEntity<CartDto> getCart(@PathVariable String userId) {
        log.info("Entering the Request to get the Cart with userId : {}", userId);
        CartDto cartDto = cartService.getCartByUser(userId);
        log.info("Completed the Request to get the Cart with userId : {}", userId);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }
}
