package com.MyProject.service;

import com.MyProject.entity.User;

public interface UserService {
	
	public User findByEmail(String email);
	
	public 	User findByActivation(String activation);
	
	public String registerUser (User user);

	public String userActivation(String code);
	
	
}
