package com.cupriver.nutrition.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.cupriver.nutrition.domain.entity.UserProfile;

/**
 * Interface for generic CRUD operations on a repository for user profile.
 * 
 * @author Chandra Prakash (www.cupriver.com)
 *
 */
@RepositoryRestResource(collectionResourceRel = "userprofile", path = "userprofile")
public interface UserProfileRepository extends PagingAndSortingRepository<UserProfile, String>, JpaSpecificationExecutor<UserProfile> {
}