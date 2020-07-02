package com.tifinbox.app.repo;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tifinbox.app.model.Rating;
import com.tifinbox.app.model.User;


@Repository
public interface RatingRepo extends JpaRepository<Rating, Integer> 
{

	
	
	
}