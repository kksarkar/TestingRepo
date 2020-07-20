package com.tifinbox.app.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;

import net.bytebuddy.implementation.bind.annotation.Default;

@Entity
@Table(name = "rating")

public class Rating {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@OneToOne
	private User ratingBy;

	@Column
	private Float ratingPoints;
	
	
	@OneToOne
	private User ratingTo;

	@Column(name = "time_stamp", nullable = false, updatable = false)
	@CreationTimestamp
	private Date timeStamp;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getRatingBy() {
		return ratingBy;
	}

	public void setRatingBy(User ratingBy) {
		this.ratingBy = ratingBy;
	}

	public User getRatingTo() {
		return ratingTo;
	}

	public void setRatingTo(User ratingTo) {
		this.ratingTo = ratingTo;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public Float getRatingPoints() {
		return ratingPoints;
	}

	public void setRatingPoints(Float ratingPoints) {
		this.ratingPoints = ratingPoints;
	}

}
