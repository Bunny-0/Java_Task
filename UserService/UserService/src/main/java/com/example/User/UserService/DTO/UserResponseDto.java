package com.example.User.UserService.DTO;


import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class UserResponseDto {

    private String email;
    private String userName;
}
