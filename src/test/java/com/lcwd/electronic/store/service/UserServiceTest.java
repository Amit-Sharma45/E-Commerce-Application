package com.lcwd.electronic.store.service;

import com.lcwd.electronic.store.dtos.UserDto;
import com.lcwd.electronic.store.entities.User;
import com.lcwd.electronic.store.payload.PageableResponse;
import com.lcwd.electronic.store.repositories.UserRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@SpringBootTest
public class UserServiceTest {

    @MockBean
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;

    @BeforeEach
    public void init() {
        user = User.builder()
                .name("Amit")
                .email("amit45@gmail.com")
                .gender("Male")
                .about("I am java developer")
                .imageName("amit.png")
                .password("akku")
                .build();
    }

    User user;

    //    create user
    @Test
    public void createUserTest() {
        Mockito.when(userRepo.save(Mockito.any())).thenReturn(user);
        UserDto savedUser = userService.createUser(modelMapper.map(user, UserDto.class));

        String actualName = savedUser.getName();
        System.out.println(actualName);
        String expectedName = "Amit";
        Assertions.assertEquals(expectedName, actualName);
//        Assertions.assertNotNull(savedUser);
    }

    //    update user
    @Test
    public void updateUserTest() {
        String userId = "an445g";
        UserDto userDto = new UserDto();
        userDto.setName("Amit Sharma");
        userDto.setImageName("anit.jpg");
        userDto.setAbout("I am a backend java developer");

        Mockito.when(userRepo.findById(Mockito.anyString())).thenReturn(Optional.of(user));
        Mockito.when(userRepo.save(Mockito.any())).thenReturn(user);

        UserDto userDto1 = userService.updateUser(userDto, userId);
        System.out.println(userDto1.getName());
        Assertions.assertEquals(userDto.getName(), userDto1.getName());

    }

    //    get user by id
    @Test
    public void getUserByIdTest() {
        String userId = "an445g";
        Mockito.when(userRepo.findById(userId)).thenReturn(Optional.of(user));
        UserDto userDto = userService.getUserById(userId);
        System.out.println(userDto.getName());
        Assertions.assertNotNull(userDto);
        Assertions.assertEquals(user.getName(), userDto.getName(), "name not matched !!");
    }

    @Test
    public void getAllUsersTest() {
        User user1 = User.builder()
                .name("Aakash")
                .email("aakash55@gmail.com")
                .gender("Male")
                .about("I am fullStack developer")
                .imageName("aakash.png")
                .password("abd")
                .build();
        User user2 = User.builder()
                .name("Dinesh")
                .email("dinesh85@gmail.com")
                .gender("Male")
                .about("I am a QA Tester")
                .imageName("dinesh.png")
                .password("denni")
                .build();
        List<User> userList = Arrays.asList(user, user1, user2);
        Page<User> page = new PageImpl<>(userList);
        Mockito.when(userRepo.findAll((Pageable) Mockito.any())).thenReturn(page);

        PageableResponse<UserDto> allUsers = userService.getAllUsers(1, 2, "name", "asc");

        Assertions.assertEquals(3, allUsers.getContent().size());

    }

    @Test
    public void deleteUserTest() {
        String userId = "an445g";
        Mockito.when(userRepo.findById("an445g")).thenReturn(Optional.of(user));

        userService.deleteUser(userId);
        Mockito.verify(userRepo, Mockito.times(1)).delete(user);
    }

    @Test
    public void getUserByEmailTest() {
        String email = "amit45@gmail.com";
        Mockito.when(userRepo.findByEmail(email)).thenReturn(Optional.of(user));

        UserDto userDto = userService.getUserByEmail(email);
        System.out.println(userDto.getName());
        Assertions.assertNotNull(userDto);
        Assertions.assertEquals(user.getName(), userDto.getName());
    }

    @Test
    public void searchUserTest() {
        User user1 = User.builder()
                .name("Aakash Kumar")
                .email("aakash55@gmail.com")
                .gender("Male")
                .about("I am fullStack developer")
                .imageName("aakash.png")
                .password("abd")
                .build();
        User user2 = User.builder()
                .name("Dinesh Kumar")
                .email("dinesh85@gmail.com")
                .gender("Male")
                .about("I am a QA Tester")
                .imageName("dinesh.png")
                .password("denni")
                .build();
        String keyword = "Kumar";
        Mockito.when(userRepo.findByNameContaining(keyword)).thenReturn(Arrays.asList(user1, user2));

        List<UserDto> userDtoList = userService.searchUser(keyword);
        Assertions.assertEquals(2, userDtoList.size(),"size not matched !!");
    }

}
