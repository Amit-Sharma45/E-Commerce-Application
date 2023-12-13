package com.lcwd.electronic.store.dtos;

import com.lcwd.electronic.store.validate.ImageNameValid;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private String userId;
    @Size(min = 3, max = 20, message = "Invalid Name !!")
    private String name;
    @Pattern(regexp = "^[a-z0-9][-a-z0-9._]+@([-a-z0-9]+\\.)+[a-z]{2,5}$", message = "Invalid Email !!")
    @NotBlank(message = "Email is Required")
    private String email;
    @NotBlank(message = "Password is Required !!")
    private String password;
    @Size(min = 4, max = 6, message = "Invalid gender !!")
    private String gender;
    @NotBlank(message = "Write something about yourself !!")
    @Size(max = 1000)
    private String about;

    //    @Pattern
//    Custom Validator
    @ImageNameValid(message = "Invalid Image Name !!")
    private String imageName;
}
