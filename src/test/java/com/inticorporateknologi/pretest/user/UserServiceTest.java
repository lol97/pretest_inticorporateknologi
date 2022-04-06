package com.inticorporateknologi.pretest.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.inticorporateknologi.pretest.user.contact.UserContact;
import com.inticorporateknologi.pretest.user.contact.UserContactRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
	@Mock
	private UserRepository userRepository;
	@Mock
	private UserContactRepository userContactRepository;
	private EntityManager entityManager;
	private UserService underTest;

	@BeforeEach
	void setUp() {
		underTest = new UserService(userRepository, userContactRepository, entityManager);
	}

	@Test
	void canGetAll() {
		// when
		underTest.getAll();
		// then
		verify(userRepository).findAll();
	}

	@Test
	void canStore() {
		// given
		List<UserContact> contacts = new ArrayList<UserContact>();
		User user = new User("Test1", "Doni Tata", 20, contacts);

		// when
		underTest.store(user);

		// then
		ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
		verify(userRepository).save(userArgumentCaptor.capture());
		User capturedUser = userArgumentCaptor.getValue();

		assertThat(capturedUser.getName()).isEqualTo(user.getName());
	}

	@Test
	void canGetAllContactsById() throws UserNotFoundException {
		// given
		List<UserContact> contacts = new ArrayList<UserContact>();
		User user = new User("Test1", "Doni Tata", 20, contacts);

		given(userRepository.findById(user.getId())).willReturn(Optional.of(user));

		// when
		List<UserContact> allContactsById = underTest.getAllContactsById(user.getId());
		// then
		verify(userRepository).findById(user.getId());

		assertThat(allContactsById).isEqualTo(contacts);

	}

	@Test
	void throwGetAllContactsById() throws UserNotFoundException {
		// given
		List<UserContact> contacts = new ArrayList<UserContact>();
		User user = new User("Test1", "Doni Tata", 20, contacts);

		given(userRepository.findById(user.getId())).willReturn(Optional.ofNullable(null));

		// when
		// then
		assertThatThrownBy(() -> underTest.getAllContactsById(user.getId())).isInstanceOf(UserNotFoundException.class)
				.hasMessageContaining(String.format("User with id %s was not found", user.getId()));

	}
}
