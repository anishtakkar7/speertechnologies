package com.speertechnologies.service;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.speertechnologies.entity.User;
import com.speertechnologies.exception.NotFoundException;
import com.speertechnologies.model.UserRequest;
import com.speertechnologies.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
    PasswordEncoder passwordEncoder;
	
	@Override
	public User saveUser(UserRequest userRequest) {
		
		Instant currentTime = Instant.now();
		User user =  User.builder().userCreated(currentTime).userUpdated(currentTime).userName(userRequest.getUserName()).password(passwordEncoder.encode(userRequest.getPassword())).build();
		return userRepository.save(user);
	}

	@Override
	public User getUserById(long userId) {
		return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found in database", "USER_NOT_FOUND"));	//create custom exception class and ExceptionHandler	
	}

}
