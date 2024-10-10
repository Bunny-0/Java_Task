package com.example.User.UserService.Entity;


import com.example.User.UserService.UserPermissions;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data

public class UserRoles {



    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int Id;

	@Column(nullable = false,unique = true, updatable = true)
	private String name;

	@OneToOne
//	@JsonIgnore
	@ToString.Exclude
	User user;

	@ElementCollection(fetch = FetchType.EAGER) // Stores as a collection in a separate table
	@Enumerated(EnumType.STRING)
	private List<UserPermissions> permissionsSet=new ArrayList<>();

}
