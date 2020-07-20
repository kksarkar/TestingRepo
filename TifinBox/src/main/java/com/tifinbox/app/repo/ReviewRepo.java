package com.tifinbox.app.repo;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tifinbox.app.model.Review;
import com.tifinbox.app.model.User;


@Repository
public interface ReviewRepo extends JpaRepository<Review, Integer> 
{

	Integer countByReviewTo(User user);

	
	
	
}