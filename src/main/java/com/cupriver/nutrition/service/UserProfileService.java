package com.cupriver.nutrition.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.cupriver.nutrition.domain.entity.UserProfile;

public interface UserProfileService {
	public List<UserProfile> getUserProfiles(Specification<UserProfile> specification, Pageable pageable);

	public Optional<UserProfile> findById(String userName);

	public UserProfile create(UserProfile userProfile);

	public UserProfile update(UserProfile userProfile);

	public void deleteById(String userName);
}
