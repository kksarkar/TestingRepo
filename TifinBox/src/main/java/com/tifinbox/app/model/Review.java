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

import com.sun.istack.NotNull;

import net.bytebuddy.implementation.bind.annotation.Default;

@Entity
@Table(name = "review")

public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@OneToOne
	private User reviewBy;

	@OneToOne
	private User reviewTo;

	@NotNull
	private String reviewText;
	
	
	@Column(name = "time_stamp", nullable = false, updatable = false)
	@CreationTimestamp
	private Date timeStamp;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getReviewBy() {
		return reviewBy;
	}

	public void setReviewBy(User reviewBy) {
		this.reviewBy = reviewBy;
	}

	public User getReviewTo() {
		return reviewTo;
	}

	public void setReviewTo(User reviewTo) {
		this.reviewTo = reviewTo;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getReviewText() {
		return reviewText;
	}

	public void setReviewText(String reviewText) {
		this.reviewText = reviewText;
	}

}
