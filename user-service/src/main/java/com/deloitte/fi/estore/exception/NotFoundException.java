package com.deloitte.fi.estore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1783093654251643447L;

	public NotFoundException(String message) {
		super(message);
	}

}
