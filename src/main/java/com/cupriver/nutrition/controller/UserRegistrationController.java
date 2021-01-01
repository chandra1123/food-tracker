package com.cupriver.nutrition.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.cupriver.nutrition.domain.UserRegistration;
import com.cupriver.nutrition.domain.entity.UserProfile;
import com.cupriver.nutrition.service.UserProfileService;
import com.google.common.base.Preconditions;

/**
 * Public Rest API end-point to create an account.
 * @author Chandra Prakash (www.cupriver.com)
 *
 */
@RestController
@RequestMapping("/register")
public class UserRegistrationController {

	private static final Logger log = LoggerFactory.getLogger(UserRegistrationController.class);
	@Autowired
	private UserProfileService userProfileService;

	@Autowired
	UserDetailsManager users;
	
	@Autowired
	public PasswordEncoder passwordEncoder;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UserProfile create(@RequestBody UserRegistration userRegistration) {
		Preconditions.checkNotNull(userRegistration);
		Preconditions.checkNotNull(userRegistration.getUserProfile());
		log.info("create {} ",userRegistration.getUserProfile());
		String userName = userRegistration.getUserProfile().getUserName();
		if (users.userExists(userName)) {
			throw new ResponseStatusException(HttpStatus.CONFLICT,userName + " already exists.");			
		}
		UserProfile userProfile = userRegistration.getUserProfile();
		UserDetails user = User.withUsername(userProfile.getUserName()).password(passwordEncoder.encode(userRegistration.getPassword())).roles("USER")
				.build();

		users.createUser(user);
		userProfileService.create(userRegistration.getUserProfile());
		return userProfile;
	}

}