package com.ecole.service;

import java.util.List;

import com.ecole.domain.Role;
import com.ecole.domain.User;

public interface UserService {
	User saveUser(User user);
	Role saveRole(Role role);
	void addRoleToUser(String username, String rolename);
	User getUser(String username);
	List<User>getUsers();
	
}
