package com.cupriver.nutrition.domain;

import java.util.List;

/**
 * 
 * Domain class for calorie information returned from Calories API provider API.
 * 
 * @author Chandra Prakash (www.cupriver.com)
 *
 */
public class Foods {

	private List<Food> foods;

	public Foods() {
		super();
	}

	public List<Food> getFoods() {
		return foods;
	}

	public void setFoods(List<Food> foods) {
		this.foods = foods;
	}

	public double getCalories() {
		double calories = 0;
		for (Food food : foods) {
			calories += food.getNfCalories();
		}
		return calories;
	}

	@Override
	public String toString() {
		return "Foods [foods=" + foods + "]";
	}

}
