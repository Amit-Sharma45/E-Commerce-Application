package com.lcwd.electronic.store.service.impl;

import com.lcwd.electronic.store.dtos.AddItemToCartRequest;
import com.lcwd.electronic.store.dtos.CartDto;
import com.lcwd.electronic.store.entities.Cart;
import com.lcwd.electronic.store.entities.CartItem;
import com.lcwd.electronic.store.entities.Product;
import com.lcwd.electronic.store.entities.User;
import com.lcwd.electronic.store.exceptions.BadApiRequestException;
import com.lcwd.electronic.store.exceptions.ResourceNotFoundException;
import com.lcwd.electronic.store.helper.AppConstants;
import com.lcwd.electronic.store.repositories.CartItemRepository;
import com.lcwd.electronic.store.repositories.CartRepository;
import com.lcwd.electronic.store.repositories.ProductRepository;
import com.lcwd.electronic.store.repositories.UserRepo;
import com.lcwd.electronic.store.service.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CartDto addItemToCart(String userId, AddItemToCartRequest request) {
        String productId = request.getProductId();
        Integer quantity = request.getQuantity();

        if (quantity <= 0) {
            throw new BadApiRequestException("Requested quantity is not valid !!");
        }

        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.PRODUCT_NOT_FOUND));
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.USER_NOT_FOUND));

        Cart cart = null;
        try {
            cart = cartRepository.findByUser(user).get();
        } catch (NoSuchElementException e) {
            cart = new Cart();
            cart.setCartId(UUID.randomUUID().toString());
            cart.setCreatedAt(new Date());
        }
//        perform cart operations
//        If cart item already present, then update
        AtomicReference<Boolean> updated = new AtomicReference<>(false);
        List<CartItem> items = cart.getItems();
        List<CartItem> updatedItems = items.stream().map(item -> {
            if (item.getProduct().getProductId().equals(productId)) {
//                items already present
                item.setQuantity(quantity);
                item.setTotalPrice(quantity * product.getPrice());
                updated.set(true);
            }
            return item;
        }).collect(Collectors.toList());
        cart.setItems(updatedItems);

//  create items
        if (!updated.get()) {
            CartItem cartItem = CartItem.builder()
                    .quantity(quantity)
                    .totalPrice(quantity * product.getPrice())
                    .cart(cart)
                    .product(product)
                    .build();
            cart.getItems().add(cartItem);
        }
        cart.setUser(user);

        Cart updatedCart = cartRepository.save(cart);
        return modelMapper.map(updatedCart, CartDto.class);
    }

    @Override
    public void removeItemFromCart(String userId, Integer cartItem) {
        CartItem cartItem1 = cartItemRepository.findById(cartItem).orElseThrow(() -> new ResourceNotFoundException("Cart Item not found !!"));
        cartItemRepository.delete(cartItem1);
    }

    @Override
    public void clearCart(String userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.USER_NOT_FOUND));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("Cart of given User not found !!"));
        cart.getItems().clear();
        cartRepository.save(cart);
    }

    @Override
    public CartDto getCartByUser(String userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.USER_NOT_FOUND));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("Cart of given User not found !!"));
        return modelMapper.map(cart, CartDto.class);
    }
}
