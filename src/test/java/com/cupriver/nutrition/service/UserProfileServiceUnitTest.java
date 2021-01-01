package com.cupriver.nutrition.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import com.cupriver.nutrition.domain.entity.UserProfile;

/**
 * Unit Test for UserProfileService.
 * 
 * @author Chandra Prakash (www.cupriver.com)
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserProfileServiceUnitTest {

	@Autowired
	private UserProfileService userProfileService;

	@Test(expected = Exception.class)
	public void whenCreateNull_ThenException() {
		userProfileService.create(null);
	}

	@Test
	public void whenCreateValid_ThenRecordCreatedAndIdPopulated() {
		final String userName = "TEST_CREATE_USER";
		UserProfile userProfile = createUserProfile(userName, "FirstName1", "LastName1", 123, 5.5, 65, 2000);
		assertEquals(userName, userProfile.getUserName());
	}

	@Test
	public void whenRecordAdded_thenItIsFetched() {
		final String userName = "TEST_GET_USER";
		List<UserProfile> userProfiles = userProfileService.getUserProfiles(null, Pageable.unpaged());
		int count = userProfiles.size();
		createUserProfile(userName, "FirstName1", "LastName1", 123, 5.5, 65, 2000);
		userProfiles = userProfileService.getUserProfiles(null, Pageable.unpaged());
		assertEquals(count + 1, userProfiles.size());
	}

	@Test
	public void whenRecordCreatedAndLookedUpById_ThenCorrectRecordReturned() {
		final String userName = "TEST_GET_USER_BY_ID";
		createUserProfile(userName, "FirstName1", "LastName1", 123, 5.5, 65, 2000);
		UserProfile userProfile = userProfileService.findById(userName).get();
		assertNotNull(userProfile);
		assertEquals(userName, userProfile.getUserName());
	}

	@Test
	public void whenRecordLookedUpByInvalidId_ThenNotFound() {
		Optional<UserProfile> userProfile = userProfileService.findById("-1");
		assertFalse(userProfile.isPresent());
	}

	@Test(expected = Exception.class)
	public void whenUpdateNull_ThenException() {
		userProfileService.update(null);
	}

	@Test
	public void whenRecordUpdatedAndFetched_thenValueUpdated() {
		final String userName = "TEST_UPDATE_USER";
		UserProfile userProfile = createUserProfile(userName, "FirstName1", "LastName1", 123, 5.5, 65, 2000);
		updateUserProfile(userName, "FirstName2", "LastName2", 132, 6.5, 66, 2001);
		UserProfile updatedUserProfile = userProfileService.findById(userName).get();
		assertEquals(updatedUserProfile.getUserName(), userProfile.getUserName());
		assertNotEquals(updatedUserProfile.getFirstName(), userProfile.getFirstName());
		assertNotEquals(updatedUserProfile.getLastName(), userProfile.getLastName());
		assertNotEquals(updatedUserProfile.getWeight(), userProfile.getWeight());
		assertNotEquals(updatedUserProfile.getHeight(), userProfile.getHeight());
		assertNotEquals(updatedUserProfile.getDailyCaloriesLimit(), userProfile.getDailyCaloriesLimit());
	}

	@Test(expected = Exception.class)
	public void whenDeleteNull_ThenException() {
		userProfileService.deleteById(null);
	}

	@Test
	public void whenRecordDeletedAndFetched_thenNotFound() {
		final String userName = "TEST_DELETE_USER";
		UserProfile userProfile = createUserProfile(userName, "FirstName1", "LastName1", 123, 5.5, 65, 2000);
		assertNotNull(userProfile);
		userProfileService.deleteById(userName);
		Optional<UserProfile> deleteUserProfile = userProfileService.findById(userName);
		assertFalse(deleteUserProfile.isPresent());
	}

	private UserProfile createUserProfile(String userName, String firstName, String lastName, double weight,
			double height, int age, int dailyCaloriesLimit) {
		UserProfile userProfile = new UserProfile(userName, firstName, lastName, weight, height, age,
				dailyCaloriesLimit);
		return userProfileService.create(userProfile);
	}

	private UserProfile updateUserProfile(String userName, String firstName, String lastName, double weight,
			double height, int age, int dailyCaloriesLimit) {
		UserProfile userProfile = new UserProfile(userName, firstName, lastName, weight, height, age,
				dailyCaloriesLimit);
		return userProfileService.update(userProfile);
	}
}
