package com.example.User.UserService.DTO;

import com.example.User.UserService.Entity.User;
import com.example.User.UserService.UserPermissions;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRolesBean {

    @Id
    private int Id;
    private String name;

    private List<UserPermissions> permissionsSet=new ArrayList<>();
}
