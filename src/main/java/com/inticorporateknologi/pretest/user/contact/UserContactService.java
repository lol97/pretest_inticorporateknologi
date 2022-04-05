package com.inticorporateknologi.pretest.user.contact;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.inticorporateknologi.pretest.user.User;
import com.inticorporateknologi.pretest.user.UserNotFoundException;
import com.inticorporateknologi.pretest.user.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class UserContactService {
	private final UserContactRepository userContactRepository;
	private final UserRepository userRepository;
	private final EntityManager entityManager;

	public List<UserContact> getAll() {
		return userContactRepository.findAll();
	}

	public UserContact findById(String id) throws UserContactNotFoundException {
		UserContact findUserContract = userContactRepository.findById(id)
				.orElseThrow(() -> new UserContactNotFoundException(id));
		return findUserContract;
	}

	public UserContact store(UserContactParameter param) throws UserNotFoundException{
		String userId = param.getUserId();
		User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
		UserContact stored = new UserContact();
		stored.setAddress(param.getAddress());
		stored.setUser(user);
		userContactRepository.save(stored);
		entityManager.persist(stored);
		user.addContact(stored);
		return stored;
	}

}
