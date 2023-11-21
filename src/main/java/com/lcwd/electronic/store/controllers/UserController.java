package com.lcwd.electronic.store.controllers;

import com.lcwd.electronic.store.dtos.ApiResponse;
import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.dtos.UserDto;
import com.lcwd.electronic.store.helper.AppConstants;
import com.lcwd.electronic.store.service.UserServiceI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    @Autowired
    private UserServiceI userServiceI;

    /**
     * @param userDto
     * @return UserDto
     * @author AMIT
     * @apiNote To save or create new User
     * @since 1.0
     */
    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        log.info("Entering the Request for Save user record");
        UserDto userDto1 = this.userServiceI.createUser(userDto);
        log.info("Completed the Request for Save user record");
        return new ResponseEntity<>(userDto1, HttpStatus.CREATED);
    }

    /**
     * @param userDto
     * @param userId
     * @return UserDto
     * @author AMIT
     * @apiNote To update the user
     * @since 1.0
     */
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable String userId) {
        log.info("Entering the Request for update the user record with userId{}: ", userId);
        UserDto userDto1 = this.userServiceI.updateUser(userDto, userId);
        log.info("Completed the Request for update the user record with userId{}: ", userId);
        return new ResponseEntity<UserDto>(userDto1, HttpStatus.OK);
    }

    /**
     * @param userId
     * @return UserDto
     * @author AMIT
     * @apiNote To Get the User by UserId
     * @since 1.0
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String userId) {
        log.info("Entering the Request for get user record by userId{}:", userId);
        UserDto user = this.userServiceI.getUserById(userId);
        log.info("Completed the Request for get user record by userId{}:", userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * @return UserDto
     * @author AMIT
     * @apiNote To Get All Users
     * @since 1.0
     */
    @GetMapping
    public ResponseEntity<PageableResponse<UserDto>> getAllUsers(
            @RequestParam(defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(defaultValue = AppConstants.SORT_DIR, required = false) String sortDir
    ) {
        log.info("Entering the Request for get All records of Users");
        PageableResponse<UserDto> allUsers = this.userServiceI.getAllUsers(pageNumber, pageSize, sortBy, sortDir);
        log.info("Completed the Request for get All records of Users");
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    /**
     * @param userId
     * @return ApiResponse
     * @author AMIT
     * @apiNote To delete User by UserId
     * @since 1.0
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable String userId) {
        log.info("Entering the Request for delete the user record by userId{}: ", userId);
        this.userServiceI.deleteUser(userId);
        ApiResponse response = ApiResponse.builder()
                .message(AppConstants.DELETED)
                .success(true)
                .build();
        log.info("Completed the Request for delete the user record by userId{}:", userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * @param email
     * @return
     * @author AMIT
     * @apiNote getUser By email
     * @since 1.0
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        log.info("Entering the Request for get the user by email");
        UserDto user = this.userServiceI.getUserByEmail(email);
        log.info("Competed the Request for get the user by email");
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * @param keyword
     * @return
     * @author AMIT
     * @apiNote search user by keyword
     * @since 1.0
     */
    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keyword) {
        log.info("Entering the Request for search the user by keyword");
        List<UserDto> users = this.userServiceI.searchUser(keyword);
        log.info("Competed the Request for search the user by keyword");
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
