package com.lcwd.electronic.store.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lcwd.electronic.store.dtos.UserDto;
import com.lcwd.electronic.store.entities.User;
import com.lcwd.electronic.store.payload.PageableResponse;
import com.lcwd.electronic.store.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private MockMvc mockMvc;

    private User user;

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

    @Test
    public void createUserTest() throws Exception {
        UserDto userDto = modelMapper.map(user, UserDto.class);
        Mockito.when(userService.createUser(Mockito.any())).thenReturn(userDto);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertObjectToJsonString(user))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").exists()
                );
    }

    @Test
    public void updateUserTest() throws Exception {
        String userId = "34dfj";
        UserDto dto = this.modelMapper.map(user, UserDto.class);
        Mockito.when(userService.updateUser(Mockito.any(), Mockito.anyString())).thenReturn(dto);
        this.mockMvc.perform(
                        MockMvcRequestBuilders.put("/users/" + userId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertObjectToJsonString(user))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists());
    }

    @Test
    public void getUserByIdTest() throws Exception {
        String userId = "am56sh";
        UserDto userDto = modelMapper.map(user, UserDto.class);
        Mockito.when(userService.getUserById(userId)).thenReturn(userDto);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/users/" + userId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertObjectToJsonString(user))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists());
    }

    @Test
    public void getAllUsersTest() throws Exception {
        UserDto obj1 = UserDto.builder().name("Amit").email("amit@gmail.com").about("I am a java Developer").gender("male").password("abs").imageName("default.png").build();
        UserDto obj2 = UserDto.builder().name("Dinesh").email("denny@gmail.com").about("I am a java Developer").gender("male").password("abs").imageName("default.png").build();
        UserDto obj3 = UserDto.builder().name("Sahil").email("sahil@gmail.com").about("I am a java Developer").gender("male").password("abs").imageName("default.png").build();
        UserDto obj4 = UserDto.builder().name("Aakash").email("aakash@gmail.com").about("I am a java Developer").gender("male").password("abs").imageName("default.png").build();


        PageableResponse<UserDto> pageableResponse = new PageableResponse<>();
        pageableResponse.setContent(Arrays.asList(
                obj1, obj2, obj3, obj4
        ));
        pageableResponse.setLastPage(false);
        pageableResponse.setPageNumber(1);
        pageableResponse.setPageSize(10);
        pageableResponse.setTotalPages(55);
        pageableResponse.setTotalElements(500l);
        Mockito.when(userService.getAllUsers(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString())).thenReturn(pageableResponse);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteUserTest() throws Exception {
        String userId = "am3464N";
//        ApiResponse apiResponse=ApiResponse.builder().message("User Deleted successfully !!").status(HttpStatus.OK).success(true).build();
        UserDto userDto = modelMapper.map(user, UserDto.class);
        Mockito.doNothing().when(userService).deleteUser(userId);
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/users/" + userId))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getUserByEmailTest() throws Exception {
        String email = "amit45@gmail.com";
        UserDto userDto = modelMapper.map(user, UserDto.class);

        Mockito.when(userService.getUserByEmail(email)).thenReturn(userDto);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/users/email/" + email)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertObjectToJsonString(user))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists());
    }

//    search user

//    upload user image

//    serve user image


    private String convertObjectToJsonString(Object user) {
        try {
            return new ObjectMapper().writeValueAsString(user);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
