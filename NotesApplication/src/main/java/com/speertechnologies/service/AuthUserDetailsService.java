package com.speertechnologies.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.speertechnologies.entity.User;
import com.speertechnologies.repository.UserRepository;

@Service
public class AuthUserDetailsService implements UserDetailsService{

	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findById(Long.valueOf(username)).get();

        return org.springframework.security.core.userdetails.User
                .withUsername(String.valueOf(user.getUserId()))
                .password(user.getPassword())
                .authorities("USER")
                .build();
	}

}
