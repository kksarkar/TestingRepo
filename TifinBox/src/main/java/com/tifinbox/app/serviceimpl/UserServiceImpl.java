package com.tifinbox.app.serviceimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.OneToOne;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tifinbox.app.configure.JwtTokenUtil;
import com.tifinbox.app.exception.CustomException;
import com.tifinbox.app.exception.ResourceAlredyExistException;
import com.tifinbox.app.exception.ResourceNotFoundException;
import com.tifinbox.app.model.Location;
import com.tifinbox.app.model.Role;
import com.tifinbox.app.model.ServiceCategory;
import com.tifinbox.app.model.Tiffin;
import com.tifinbox.app.model.TiffinCategory;
import com.tifinbox.app.model.User;
import com.tifinbox.app.model.UserRoleMapping;
import com.tifinbox.app.repo.LocationRepo;
import com.tifinbox.app.repo.NotificationRepo;
import com.tifinbox.app.repo.RoleRepo;
import com.tifinbox.app.repo.ServiceCategoryRepo;
import com.tifinbox.app.repo.TiffinCategoryRepo;
import com.tifinbox.app.repo.TiffinRepo;
import com.tifinbox.app.repo.UserRepo;
import com.tifinbox.app.repo.UserRoleMappingRepo;
import com.tifinbox.app.service.UserService;

import net.bytebuddy.dynamic.scaffold.MethodRegistry.Handler.ForAbstractMethod;

@Service
@Transactional
public class UserServiceImpl implements UserDetailsService, UserService {
	@Autowired
	private RoleRepo roleRepo;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Autowired
	UserRoleMappingRepo userRoleMapping;

	@Autowired
	UserRepo userRepo;

	@Autowired
	LocationRepo cityRepo;

	@Autowired
	TiffinCategoryRepo tiffinCategoryRepo;

	@Autowired
	ServiceCategoryRepo serviceCategoryRepo;

	@Autowired
	NotificationRepo notificationRepo;


	@Autowired
	LocationRepo locationRepo;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepo.findByUsername(username);

		if (user == null) {
			throw new ResourceNotFoundException("Invalid user name or password.");
		}
		System.out.println(username + "=111username");

		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				new ArrayList<>());

	}

	public User GetUser() 
	{
		User user = userRepo.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		if (user == null) {
			throw new ResourceNotFoundException("Please sign in again and continue.");
		}
		return user;
	}

	@Override
	public User doRegistation(User user) throws Exception {
		validateUser(user);

		User newUser = new User();
		newUser.setFullName(user.getFullName());
		newUser.setUsername(user.getUsername());
		newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		newUser.setIsActive("Y");

		UserRoleMapping userRole = new UserRoleMapping();

		if (user.getUserType().equals("customer")) 
		{
			Role role = roleRepo.findByName("Customer");

			userRole.setRole(role);
			userRole.setUser(newUser);

			userRepo.save(newUser);
			userRoleMapping.save(userRole);

			return user;
		}
		if (user.getUserType().equals("vendor")) {
			Role role = roleRepo.findByName("Vendor");

			userRole.setRole(role);
			userRole.setUser(newUser);

		}

		Set<com.tifinbox.app.model.Service> services = user.getServices();
		for (com.tifinbox.app.model.Service service : services) 
		{
			ServiceCategory sc = serviceCategoryRepo.findById(service.getServiceCategory().getId())
					.orElseThrow(() -> new ResourceNotFoundException("Service Category not found."));
			service.setServiceCategory(sc);
			service.setUser(newUser);

			services.add(service);
		}
		newUser.setServices(services);

		Set<Tiffin> tiffins = new HashSet<>();
		Set<Tiffin> tiffines = user.getTiffines();

		for (Tiffin tiffin : tiffines) {
			TiffinCategory tc = tiffinCategoryRepo.findById(tiffin.getTiffinType().getId())
					.orElseThrow(() -> new ResourceNotFoundException("Tifin Category not found."));
			tiffin.setTiffinType(tc);
			tiffin.setUser(newUser);
			tiffins.add(tiffin);
		}

		user.setTiffines(tiffins);

		newUser.setTiffines(user.getTiffines());

		newUser.setAdvanceMoney(user.getAdvanceMoney());

		
		newUser.setCity(user.getCity());

		newUser.setAddress(user.getAddress());

		userRepo.save(newUser);
		userRoleMapping.save(userRole);


		final UserDetails userDetails = loadUserByUsername(user.getUsername());

		if (userDetails == null) {
			throw new Exception("INVALID_CREDENTIALS");
		}

		final String token = jwtTokenUtil.generateToken(userDetails);
		
		newUser.setToken(token);
		
		return newUser;

	}

	private void validateUser(User user) {
		User ExistingUser = userRepo.findByUsername(user.getUsername());
		if (ExistingUser != null) {
			throw new ResourceAlredyExistException("Username is alredy exists. Please try another name");
		}

		if (!user.getPassword().equals(user.getConfirmPassword())) {
			throw new CustomException("Password and confirm password don't match.");
		}
	}

	@Override
	public User doLogin(User user) throws Exception 
	{

		try {
			authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}

		final UserDetails userDetails = loadUserByUsername(user.getUsername());

		if (userDetails == null) {
			throw new Exception("INVALID_CREDENTIALS");
		}

		final String token = jwtTokenUtil.generateToken(userDetails);

		System.out.println(token);

		User existingUser = userRepo.findByUsername(user.getUsername());
		existingUser.setToken(token);

		return existingUser;
	}

	@Override
	public Map<String, Object> getConfiguration() 
	{
	
		Map<String,Object> map=new HashMap<>();
		
		//List<Location> locations =  locationRepo.findAll();

		List<TiffinCategory> tifinCategories =  tiffinCategoryRepo.findAll();

		List<ServiceCategory> serviceCategory =  serviceCategoryRepo.findAll();
		
		//map.put("cities",  locations);
    	map.put("tifinCategories",tifinCategories);
    	map.put("serviceCategory",serviceCategory);
    	
    	return map;
    	
	}

	@Override
	public User getUserForTesting() 
	{
	
		return userRepo.findById(1).orElse(new User());
	}

}
