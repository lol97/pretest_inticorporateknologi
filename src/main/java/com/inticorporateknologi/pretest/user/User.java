package com.inticorporateknologi.pretest.user;

import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inticorporateknologi.pretest.user.contact.UserContact;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class User {
	@Id
	private String id;
	private String name;
	private Integer age;
	
	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	private List<UserContact> userContacts;

	@PrePersist
	private void ensureId() {
		this.setId(UUID.randomUUID().toString());
	}

	public List<UserContact> addContact(UserContact userContact) {
		userContacts.add(userContact);
		return this.getUserContacts();
	}

	public List<UserContact> removeContact(UserContact userContact) {
		userContacts.remove(userContact);
		return this.getUserContacts();
	}
}
