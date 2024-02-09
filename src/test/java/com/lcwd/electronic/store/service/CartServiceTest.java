package com.lcwd.electronic.store.service;

import com.lcwd.electronic.store.repositories.CartItemRepository;
import com.lcwd.electronic.store.repositories.CartRepository;
import com.lcwd.electronic.store.repositories.ProductRepository;
import com.lcwd.electronic.store.repositories.UserRepo;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class CartServiceTest {
    @MockBean
    private CartRepository cartRepository;
    @MockBean
    private CartItemRepository cartItemRepository;
    @MockBean
    private UserRepo userRepo;
    @MockBean
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CartService cartService;

    @Test
    public void addItemToCartTest() {

    }

}
