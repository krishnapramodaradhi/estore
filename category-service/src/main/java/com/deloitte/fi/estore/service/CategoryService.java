package com.deloitte.fi.estore.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deloitte.fi.estore.model.Category;
import com.deloitte.fi.estore.repository.CategoryRepository;

@Service
public class CategoryService {

	private static final Logger log = LoggerFactory.getLogger(CategoryService.class);

	@Autowired
	CategoryRepository categoryRepository;

	public Category create(Category category) {
		log.info("~~~~Inside Category Create~~~~~" + getClass().getSimpleName());
		try {
			return categoryRepository.save(category);
		} catch (Exception e) {
			log.error("Error in create method of " + getClass().getSimpleName() + " " + e.getMessage());
		}
		return null;
	}

	public Iterable<Category> findAll() {
		log.info("~~~~Inside Category findAll~~~~~" + getClass().getSimpleName());
		try {
			return categoryRepository.findAll();
		} catch (Exception e) {
			log.error("Error in findAll method of " + getClass().getSimpleName() + " " + e.getMessage());
		}
		return null;
	}

	public Optional<Category> findOne(String id) {
		log.info("~~~~Inside Category FindOne~~~~~" + getClass().getSimpleName());
		try {
			return categoryRepository.findById(id);
		} catch (Exception e) {
			log.error("Error in FindOne method of " + getClass().getSimpleName() + " " + e.getMessage());
		}
		return null;
	}
	
	public Category update(String id, Category category) {
		log.info("~~~~Inside Category update~~~~~" + getClass().getSimpleName());
		try {
			Category cat = categoryRepository.findOne(id);
			cat.setTitle(category.getName());
			return categoryRepository.save(cat);
		} catch (Exception e) {
			log.error("Error in Update method of " + getClass().getSimpleName() + " " + e.getMessage());
		}
		return null;
	}
	
	public String delete(String id) {
		log.info("~~~~Inside Category Delete~~~~~" + getClass().getSimpleName());
		try {	
			categoryRepository.deleteById(id);
			return "Category Deleted";
		} catch (Exception e) {
			log.error("Error in delete method of " + getClass().getSimpleName() + " " + e.getMessage());
		}
		return "Error in deletion";
	}
	
	

}
