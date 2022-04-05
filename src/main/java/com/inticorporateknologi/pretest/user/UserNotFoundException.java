package com.inticorporateknologi.pretest.user;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.inticorporateknologi.pretest.user.contact.UserContactNotFoundException;

public class UserNotFoundException extends Exception {
	private static final Logger LOGGER = LogManager.getLogger(UserContactNotFoundException.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;

    public UserNotFoundException(String id) {
    	super(String.format("User with id %s was not found", id));
        LOGGER.warn(String.format("User with id %s was not found", id));
    }

    public String getId(){
        return id;
    }

}
