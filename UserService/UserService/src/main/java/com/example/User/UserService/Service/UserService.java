package com.example.User.UserService.Service;

import com.example.User.UserService.DTO.UserEntryDto;
import com.example.User.UserService.DTO.UserResponseDto;
import com.example.User.UserService.Entity.User;
import com.example.User.UserService.Repository.UserRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@NoArgsConstructor
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    KafkaTemplate kafkaTemplate;

    public UserResponseDto registerUser(UserEntryDto userData){

        User user=new User(userData.getId(),userData.getUserName(),userData.getPassword(),userData.getEmail(),userData.getPhoneNumber());
        User savedData=userRepository.save(user);
        UserResponseDto responseDto=UserResponseDto.builder().userName(savedData.getName()).email(savedData.getEmail()).build();
        String message="New User Registered - "+responseDto.toString();
        kafkaTemplate.send("create_user", message);
    return responseDto;

    }

    public List<UserResponseDto> getALlUser(){
        List<User> data=userRepository.findAll();
        List<UserResponseDto> responseDtoList=new ArrayList<>();
        data.stream().forEach(user -> {
            UserResponseDto responseDto=UserResponseDto.builder().userName(user.getName()).email(user.getEmail()).build();
            responseDtoList.add(responseDto);
        });
        return responseDtoList;


    }

    public UserResponseDto getUserById(int id){
        User user=userRepository.findById(id).get();
        UserResponseDto userResponseDto=UserResponseDto.builder().userName(user.getName()).email(user.getEmail()).build();
        return userResponseDto;



    }
    public UserResponseDto getUserByName(String name){
        User user=null;
        user=userRepository.findByName(name);
        UserResponseDto userResponseDto=UserResponseDto.builder().userName(user.getName()).email(user.getEmail()).build();
        return userResponseDto;
    }
    public UserResponseDto updateUser(User user){
        User user1=userRepository.save(user);
        UserResponseDto userResponseDto=UserResponseDto.builder().userName(user1.getName()).email(user1.getEmail()).build();
        return userResponseDto;
    }


}
