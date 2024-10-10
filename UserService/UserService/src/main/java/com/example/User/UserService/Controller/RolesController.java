package com.example.User.UserService.Controller;


import com.example.User.UserService.DTO.UserRolesBean;
import com.example.User.UserService.Service.Impl.UserRoleServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1.0/")
public class RolesController {

@Autowired
    UserRoleServices userRoleServices;

@PostMapping(value = "/roles")
    public UserRolesBean addRoles(@RequestBody UserRolesBean userRolesBean){
    userRoleServices.addRole(userRolesBean);
    return userRolesBean;
}

}
