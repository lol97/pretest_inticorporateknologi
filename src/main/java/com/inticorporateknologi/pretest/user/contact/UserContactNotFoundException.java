package com.inticorporateknologi.pretest.user.contact;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserContactNotFoundException extends RuntimeException {
	private static final Logger LOGGER = LogManager.getLogger(UserContactNotFoundException.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;

    public UserContactNotFoundException(String id) {
        super(String.format("User Contact with id %s was not found", id));
        LOGGER.warn(String.format("User Contact with id %s was not found", id));
    }

    public String getId(){
        return id;
    }
}
