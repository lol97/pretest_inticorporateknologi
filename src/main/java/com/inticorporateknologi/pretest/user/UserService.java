package com.inticorporateknologi.pretest.user;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.inticorporateknologi.pretest.user.contact.UserContact;
import com.inticorporateknologi.pretest.user.contact.UserContactRepository;
import com.inticorporateknologi.pretest.user.contact.UserContactNotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class UserService {
	private final UserRepository userRepository;
	private final UserContactRepository userContactRepository;
	
	private final EntityManager entityManager;

	public List<User> getAll() {
		return userRepository.findAll();
	}

	public User store(User user) {
		return userRepository.save(user);
	}

	public List<UserContact> getAllContactsById(User id) throws UserNotFoundException {
		User user = userRepository.findById(id.getId()).orElseThrow(() -> new UserNotFoundException(id.getId()));
		return user.getUserContacts();
	}

	public List<UserContact> addContact(String userId, UserContact userContact) throws UserNotFoundException {
		User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
		userContact.setUser(user);
		userContactRepository.save(userContact);
		entityManager.persist(userContact);
		user.addContact(userContact);
		return user.getUserContacts();

	}

	public List<UserContact> removeContact(String userId, String userContactId)
			throws UserNotFoundException, UserContactNotFoundException {
		User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
		UserContact findUserContract = userContactRepository.findById(userContactId)
				.orElseThrow(() -> new UserContactNotFoundException(userContactId));
		user.removeContact(findUserContract);
		entityManager.remove(findUserContract);
		return user.getUserContacts();
	}

	public User findById(String userId) throws UserNotFoundException{
		return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
	}
}
