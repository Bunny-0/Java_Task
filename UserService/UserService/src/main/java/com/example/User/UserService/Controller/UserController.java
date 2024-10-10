package com.example.User.UserService.Controller;

import com.example.User.UserService.DTO.UserEntryDto;
import com.example.User.UserService.DTO.UserResponseDto;
import com.example.User.UserService.Entity.User;
import com.example.User.UserService.Service.Impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(value = "/users")
    public ResponseEntity<UserResponseDto> registerUser(@RequestBody UserEntryDto userEntryDto) {
        UserResponseDto userResponseDto = userService.registerUser(userEntryDto);
        return new ResponseEntity<>(userResponseDto, HttpStatus.CREATED);
    }

    @GetMapping(value = "/users/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable("id") int id) {
        UserResponseDto userResponseDto = userService.getUserById(id);
        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    @GetMapping(value = "/users")
    public ResponseEntity<List<UserResponseDto>> getUsers() {
        List<UserResponseDto> userResponseDtos = userService.getALlUser();
        return new ResponseEntity<>(userResponseDtos, HttpStatus.OK);
    }

    @GetMapping("/users/{name}")
    public ResponseEntity<UserResponseDto> getUserByName(@PathVariable("name") String name) {
        UserResponseDto userResponseDto = userService.getUserByName(name);
        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    @PutMapping("/users")
    public ResponseEntity<UserResponseDto> updateUser(@RequestBody User user) {
        UserResponseDto userResponseDto = userService.updateUser(user);
        return new ResponseEntity<>(userResponseDto, HttpStatus.ACCEPTED);
    }
}
