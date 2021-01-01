package com.cupriver.nutrition.domain.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Entity class for  User Profile information.
 * 
 * @author Chandra Prakash (www.cupriver.com)
 *
 */
@Entity
public class UserProfile {

	public UserProfile() {
		super();
	}

	public UserProfile(String userName, String firstName, String lastName, double weight, double height, int age,
			int dailyCaloriesLimit) {
		super();
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.weight = weight;
		this.height = height;
		this.age = age;
		this.dailyCaloriesLimit = dailyCaloriesLimit;
	}

	@Id
	private String userName;

	private String firstName;
	private String lastName;
	private double weight;
	private double height;
	private int age;
	private int dailyCaloriesLimit;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getDailyCaloriesLimit() {
		return dailyCaloriesLimit;
	}

	public void setDailyCaloriesLimit(int dailyCaloriesLimit) {
		this.dailyCaloriesLimit = dailyCaloriesLimit;
	}

	@Override
	public String toString() {
		return "UserProfile [userName=" + userName + ", firstName=" + firstName + ", lastName=" + lastName + ", weight="
				+ weight + ", height=" + height + ", age=" + age + ", dailyCaloriesLimit=" + dailyCaloriesLimit + "]";
	}

}