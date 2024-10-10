package com.example.User.UserService.Controller;


import com.example.User.UserService.DTO.UserRolesBean;
import com.example.User.UserService.Service.Impl.UserRoleServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/roles/{roleId}")
    public ResponseEntity<UserRolesBean> findRoleById(@PathVariable int roleId) {
        UserRolesBean roleBean = userRoleServices.findRoleById(roleId);
        return new ResponseEntity<>(roleBean, HttpStatus.OK);
    }

    @PutMapping("/roles")
    public ResponseEntity<UserRolesBean> updateRole(@RequestBody UserRolesBean roleBean) {
        UserRolesBean updatedRole = userRoleServices.updateRole(roleBean);
        return new ResponseEntity<>(updatedRole, HttpStatus.OK);
    }

    @DeleteMapping("/roles/{roleId}")
    public ResponseEntity<List<UserRolesBean>> deleteRole(@PathVariable String roleId) {
        List<UserRolesBean> updatedRoles = userRoleServices.deleteRole(roleId);
        return new ResponseEntity<>(updatedRoles, HttpStatus.OK);
    }

    @GetMapping("/roles")
    public ResponseEntity<List<UserRolesBean>> findAllRoles() {
        List<UserRolesBean> allRoles = userRoleServices.findAllRoles();
        return new ResponseEntity<>(allRoles, HttpStatus.OK);
    }

}
