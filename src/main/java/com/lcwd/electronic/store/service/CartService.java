package com.lcwd.electronic.store.service;

import com.lcwd.electronic.store.dtos.AddItemToCartRequest;
import com.lcwd.electronic.store.dtos.CartDto;

public interface CartService {
//    add item to cart:
//    cart for user is not available ,we will create the cart and then add items
//    cart available , add items to cart


    CartDto addItemToCart(String userId, AddItemToCartRequest request);

    //    remove item from cart
    void removeItemFromCart(String userId, Integer cartItem);

    void clearCart(String userId);

    CartDto getCartByUser(String userId);
}
