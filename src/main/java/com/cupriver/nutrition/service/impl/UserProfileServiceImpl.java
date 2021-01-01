package com.cupriver.nutrition.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cupriver.nutrition.dao.UserProfileRepository;
import com.cupriver.nutrition.domain.entity.UserProfile;
import com.cupriver.nutrition.service.UserProfileService;

/**
 * Business Logic for User Profile CRUD methods.
 * 
 * @author Chandra Prakash (www.cupriver.com)
 */
@Service
@Transactional
public class UserProfileServiceImpl implements UserProfileService {

	@Autowired
	private UserProfileRepository userProfileRepository;

	/**
	 * Search user profiles.
	 * 
	 * @param specification
	 * @param pageable
	 * @return
	 */
	public List<UserProfile> getUserProfiles(Specification<UserProfile> specification, Pageable pageable) {
		Page<UserProfile> pagedResult = userProfileRepository.findAll(specification, pageable);
		List<UserProfile> result = new ArrayList<>();
		if (pagedResult.hasContent()) {
			result.addAll(pagedResult.getContent());
		}
		return result;
	}

	/**
	 * Get user profile by ID.
	 * 
	 * @param userName
	 * @return
	 */
	public Optional<UserProfile> findById(String userName) {
		return userProfileRepository.findById(userName);
	}

	/**
	 * Create user profile.
	 * 
	 * @param userProfile
	 * @return
	 */
	public UserProfile create(UserProfile userProfile) {
		userProfile = userProfileRepository.save(userProfile);
		return userProfile;
	}

	/**
	 * Update user profile.
	 * 
	 * @param userProfile
	 * @return
	 */
	public UserProfile update(UserProfile userProfile) {
		userProfile = userProfileRepository.save(userProfile);
		return userProfile;
	}

	/**
	 * Delete user profile.
	 * 
	 * @param userName
	 */
	public void deleteById(String userName) {
		userProfileRepository.deleteById(userName);
	}

}
