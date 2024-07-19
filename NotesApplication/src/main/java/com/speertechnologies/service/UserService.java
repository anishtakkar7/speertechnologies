package com.speertechnologies.service;

import com.speertechnologies.entity.User;
import com.speertechnologies.model.UserRequest;

public interface UserService {

	User saveUser(UserRequest userRequest);
	
	User getUserById(long userId);
}
