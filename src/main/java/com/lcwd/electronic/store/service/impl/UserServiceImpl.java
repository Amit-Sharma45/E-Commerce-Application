package com.lcwd.electronic.store.service.impl;

import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.dtos.UserDto;
import com.lcwd.electronic.store.entities.User;
import com.lcwd.electronic.store.exceptions.ResourceNotFoundException;
import com.lcwd.electronic.store.helper.AppConstants;
import com.lcwd.electronic.store.helper.Helper;
import com.lcwd.electronic.store.repositories.UserRepo;
import com.lcwd.electronic.store.service.UserServiceI;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserServiceI {

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        logger.info("Initiating the dao call for save the user");
        String userId = UUID.randomUUID().toString();
        userDto.setUserId(userId);

        User user = this.modelMapper.map(userDto, User.class);
        User savedUser = this.userRepo.save(user);
        logger.info("Completed the dao call for save the user");
        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        logger.info("Initiating the dao call for update the user data with userId{}: ", userId);
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND));
        user.setName(userDto.getName());
//      email
        user.setPassword(userDto.getPassword());
        user.setGender(userDto.getGender());
        user.setAbout(userDto.getAbout());
        user.setImageName(userDto.getImageName());

        User updatedUser = this.userRepo.save(user);
        logger.info("Completed the dao call for update the user data with userId{}: ", userId);
        return modelMapper.map(updatedUser, UserDto.class);
    }

    @Override
    public UserDto getUserById(String userId) {
        logger.info("Initiating the dao call for get single user data with userId{}: ", userId);
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND));
        logger.info("Completed the dao call for get single user data with userId{}: ", userId);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public PageableResponse<UserDto> getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        logger.info("Initiating the dao call for get All records of Users");

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<User> page = this.userRepo.findAll(pageable);

        List<User> users = page.getContent();

//        List<UserDto> userDtos = users.stream().map(user -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
        PageableResponse<UserDto> response = Helper.getPageableResponse(page, UserDto.class);

        logger.info("Completed the dao call for get All records of Users");
        return response;
    }

    @Override
    public void deleteUser(String userId) {
        logger.info("Initiating the dao call for delete the user record by userId{}:", userId);
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND));
        logger.info("Completed the dao call for delete the user record by userId{}:", userId);
        this.userRepo.delete(user);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        logger.info("Initiating the dao call for get the user record by email");
        User user = this.userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found with given email !!"));
        logger.info("Completed the dao call for get the user record by email");
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> searchUser(String keyword) {
        logger.info("Initiating the dao call for search the user record by keyword");
        List<User> users = this.userRepo.findByNameContaining(keyword);
        List<UserDto> userDtos = users.stream().map(user -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
        logger.info("Completed the dao call for search the user record by keyword");
        return userDtos;
    }
}
