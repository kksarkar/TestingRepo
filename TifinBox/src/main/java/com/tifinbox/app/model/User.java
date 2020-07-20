package com.tifinbox.app.model;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import net.bytebuddy.implementation.bind.annotation.Default;

@Entity
@Table(name = "user")

public class User 
{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	
	@Column(length=100)
	private String fullName;

	
	
	@Column(length=100)
	private String username;

	
	@Column(length=100)
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;

	
	@Column(length=100)
	@Transient
	@JsonProperty(access = Access.WRITE_ONLY)
	private String confirmPassword;

	
	
	@Transient
	private Float distanceInKM;
	
	 
	
	/*@OneToMany()
	@Transient
	@JsonIgnore
	private List<ServiceCategory> servicesCategory;*/
	
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER , cascade=CascadeType.ALL )
	//@Transient
	//@JsonIgnore
	private Set<Service> services;

	
	
	@Column(length=100)
	private String tiffinServiceName;
	
	
	@Column
	private Integer advanceMoney;
/*
	@OneToMany()
	@Transient
	@JsonIgnore
	private Set<TiffinCategory> tiffinCategory;
*/
	
	
	
	
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade=CascadeType.ALL   )
	// @Transient
	// @JsonIgnore
	private Set<Tiffin> tiffines;

	
	
	
	private String city;

	@Column
	private String address;
	
	@Column
	private Float lat;
	
	@Column
	private Float lng;
	

	@Column
	private String profileUrl;

	@Column
	private String isActive;

	/*
	@Column
	private Role role;
*/
	
	
	
	@Column
	private String userType;

	@Transient
	@Column
	private String token;

	@Transient
	private Float totalRating;
	

	@Transient
	private Integer totalReviews;
	
	
	@Transient
	private Integer ratingCount;
	
	
	@Column(name = "time_stamp", nullable = false, updatable = false)
	@CreationTimestamp
	private Date timeStamp;

	public User()
	{
	
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
/*
	public List<ServiceCategory> getServicesCategory() {
		return servicesCategory;
	}

	public void setServicesCategory(List<ServiceCategory> servicesCategory) {
		this.servicesCategory = servicesCategory;
	}
*/
	public Integer getAdvanceMoney() {
		return advanceMoney;
	}

	public void setAdvanceMoney(Integer advanceMoney) {
		this.advanceMoney = advanceMoney;
	}

	
/*	public Set<TiffinCategory> getTiffinCategory() {
		return tiffinCategory;
	}

	public void setTiffinCategory(Set<TiffinCategory> tiffinCategory) {
		this.tiffinCategory = tiffinCategory;
	}*/

	public Set<Tiffin> getTiffines() {
		return tiffines;
	}

	public void setTiffines(Set<Tiffin> tiffines) {
		this.tiffines = tiffines;
	}

	
	



	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getProfileUrl() {
		return profileUrl;
	}

	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	/*public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}*/

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public Set<Service> getServices() {
		return services;
	}

	public void setServices(Set<Service> services) {
		this.services = services;
	}

	public String getTiffinServiceName() {
		return tiffinServiceName;
	}

	public void setTiffinServiceName(String tiffinServiceName) {
		this.tiffinServiceName = tiffinServiceName;
	}

	public Float getLat() {
		return lat;
	}

	public void setLat(Float lat) {
		this.lat = lat;
	}

	public Float getLng() {
		return lng;
	}

	public void setLng(Float lng) {
		this.lng = lng;
	}

	public Float getDistanceInKM() {
		return distanceInKM;
	}

	public void setDistanceInKM(Float distanceInKM) {
		this.distanceInKM = distanceInKM;
	}

	public Float getTotalRating() {
		return totalRating;
	}

	public void setTotalRating(Float totalRating) {
		this.totalRating = totalRating;
	}

	public Integer getTotalReviews() {
		return totalReviews;
	}

	public void setTotalReviews(Integer totalReviews) {
		this.totalReviews = totalReviews;
	}

	public Integer getRatingCount() {
		return ratingCount;
	}

	public void setRatingCount(Integer ratingCount) {
		this.ratingCount = ratingCount;
	}






	
	
}