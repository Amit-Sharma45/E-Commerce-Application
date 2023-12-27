package com.lcwd.electronic.store.controllers;

import com.lcwd.electronic.store.dtos.AddItemToCartRequest;
import com.lcwd.electronic.store.dtos.CartDto;
import com.lcwd.electronic.store.helper.AppConstants;
import com.lcwd.electronic.store.helper.UrlConstants;
import com.lcwd.electronic.store.payload.ApiResponse;
import com.lcwd.electronic.store.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(UrlConstants.CART_BASE_URL)
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping("/{userId}")
    public ResponseEntity<CartDto> addItemToCart(@PathVariable String userId, @RequestBody AddItemToCartRequest request) {
        CartDto cartDto = cartService.addItemToCart(userId, request);
        return new ResponseEntity<>(cartDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{userId}/items/{itemId}")
    public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable String userId, @PathVariable Integer itemId) {
        cartService.removeItemFromCart(userId, itemId);
        ApiResponse apiResponse = ApiResponse.builder().message(AppConstants.ITEM_DELETED)
                .success(true)
                .status(HttpStatus.OK).build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable String userId) {
        cartService.clearCart(userId);
        ApiResponse apiResponse = ApiResponse.builder().message(AppConstants.CART_CLEARED)
                .success(true)
                .status(HttpStatus.OK).build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CartDto> getCart(@PathVariable String userId) {
        CartDto cartDto = cartService.getCartByUser(userId);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }
}
