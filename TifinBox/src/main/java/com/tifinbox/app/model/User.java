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

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import net.bytebuddy.implementation.bind.annotation.Default;

@Entity
@Table(name = "user")

public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column
	private String fullName;

	@Column
	private String username;

	@Column
	private String password;

	@Column
	@Transient
	private String confirmPassword;

	/*@OneToMany()
	@Transient
	@JsonIgnore
	private List<ServiceCategory> servicesCategory;*/

	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER , cascade=CascadeType.ALL )
	//@Transient
	//@JsonIgnore
	private Set<Service> services;

	
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

	@Transient
	@JsonIgnore
    private String tiffinfood;
    
	@Transient
	@JsonIgnore
    private Integer tiffinRs;
    
	
	
	private String city;

	@Column
	private String address;

	@Column
	private String profileUrl;

	@Column
	private String isActive;

	@Transient
	@Column
	private Role role;

	@Transient
	@Column
	private String userType;

	@Transient
	@Column
	private String token;

	@Column(name = "time_stamp", nullable = false, updatable = false)
	@CreationTimestamp
	private Date timeStamp;

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

	
	
	public String getTiffinfood() {
		return tiffinfood;
	}

	public void setTiffinfood(String tiffinfood) {
		this.tiffinfood = tiffinfood;
	}

	public Integer getTiffinRs() {
		return tiffinRs;
	}

	public void setTiffinRs(Integer tiffinRs) {
		this.tiffinRs = tiffinRs;
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

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

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

	
	
}