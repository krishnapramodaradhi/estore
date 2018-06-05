package com.deloitte.fi.estore.products.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.deloitte.fi.estore.products.model.Product;

public interface ProductsRepository extends CrudRepository<Product, String> {

	@Query("{ '_id': ?0 }")
	Product findOne(String id);
}
