package com.lcwd.electronic.store.controllers;

import com.lcwd.electronic.store.helper.UrlConstants;
import com.lcwd.electronic.store.payload.ApiResponse;
import com.lcwd.electronic.store.payload.ImageResponse;
import com.lcwd.electronic.store.payload.PageableResponse;
import com.lcwd.electronic.store.dtos.UserDto;
import com.lcwd.electronic.store.helper.AppConstants;
import com.lcwd.electronic.store.service.ImageService;
import com.lcwd.electronic.store.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping(UrlConstants.USER_BASE_URL)
@Slf4j
public class UserController {
    @Autowired
    private UserService userServiceI;
    @Autowired
    private ImageService imageService;
    @Value("${user.profile.image.path}")
    private String imageUploadPath;

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
                .message(AppConstants.USER_DELETED)
                .success(true)
                .status(HttpStatus.OK)
                .build();
        log.info("Completed the Request for delete the user record by userId{}:", userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * @param email
     * @return UserDto
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
     * @return List of UserDto
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

    /**
     * @param image
     * @param userId
     * @return ImageResponse
     * @throws IOException
     * @apiNote To upload user Image
     * @author AMIT
     * @since 1.0
     */
    @PostMapping("/image/{userId}")
    public ResponseEntity<ImageResponse> uploadUserImage(@RequestParam("userImage") MultipartFile image, @PathVariable String userId) throws IOException {
        log.info("Entering the Request for upload user Image for userId{}: ", userId);
        String imageName = imageService.uploadImage(image, imageUploadPath);
        UserDto user = userServiceI.getUserById(userId);
        user.setImageName(imageName);
        UserDto userDto = userServiceI.updateUser(user, userId);

        ImageResponse imageResponse = ImageResponse.builder().imageName(imageName).success(true).message("image uploaded successfully !!").status(HttpStatus.CREATED).build();
        log.info("Completed the Request for upload user Image for userId{}: ", userId);
        return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);
    }

    /**
     * @param userId
     * @param response
     * @throws IOException
     * @apiNote To get or serve the user image
     * @author AMIT
     * @since 1.0
     */
    @GetMapping("/image/{userId}")
    public void serveUserImage(@PathVariable String userId, HttpServletResponse response) throws IOException {
        log.info("Entering the Request for get user Image for userId{}: ", userId);
        UserDto user = userServiceI.getUserById(userId);
        log.info("user image name : {} ", user.getImageName());
        InputStream resource = imageService.getResource(imageUploadPath, user.getImageName());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
        log.info("Completed the Request for get user Image for userId{}: ", userId);
    }

}
