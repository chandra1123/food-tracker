package com.cupriver.nutrition.domain;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Domain class for food details returned from Calories API provider API.
 * 
 * @author Chandra Prakash (www.cupriver.com)
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Food {

	@JsonAlias({"food_name"})
	private String foodName;
	@JsonAlias({"brand_name"})
	private String brandName;
	@JsonAlias({"nf_calories"})
	private double nfCalories;

	public Food() {
	}

	public String getFoodName() {
		return foodName;
	}

	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public double getNfCalories() {
		return nfCalories;
	}

	public void setNfCalories(double nfCalories) {
		this.nfCalories = nfCalories;
	}

	@Override
	public String toString() {
		return "Food [foodName=" + foodName + ", brandName=" + brandName + ", nfCalories=" + nfCalories + "]";
	}
	
}

