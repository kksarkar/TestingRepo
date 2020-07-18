package com.tifinbox.app.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tifinbox.app.model.CustomUser;
import com.tifinbox.app.model.User;


@Service
public interface UserService 
{
	

	public User doRegistation(User user) throws Exception;

	public User doLogin(User user) throws Exception;

	public Map<String, Object> getConfiguration();

	

	public List<User> getVendors(String search, Float lat, Float lng);

	
	
}
