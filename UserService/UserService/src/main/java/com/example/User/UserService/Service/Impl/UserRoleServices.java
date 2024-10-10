package com.example.User.UserService.Service.Impl;


import com.example.User.UserService.DTO.UserRolesBean;
import com.example.User.UserService.Entity.UserRoles;
import com.example.User.UserService.Repository.UserRolesRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserRoleServices {


    @Autowired
    UserRolesRepository repository;

    public UserRolesBean addRole(UserRolesBean userRolesBean){
        UserRoles userRoles=new UserRoles();
        BeanUtils.copyProperties(userRolesBean,userRoles);
        repository.save(userRoles);
        return userRolesBean;
    }

    public UserRolesBean findRoleById(int roleId) {
	   UserRoles userRoles=repository.findById(roleId).get();
        return  mapDomainToBean(userRoles);
	}

	public UserRolesBean updateRole(UserRolesBean roleBean) {
		return mapDomainToBean(repository.save(mapBeanToDomain(roleBean)));
	}

	public List<UserRolesBean> deleteRole(UserRolesBean roleBean) {
		repository.delete(mapBeanToDomain(roleBean));
		return findAllRoles();
	}

	public List<UserRolesBean>  deleteRole(String roleId) {
		repository.deleteById(Integer.valueOf(roleId));
		return findAllRoles();
	}

	public List<UserRolesBean> findAllRoles() {
		List<UserRolesBean> userRoleBeanList = new ArrayList<>();

		List<UserRoles> userRolesList = repository.findAll();

		if(userRolesList != null && userRolesList.size() >0) {
			userRolesList.stream().forEach(userRoleDomain->{
				userRoleBeanList.add(mapDomainToBean(userRoleDomain));
			});
		}
		return userRoleBeanList;
	}
	public UserRoles mapBeanToDomain(UserRolesBean UserRoleBean) {
        UserRoles userRoles=new UserRoles();
        BeanUtils.copyProperties(UserRoleBean,userRoles);

		return userRoles;

	}
	  public UserRolesBean mapDomainToBean(UserRoles roleDomain) {
		  UserRolesBean userRoleBean = new UserRolesBean();
		  BeanUtils.copyProperties(roleDomain, userRoleBean);
		return userRoleBean;
	}

}
