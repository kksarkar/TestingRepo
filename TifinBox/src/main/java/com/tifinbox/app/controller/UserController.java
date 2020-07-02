package com.tifinbox.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tifinbox.app.model.User;
import com.tifinbox.app.repo.UserRepo;
import com.tifinbox.app.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserController 
{
    
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);



	@Autowired
	UserRepo userRepo;
	
	@Autowired
	UserService userService;
	
	@PostMapping("/user/registation")
	public User doRegistation(@RequestBody User user) throws Exception
	{
		logger.debug("in UserController -> Registration() "); 
		
		return userService.doRegistation(user);	
	}
	
	
	@PostMapping("/user/login")
	public User doLogin(@RequestBody User user) throws Exception
	{
		logger.debug("in UserController -> doLogin() "); 
		
		return userService.doLogin(user);	
	}
	
	@GetMapping("/user/getconfig")
	public Map<String, Object> getConfiguration()
	{
		
		logger.debug("in UserController -> getConfiguration() "); 
		return userService.getConfiguration();	
		
	}
	
	
}
