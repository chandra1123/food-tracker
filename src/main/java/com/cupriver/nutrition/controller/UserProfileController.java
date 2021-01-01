package com.cupriver.nutrition.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cupriver.nutrition.dao.util.PageRequestBuilder;
import com.cupriver.nutrition.domain.entity.UserProfile;
import com.cupriver.nutrition.expressionparser.FilterExpressionParser;
import com.cupriver.nutrition.expressionparser.GenericSpecification;
import com.cupriver.nutrition.expressionparser.GenericSpecificationsBuilder;
import com.cupriver.nutrition.service.UserProfileService;
import com.google.common.base.Preconditions;

/**
 * Rest API end-points to CRUD user accounts.
 *  
 * @author Chandra Prakash (www.cupriver.com)
 *
 */
/**
 * @author Chandra Prakash (www.cupriver.com)
 *
 */
@RestController
@RequestMapping("/userprofile")
public class UserProfileController {
	
	private static final Logger log = LoggerFactory.getLogger(UserProfileController.class);

	@Autowired
	private UserProfileService userProfileService;

	@GetMapping
	@PostFilter ("hasAnyRole('ADMIN','MANAGER') || authentication.name == filterObject.userName")
	public List<UserProfile> findAll( @RequestParam(defaultValue = "0") Integer pageNo, 
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "userName") String sortBy,
            @RequestParam(defaultValue = "") String filter) {
		log.info("findAll user profile pageNo {}, pageSize {},  sortBy {},  filter {}",pageNo, pageSize, sortBy, filter);
		Pageable pageable = PageRequestBuilder.getPageRequest(pageNo, pageSize, sortBy);
		Specification<UserProfile> specification = resolveSpecificationFromInfixExpr(filter);
		return userProfileService.getUserProfiles(specification, pageable);		
	}

	@GetMapping(value = "/{userName}")
	@PreAuthorize("hasAnyRole('ADMIN','MANAGER') || #userName == authentication.name")
	public UserProfile findById(@PathVariable("userName") String userName) {
		log.info("findById user profile for userName {}",userName);
		return userProfileService.findById(userName).orElseThrow(() -> new ResourceNotFoundException());
	}


	@PutMapping(value = "/{userName}")
	@PreAuthorize("hasAnyRole('ADMIN','MANAGER') || #userName == authentication.name")
	@ResponseStatus(HttpStatus.OK)
	public void update(@PathVariable("userName") String userName, @RequestBody UserProfile userProfile) {
		log.info("update user profile for userName {}, userProfile {}",userName, userProfile);
		Preconditions.checkNotNull(userProfile);
		Preconditions.checkArgument(userName.equals(userProfile.getUserName()));
		UserProfile existingUserProfile = userProfileService.findById(userName).orElseThrow(() -> new ResourceNotFoundException());
		existingUserProfile.setFirstName(userProfile.getFirstName());
		existingUserProfile.setLastName(userProfile.getLastName());
		existingUserProfile.setAge(userProfile.getAge());
		existingUserProfile.setHeight(userProfile.getHeight());
		existingUserProfile.setWeight(userProfile.getWeight());
		existingUserProfile.setDailyCaloriesLimit(userProfile.getDailyCaloriesLimit());
		userProfileService.update(existingUserProfile);
	}

	@DeleteMapping(value = "/{userName}")
	@PreAuthorize("hasAnyRole('ADMIN','MANAGER') || #userName == authentication.name")
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable("userName") String userName) {
		log.info("delete user profile for userName {}",userName);
		userProfileService.findById(userName).orElseThrow(() -> new ResourceNotFoundException());
		userProfileService.deleteById(userName);
	}
	
	
	/**
	 * Create specification from the search query.े
	 * @param searchParameters
	 * @return
	 */
	protected Specification<UserProfile> resolveSpecificationFromInfixExpr(String searchParameters) {
	    GenericSpecificationsBuilder<UserProfile> specBuilder = new GenericSpecificationsBuilder<>();
	    return specBuilder.build(FilterExpressionParser.parse(searchParameters), GenericSpecification<UserProfile>::new);
	}

}