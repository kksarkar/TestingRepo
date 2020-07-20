package com.tifinbox.app.repo;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tifinbox.app.model.User;


@Repository
public interface UserRepo extends JpaRepository<User, Integer> 
{


	
	User findByUsername(String username);

	@Query(value = "SELECT u.* , 'k' as p  FROM User u",  nativeQuery = true)
	List<User> findByCustomdetails();

	//@Query(value = "SELECT z FROM User z where u.(UPPER( z.city ) like '%:search%' or UPPER(z.tiffin_service_name) like '%:search%' or  upper(z.full_name) like '%:search%' )")
	@Query(value = "SELECT z FROM User z where (UPPER(z.city) like %:search% or UPPER(z.fullName) like %:search% or UPPER(z.tiffinServiceName)  like %:search%  ) and z.userType='Vendor' and z.isActive='Y' ")
	List<User> findBySearchText(String search);

	//@Query(value = "SELECT *from user ", nativeQuery= true)
	//@Query(value = "SELECT  z.id, p.distance_unit * DEGREES(ACOS(LEAST(1.0, COS(RADIANS(p.latpoint)) * COS(RADIANS(z.lat)) * COS(RADIANS(p.longpoint) - RADIANS(z.lng))+ SIN(RADIANS(p.latpoint))* SIN(RADIANS(z.lat))))) AS distance_in_km FROM user AS z JOIN (   /* these are the query parameters */ SELECT  21.0953  AS latpoint,  71.7504 AS longpoint, 200.0 AS radius, 111.045 AS distance_unit) AS p ON 1=1 WHERE z.lat BETWEEN p.latpoint  - (p.radius / p.distance_unit) AND p.latpoint  + (p.radius / p.distance_unit) AND z.lng BETWEEN p.longpoint - (p.radius / (p.distance_unit * COS(RADIANS(p.latpoint)))) AND p.longpoint + (p.radius / (p.distance_unit * COS(RADIANS(p.latpoint)))) ORDER BY distance_in_km" ,nativeQuery = true)
	//@Query(value = "SELECT  z.id, 'k' as z.distance_in_km from user z " ,nativeQuery = true)
//	@Query(value = "SELECT *from user ", nativeQuery= true)
	//@Query(value = "SELECT  com.tifinbox.app.model.CustomUser(z.city , z.username ) from User z ")
	//List<CustomUser> findNearByMe(Float lat, Float lng);

	@Query(value = "SELECT *from user", nativeQuery= true)
	//@Query(value = "SELECT  com.tifinbox.app.model.CustomUser(z.city , z.username ) from User z ")
	List<User> findNearByMe1(Float lat, Float lng);

	
	 //where n.intendedUser.id= :id
	
}