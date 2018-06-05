package com.deloitte.fi.estore.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deloitte.fi.estore.constants.Constants;
import com.deloitte.fi.estore.model.User;
import com.deloitte.fi.estore.repository.UserRepository;
import com.google.gson.Gson;

@Service
public class UserService {

	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	Gson gson;

	public User create(User user) {
		LOG.info("~~~~~ Inside create method of " + getClass().getSimpleName() + " ~~~~~");
		try {
			return userRepository.save(user);
		} catch (Exception e) {
			LOG.error("Error in create method of " + getClass().getSimpleName() + " " + e.getMessage());
		}
		return null;
	}

	public User findOne(String email) {
		LOG.info("~~~~~ Inside findOne method of " + getClass().getSimpleName() + " ~~~~~");
		try {
			return userRepository.findByEmail(email);
		} catch (Exception e) {
			LOG.error("Error in findOne method of " + getClass().getSimpleName() + " " + e.getMessage());
		}
		return null;
	}

	public User findById(String id) {
		LOG.info("~~~~~ Inside findById method of " + getClass().getSimpleName() + " ~~~~~");
		try {
			return userRepository.findOne(id);
		} catch (Exception e) {
			LOG.error("Error in findById method of " + getClass().getSimpleName() + " " + e.getMessage());
		}
		return null;
	}
	
	public Iterable<User> findAll() {
		LOG.info("~~~~~ Inside findAll method of " + getClass().getSimpleName() + " ~~~~~");
		try {
			return userRepository.findAll();
		} catch (Exception e) {
			LOG.error("Error in findAll method of " + getClass().getSimpleName() + " " + e.getMessage());
		}
		return null;
	}
	
	public String delete(String id) {
		LOG.info("~~~~~ Inside delete method of " + getClass().getSimpleName() + " ~~~~~");
		try {
			userRepository.deleteById(id);
			return gson.toJson(Constants.USER_DELETED);
		} catch (Exception e) {
			LOG.error("Error in delete method of " + getClass().getSimpleName() + " " + e.getMessage());
		}
		return null;
	}

}
