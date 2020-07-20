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

import javax.persistence.EntityManager;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
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

import com.google.gson.Gson;
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
import com.tifinbox.app.repo.RatingRepo;
import com.tifinbox.app.repo.ReviewRepo;
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
	RatingRepo ratingRepo;

	@Autowired
	ReviewRepo reviewRepo;

	
	@Autowired
	LocationRepo cityRepo;

	@Autowired
	TiffinCategoryRepo tiffinCategoryRepo;

	@Autowired
	ServiceCategoryRepo serviceCategoryRepo;


	@Autowired
	JdbcTemplate template ;
	 
	@Autowired
	NotificationRepo notificationRepo;

	@PersistenceContext
	private EntityManager entityManager;
	
	
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
			
			final UserDetails userDetails = loadUserByUsername(user.getUsername());

			if (userDetails == null) {
				throw new Exception("INVALID_CREDENTIALS");
			}

			final String token = jwtTokenUtil.generateToken(userDetails);
			
			
			newUser.setToken(token);
			newUser.setUserType("Customer");

			return newUser;
		}
		if (user.getUserType().equals("vendor")) {
			Role role = roleRepo.findByName("Vendor");

			userRole.setRole(role);
			userRole.setUser(newUser);

		}

		Set<com.tifinbox.app.model.Service> services = user.getServices();
		Set<com.tifinbox.app.model.Service> savingServices = new HashSet<>(); 
		
		for (com.tifinbox.app.model.Service service : services) 
		{
			
			if(service.getServiceCategory().isFlag())
			{
				ServiceCategory sc = serviceCategoryRepo.findById(service.getServiceCategory().getId())
						.orElseThrow(() -> new ResourceNotFoundException("Service Category not found."));
				service.setServiceCategory(sc);
				service.setUser(newUser);
	
				savingServices.add(service);
			}
		}
		
	
		newUser.setServices(savingServices);
		
		Set<Tiffin> tiffins = new HashSet<>();
		Set<Tiffin> tiffines = user.getTiffines();

		for (Tiffin tiffin : tiffines) {
			TiffinCategory tc = tiffinCategoryRepo.findById(tiffin.getTiffinCategory().getId())
					.orElseThrow(() -> new ResourceNotFoundException("Tifin Category not found."));
			tiffin.setTiffinCategory(tc);
			tiffin.setUser(newUser);
			tiffins.add(tiffin);
		}

		user.setTiffines(tiffins);

		newUser.setTiffines(user.getTiffines());
		
		newUser.setTiffinServiceName(user.getTiffinServiceName());
		newUser.setAdvanceMoney(user.getAdvanceMoney());

		
		newUser.setCity(user.getCity());

		newUser.setAddress(user.getAddress());
		newUser.setLat(user.getLat());
		newUser.setLng(user.getLng());
		userRepo.save(newUser);
		userRoleMapping.save(userRole);


		final UserDetails userDetails = loadUserByUsername(user.getUsername());

		if (userDetails == null) {
			throw new Exception("INVALID_CREDENTIALS");
		}

		final String token = jwtTokenUtil.generateToken(userDetails);
		
		newUser.setToken(token);
		newUser.setUserType("Vendor");
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
		}
		catch (DisabledException e) 
		{
			throw new Exception("User is disabled", e);
		} 
		catch (BadCredentialsException e) 
		{
			throw new Exception("Invalid user name or password.", e);
		}
		catch (Exception  e) 
		{
			throw new ResourceNotFoundException("Invalid user name or password.");
		}

		final UserDetails userDetails = loadUserByUsername(user.getUsername());

		if (userDetails == null) 
		{
			throw new Exception("Invalid user name or password.");
		}

		final String token = jwtTokenUtil.generateToken(userDetails);

		System.out.println(token);

		User existingUser = userRepo.findByUsername(user.getUsername());
		existingUser.setToken(token);

		existingUser.setUserType( userRoleMapping.findUserRoles(existingUser.getId()));
		
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
	public List<User> getVendors(String search, Float lat, Float lng) 
	{
		//sreturn userRepo.findNearByMe1(lat,lng);
		
		List<User> result = new ArrayList<User>();
		
		/*if(search != "null" && lat >0 & lng >0)
		{
			System.out.println(search);
			System.out.println("in all ctire");
		}
		else*/
		if ( lat >0 & lng >0)
		{
			
			System.out.println("in near by me..");
			
			int withinKm=200;
			
			SqlRowSet rs = template.queryForRowSet("SELECT  z.id, p.distance_unit * DEGREES(ACOS(LEAST(1.0, COS(RADIANS(p.latpoint)) * COS(RADIANS(z.lat)) * COS(RADIANS(p.longpoint) - RADIANS(z.lng))+ SIN(RADIANS(p.latpoint))* SIN(RADIANS(z.lat))))) AS distance_in_km FROM user AS z JOIN (   /* these are the query parameters */ SELECT  21.0953  AS latpoint,  71.7504 AS longpoint, "+withinKm+" AS radius, 111.045 AS distance_unit) AS p ON 1=1 WHERE z.lat BETWEEN p.latpoint  - (p.radius / p.distance_unit) AND p.latpoint  + (p.radius / p.distance_unit) AND z.lng BETWEEN p.longpoint - (p.radius / (p.distance_unit * COS(RADIANS(p.latpoint)))) AND p.longpoint + (p.radius / (p.distance_unit * COS(RADIANS(p.latpoint)))) and is_active = 'Y' and user_type='Vendor' ORDER BY distance_in_km");
			System.out.print("get data...");
			while(rs.next())
			{
				User user = new User();
				System.out.println(rs.getInt(1));
				user = userRepo.findById(rs.getInt(1)).orElse(null);
				user.setDistanceInKM(rs.getFloat(2));
				
			//	reviewTo
				
				SqlRowSet rsGetRating = template.queryForRowSet("select rating0_.rating_to_id , count(rating0_.rating_to_id), avg(rating0_.rating_points) as col_0_0_  from rating rating0_  group by rating0_.rating_to_id having rating0_.rating_to_id="+ user.getId());
				while(rsGetRating.next())
				{
					user.setRatingCount( rsGetRating.getInt(2));
					user.setTotalRating(rsGetRating.getFloat(3) );
				}
				
				//user.setTotalRating(ratingRepo.getTotalRating(user.getId()));
				
				user.setTotalReviews(reviewRepo.countByReviewTo(user));
				
				result.add(user);
			}
			//result= userRepo.findNearByMe(lat,lng);
		}
		else if(search != null )
		{
			if(search.length()<=2)
			{
				throw new ResourceNotFoundException("Please enter more than 2 characters.");
			}
			else
			{
				System.out.println("in search");
				result= userRepo.findBySearchText(search);	
				
				//result.forEach(user -> user.setTotalRating(ratingRepo.getTotalRating(user.getId())));
				
				for(User user : result)
				{
					SqlRowSet rsGetRating = template.queryForRowSet("select rating0_.rating_to_id , count(rating0_.rating_to_id), avg(rating0_.rating_points) as col_0_0_  from rating rating0_  group by rating0_.rating_to_id having rating0_.rating_to_id="+ user.getId());
					while(rsGetRating.next())
					{
						user.setRatingCount( rsGetRating.getInt(2));
						user.setTotalRating(rsGetRating.getFloat(3) );
					}
					user.setTotalReviews(reviewRepo.countByReviewTo(user));
				}
				
				
				//result.forEach(user -> user.setTotalReviews(reviewRepo.countByReviewTo(user)));
				
				
			}
			
		}
		else
		{
			throw new ResourceNotFoundException("Please enter search string or allow location  to server you.");
		}
		
		return result;
		
	}

/*
	@Override
	public List<User> getVendors(String search) 
	{
		//return null;
		
		 /*System.out.println(" -- finding all EmployeeSalary --");
	      List<EmployeeSalary> list = repo.findBy();
	      for (EmployeeSalary es : list) {
	          System.out.printf("Name: %s, Salary: %s%n", es.getName(), es.getSalary());
	      }
	      
		
	      Query query = entityManager.createQuery("Select e from User e");
	      List<User> list = query.getResultList();

	      for(User e:list) {
	         System.out.println("Employee NAME :"+e.getFullName());
	      }
	      Query query1 = entityManager.createQuery("Select e , 'extra' from User e");
	      List<Object[]> list1 = query1.getResultList();

	      System.out.println("ddd");
	      for(Object[] result:list1) 
	      {
	    	    User name = (User) result[0];
	    	    String count = ((String) result[1]);
	    	    System.out.println(name);
	    	    System.out.println(count);
	    	    /*
	    	    System.out.println(result[0]);
	    	    System.out.println(result);*/
	    	    
	    	   // System.out.println(new Gson().toJson(name));
		 // }
	      
	      /*
	      List<Object[]> results = entityManager.createQuery("SELECT m.name AS name, COUNT(m) AS total FROM Man AS m GROUP BY m.name ORDER BY m.name ASC");
	    	        .getResultList();
	    	for (Object[] result : results) {
	    	    String name = (String) result[0];
	    	    int count = ((Number) result[1]).intValue();
	    	}*/
		
		//return null;
		
		
	//}*/
	
	

}
