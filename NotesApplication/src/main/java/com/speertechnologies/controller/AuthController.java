package com.speertechnologies.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.speertechnologies.entity.User;
import com.speertechnologies.exception.UserServiceException;
import com.speertechnologies.jwt.security.JwtUtil;
import com.speertechnologies.model.AuthenticationRequest;
import com.speertechnologies.model.AuthenticationResponse;
import com.speertechnologies.model.UserRequest;
import com.speertechnologies.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	
	@Autowired
	private UserService userService;
	@Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    
    
	@PostMapping("/signup")
	public ResponseEntity<String> userSignUp(@RequestBody UserRequest userRequest)
	{
		userService.saveUser(userRequest);
		return new ResponseEntity<String>("User created successfully !!!", HttpStatus.CREATED);
	}


    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (UsernameNotFoundException e) {
            throw new UserServiceException("Incorrect username or password", "USER_NOT_AUTHENTICATED");
            
        }

        final String jwt = jwtUtil.generateToken(authenticationRequest.getUsername());

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
	
//	
//	@GetMapping("/login/")
//	public ResponseEntity<Map<String,String>> userLogin(@RequestBody UserRequest userRequest)
//	{
//		long userId = 11l ;//use token to get user id 
//		User user  = userService.getUserByIdAndPassword(userRequest);
//		String token = ""; //generate jwt token if user found
//		HashMap<String, String> tokenValue = new HashMap<String, String>();
//		tokenValue.put("Token", token);
//		return new ResponseEntity<Map<String,String>>(tokenValue, HttpStatus.CREATED);
//	}
}
