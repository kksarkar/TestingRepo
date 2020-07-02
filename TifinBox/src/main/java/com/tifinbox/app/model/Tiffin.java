package com.tifinbox.app.model;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;




@Entity
@Table(name="tiffin")
public class Tiffin 
{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
   
    @OneToOne(fetch=FetchType.EAGER)
    private TiffinCategory tiffinType;
  
    @Column
    private String tiffinfood;
    
    @Column
    private Integer tiffinRs;
    

	@ManyToOne
	@JoinColumn(name="user_id")
	@JsonIgnore
	private User user;


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	


	public TiffinCategory getTiffinType() {
		return tiffinType;
	}


	public void setTiffinType(TiffinCategory tiffinType) {
		this.tiffinType = tiffinType;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
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
	
	
	
  
}