package com.tifinbox.app.repoimpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.tifinbox.app.model.UserRoleMapping;
import com.tifinbox.app.repo.UserRoleMappingCustomRepo;


public class UserRoleMappingCustomRepoImpl implements UserRoleMappingCustomRepo
{

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public String findUserRoles(int userId) 
	{
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		
		CriteriaQuery<String> query = cb.createQuery(String.class);
		
		Root<UserRoleMapping> userRoleMappingRoot = query.from(UserRoleMapping.class);
		
		query.select(  userRoleMappingRoot.get("role").get("name")).where(cb.equal(userRoleMappingRoot.get("user").get("id"), userId))  ;
		
		return entityManager.createQuery(query).getSingleResult();
	}

}
