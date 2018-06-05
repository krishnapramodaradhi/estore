package com.deloitte.fi.estore.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.deloitte.fi.estore.model.Category;

public interface CategoryRepository extends CrudRepository<Category,String> {
	
	@Query("{ '_id': ?0 }")
	Category findOne(String id);
	
}
