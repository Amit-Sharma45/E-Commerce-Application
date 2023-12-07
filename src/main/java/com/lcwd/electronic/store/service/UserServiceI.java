package com.lcwd.electronic.store.service;

import com.lcwd.electronic.store.payload.PageableResponse;
import com.lcwd.electronic.store.dtos.UserDto;

import java.util.List;

public interface UserServiceI {

    UserDto createUser(UserDto userDto);

    UserDto updateUser(UserDto userDto, String userId);

    UserDto getUserById(String userId);

    PageableResponse<UserDto> getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    void deleteUser(String userId);

    UserDto getUserByEmail(String email);

    List<UserDto> searchUser(String keyword);



}
