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

    @Query("SELECT  AVG(u.ratingPoints) from Rating u where u.ratingTo.id= :id")
   // @Query(value= "select avg(r.rating_points) from rating r where r.ratingTo.id = ?id" , nativeQuery = true)
   // @Query("SELECT AVG(e.rating) FROM rating e WHERE e.routeUid = ?1") 
	Float getTotalRating(Integer id);

	
}