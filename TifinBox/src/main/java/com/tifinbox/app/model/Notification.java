package com.tifinbox.app.model;
import java.util.Date;
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

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;



@Entity
@Table(name="notification")

public class Notification 
{
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Integer id;
   
  @Column(name="notification_text")
  private String text;
  
  @Column(name="is_seen")
  private String isSeen;
  
  @ManyToOne
  @JoinColumn(name="user_id")
  @JsonIgnore
  private User user;

  
  @Column(name = "time_stamp" , nullable = false, updatable = false)
	@CreationTimestamp
	private Date timeStamp;

  
}