package com.example.User.UserService.DTO;


import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class UserEntryDto {


    int id;
    private String userName;
    private String Password;
    private String email;
    private String phoneNumber;
}
