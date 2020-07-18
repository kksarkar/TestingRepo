package com.tifinbox.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
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

import com.tifinbox.app.model.CustomUser;
import com.tifinbox.app.model.User;
import com.tifinbox.app.repo.UserRepo;
import com.tifinbox.app.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

import java.sql.ResultSet;
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
	
	
	@GetMapping("/user/getVendors/{search}/{lat}/{lng}")
	public List<User> getVendors(@PathVariable String search, @PathVariable Float lat, @PathVariable Float lng)
	{
		
		logger.debug("in UserController -> getVendors() "); 
		return userService.getVendors(search,lat,lng);
		
	}
	@GetMapping("/user/getPerson")
	public List<User> getVendors1()
	{
		
		logger.debug("in UserController -> getVendors() "); 
		//return userService.getVendors(search,lat,lng);
		return userRepo.findAll();
		
	}
	
	   
	
}


/*

Testing:

1) we can pass this json for /user/registation API

{
 
    "fullName": "Javed",
    "username": "javed",
    "password": "123456",
    "confirmPassword": "123456",
    "services": [
        {

            "serviceCategory": 
			{
                "id": 3
            }
        },
        {
            "serviceCategory": 
			{
                "id": 2
            }
        }
    ],
    "advanceMoney": 500,
    "tiffines": [
        {
            
            "tiffinType": 
			{
                "id": 1
            
            },
            "tiffinfood": "Shak rotli",
            "tiffinRs": 50
        }
        
    ],
    "city": "Mahuva",
    "address": "Fatima soc",
    
    "userType": "vendor"
    
}

*/