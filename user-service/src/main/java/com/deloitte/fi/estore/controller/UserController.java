package com.deloitte.fi.estore.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.deloitte.fi.estore.constants.Constants;
import com.deloitte.fi.estore.exception.InternalServerException;
import com.deloitte.fi.estore.exception.InvalidArgumentException;
import com.deloitte.fi.estore.exception.NotFoundException;
import com.deloitte.fi.estore.model.User;
import com.deloitte.fi.estore.service.UserService;
import com.deloitte.fi.estore.utility.UserUtilities;
import com.google.gson.Gson;

@RestController
@RequestMapping(Constants.USER_ROUTE)
@CrossOrigin("*")
public class UserController {

	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

	private UserService userService;
	private UserUtilities userUtilities;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	private Gson gson;

	public UserController(UserService userService, UserUtilities userUtilities,
			BCryptPasswordEncoder bCryptPasswordEncoder, Gson gson) {
		this.userService = userService;
		this.userUtilities = userUtilities;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.gson = gson;
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public User create(@RequestBody User user) {
		LOG.info("~~~~~ Inside create method of " + this.getClass().getSimpleName() + " ~~~~~");
		try {
			if (null == user)
				throw new NotFoundException(Constants.INVALID_FORM);
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			user.setRole(Constants.USER);
			return userService.create(user);
		} catch (Exception e) {
			LOG.error("~~~~~ Exception in create method of " + this.getClass().getSimpleName() + " "
					+ e.getLocalizedMessage() + " ~~~~~");
			throw new InternalServerException(e.getLocalizedMessage());
		}
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, value = Constants.USER_LOGIN)
	public String login(@RequestBody User user) {
		LOG.info("~~~~~ Inside login method of " + this.getClass().getSimpleName() + " ~~~~~");
		try {
			if (null == user.getEmail() || user.getEmail().isEmpty())
				throw new InvalidArgumentException(Constants.INVALID_EMAIL);
			else if (null == user.getPassword() || user.getPassword().isEmpty())
				throw new InvalidArgumentException(Constants.INVALID_USERNAME);
			else {
				User u = userService.findOne(user.getEmail());
				if (!bCryptPasswordEncoder.matches(user.getPassword(), u.getPassword())) {
					throw new InvalidArgumentException(Constants.INVALID_PASSWORD);
				} else {
					String token = userUtilities.encodeToken(Constants.SECRET, u.getId());
					return gson.toJson(token);
				}
			}
		} catch (IllegalArgumentException e) {
			LOG.error("~~~~~ UTF-8 encoding not supported ~~~~~" + e.getMessage());
			throw new InternalServerException(e.getLocalizedMessage());
		} catch (JWTCreationException ex) {
			LOG.error("~~~~ Claim cannot be converted to JSON in " + this.getClass().getSimpleName() + " ~~~~~"
					+ ex.getLocalizedMessage());
			throw new InternalServerException(ex.getLocalizedMessage());
		}
	}

	@GetMapping(Constants.USER_PROFILE)
	public User profile(@RequestHeader("Token") String token) {
		LOG.info("~~~~~ Inside profile method of " + this.getClass().getSimpleName() + " ~~~~~");
		LOG.info("~~~~~ Token: " + token + " ~~~~~");
		String id = userUtilities.decodeToken(token);
		LOG.info("~~~~~ Id: " + id + " ~~~~~");
		try {
			return userService.findById(id);
		} catch (Exception e) {
			LOG.error("~~~~~ Exception occured in profile method of " + this.getClass().getSimpleName() + " ~~~~~"
					+ e.getLocalizedMessage());
			throw new InternalServerException(e.getLocalizedMessage());
		}
	}
	
	@GetMapping
	public Iterable<User> findAll() {
		LOG.info("~~~~~ Inside findAll method of " + this.getClass().getSimpleName() + " ~~~~~");
		try {
			return userService.findAll();
		} catch (Exception e) {
			LOG.error("~~~~~ Exception occured in findAll method of " + this.getClass().getSimpleName() + " ~~~~~"
					+ e.getLocalizedMessage());
			throw new InternalServerException(e.getLocalizedMessage());
		}
	}
	
	@GetMapping(value = Constants.USER_ID, produces = MediaType.APPLICATION_JSON_VALUE)
	public User findOne(@PathVariable String id) {
		LOG.info("~~~~~ Inside findOne method of " + this.getClass().getSimpleName() + " of id: " + id + " ~~~~~");
		try {
			return userService.findById(id);
		} catch (Exception e) {
			LOG.error("~~~~~ Exception occured in findOne method of " + this.getClass().getSimpleName() + " ~~~~~"
					+ e.getLocalizedMessage());
			throw new InternalServerException(e.getLocalizedMessage());
		}
	}
	
	@PatchMapping(value = Constants.USER_ID, consumes = MediaType.APPLICATION_JSON_VALUE)
	public User update(@PathVariable String id, @RequestBody User user) {
		LOG.info("~~~~~ Inside update method of " + this.getClass().getSimpleName() + " for id: " + id + " ~~~~~");
		try {
			User u = userService.findById(id);
			if (null == u)
				throw new InternalServerException(Constants.NO_ID);
			if (null != user.getFirstName())
				u.setFirstName(user.getFirstName());
			if (null != user.getLastName())
				u.setLastName(user.getLastName());
			if (null != user.getEmail())
				u.setEmail(user.getEmail());
			if (null != user.getDob())
				u.setDob(user.getDob());
			if (null != user.getUsername())
				u.setUsername(user.getUsername());
			if (null != user.getPassword())
				u.setPassword(user.getPassword());
			if (null != user.getRole())
				u.setRole(user.getRole());
			return userService.create(u);
		} catch (Exception e) {
			LOG.error("~~~~~ Exception occured in update method of " + this.getClass().getSimpleName() + " ~~~~~"
					+ e.getLocalizedMessage());
			throw new InternalServerException(e.getLocalizedMessage());
		}
	}
	
	@DeleteMapping(Constants.USER_ID)
	public String delete(@PathVariable String id) {
		LOG.info("~~~~~ Inside delete method of " + this.getClass().getSimpleName() + " for id: " + id + " ~~~~~");
		try {
			return userService.delete(id);
		} catch (Exception e) {
			LOG.error("~~~~~ Exception occured in delete method of " + this.getClass().getSimpleName() + " ~~~~~"
					+ e.getLocalizedMessage());
			throw new InternalServerException(e.getLocalizedMessage());
		}
	}
}
