package com.inticorporateknologi.pretest.user.contact;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserContactParameter {
	private String id;
	private String address;
	private String userId;
	public UserContactParameter(String address, String userId) {
		super();
		this.address = address;
		this.userId = userId;
	}
	
}
