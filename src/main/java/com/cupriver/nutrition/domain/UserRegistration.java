package com.cupriver.nutrition.domain;

import com.cupriver.nutrition.domain.entity.UserProfile;

/**
 * User Registration information.
 * 
 * @author Chandra Prakash (www.cupriver.com)
 *
 */
public class UserRegistration {

	private UserProfile userProfile;
	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserProfile getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

	@Override
	public String toString() {
		return "UserRegistration [userProfile=" + userProfile + "]";
	}

}