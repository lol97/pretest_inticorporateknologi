package com.inticorporateknologi.pretest.user.contact;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

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

import com.inticorporateknologi.pretest.user.UserNotFoundException;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/${version}/userContacts")
@AllArgsConstructor
public class UserContactController {
	private final UserContactService userContactService;
	private final Environment env;
	
	@GetMapping("")
	public CollectionModel<UserContact> getAll() {
		List<UserContact> allUserContacts = userContactService.getAll();
		Link link = linkTo(methodOn(UserContactController.class).getAll()).withSelfRel();
		String apiVersion = env.getProperty("version");
		link = link.withHref(link.getHref().replace("${version}", apiVersion));
		CollectionModel<UserContact> result = CollectionModel.of(allUserContacts, link);
		return result;
	}
	
	@GetMapping("/{id}")
	public EntityModel<UserContact> findById(@PathVariable String id) throws UserNotFoundException{
		UserContact userContact = userContactService.findById(id);
		String apiVersion = env.getProperty("version");
		Link linkDetail = linkTo(methodOn(UserContactController.class).findById(id)).withSelfRel();
		Link linkAll = linkTo(methodOn(UserContactController.class).getAll()).withSelfRel();
		linkDetail = linkDetail.withHref(linkDetail.getHref().replace("${version}", apiVersion));
		linkAll = linkAll.withHref(linkAll.getHref().replace("${version}", apiVersion));
		return EntityModel.of(userContact, //
				linkDetail,
				linkAll);
	}
	
	@PostMapping("")
	public EntityModel<UserContact> storeUser(@RequestBody UserContactParameter param) throws UserNotFoundException {
		UserContact entStored = userContactService.store(param);
		String apiVersion = env.getProperty("version");
		Link linkDetail = linkTo(methodOn(UserContactController.class).findById(entStored.getId())).withSelfRel();
		Link linkAll = linkTo(methodOn(UserContactController.class).getAll()).withSelfRel();
		linkDetail = linkDetail.withHref(linkDetail.getHref().replace("${version}", apiVersion));
		linkAll = linkAll.withHref(linkAll.getHref().replace("${version}", apiVersion));
		return EntityModel.of(entStored, //
				linkDetail,
				linkAll);
	}

}
