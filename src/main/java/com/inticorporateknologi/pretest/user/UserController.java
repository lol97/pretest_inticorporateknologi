package com.inticorporateknologi.pretest.user;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.core.env.Environment;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inticorporateknologi.pretest.user.contact.UserContact;
import com.inticorporateknologi.pretest.user.contact.UserContactNotFoundException;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/${version}/users")
@AllArgsConstructor
public class UserController {
	private final UserService userService;
	private final Environment env;

	@GetMapping("")
	public CollectionModel<User> getAllUser() {
		List<User> allUsers = userService.getAll();
		Link link = linkTo(methodOn(UserController.class).getAllUser()).withSelfRel();
		String apiVersion = env.getProperty("version");
		link = link.withHref(link.getHref().replace("${version}", apiVersion));
		CollectionModel<User> result = CollectionModel.of(allUsers, link);
		return result;
	}
	
	@GetMapping("/{userId}")
	public EntityModel<User> findById(@PathVariable String userId) throws UserNotFoundException{
		User user = userService.findById(userId);
		String apiVersion = env.getProperty("version");
		Link linkDetail = linkTo(methodOn(UserController.class).findById(userId)).withSelfRel();
		Link linkAll = linkTo(methodOn(UserController.class).getAllUser()).withSelfRel();
		linkDetail = linkDetail.withHref(linkDetail.getHref().replace("${version}", apiVersion));
		linkAll = linkAll.withHref(linkAll.getHref().replace("${version}", apiVersion));
		return EntityModel.of(user, //
				linkDetail,
				linkAll);
	}

	@PostMapping
	public EntityModel<User> storeUser(@RequestBody User user) throws UserNotFoundException {
		User userStored = userService.store(user);
		String apiVersion = env.getProperty("version");
		Link linkDetail = linkTo(methodOn(UserController.class).findById(userStored.getId())).withSelfRel();
		Link linkAll = linkTo(methodOn(UserController.class).getAllUser()).withSelfRel();
		linkDetail = linkDetail.withHref(linkDetail.getHref().replace("${version}", apiVersion));
		linkAll = linkAll.withHref(linkAll.getHref().replace("${version}", apiVersion));
		return EntityModel.of(userStored, //
				linkDetail,
				linkAll);
	}

	@GetMapping("/{id}/contacts")
	public List<UserContact> getAllContactsById(@PathVariable User id) throws UserNotFoundException {
		return userService.getAllContactsById(id);
	}

	@PostMapping("/{userId}/addContact")
	public List<UserContact> addContact(@PathVariable String userId, @RequestBody UserContact userContact)
			throws UserNotFoundException {
		return userService.addContact(userId, userContact);
	}

	@PostMapping("/{userId}/removeContact/{userContactId}")
	public List<UserContact> removeContact(@PathVariable String userId, @PathVariable String userContactId)
			throws UserNotFoundException, UserContactNotFoundException {
		return userService.removeContact(userId, userContactId);
	}
}
