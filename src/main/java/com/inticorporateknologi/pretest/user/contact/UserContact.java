package com.inticorporateknologi.pretest.user.contact;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import com.inticorporateknologi.pretest.user.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UserContact {
	@Id
	private String id;
	private String address;
	
	@ManyToOne
	@JoinColumn(name = "user")
	private User user;
	
	@PrePersist
	private void ensureId() {
		this.setId(UUID.randomUUID().toString());
	}

}
