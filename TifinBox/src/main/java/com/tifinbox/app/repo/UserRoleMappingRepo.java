package com.tifinbox.app.repo;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tifinbox.app.model.UserRoleMapping;


@Repository
public interface UserRoleMappingRepo extends JpaRepository<UserRoleMapping, Integer> ,UserRoleMappingCustomRepo
{
	
}