package com.deloitte.fi.estore.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.deloitte.fi.estore.model.User;

public interface UserRepository extends CrudRepository<User, String> {

	@Query("{ 'email': ?0 }")
	User findByEmail(String email);
	
	@Query("{ '_id': ?0 }")
	User findOne(String id);

}
