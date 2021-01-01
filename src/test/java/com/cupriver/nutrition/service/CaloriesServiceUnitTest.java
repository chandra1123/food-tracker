
package com.cupriver.nutrition.service;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Unit Test for CaloriesService.
 * 
 * @author Chandra Prakash (www.cupriver.com)
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CaloriesServiceUnitTest {

	@Autowired
	CaloriesService caloriesService;

	/**
	 * Test method for
	 * {@link com.cupriver.nutrition.service.impl.CaloriesServiceImpl#calories(java.lang.String)}.
	 */
	@Test
	public void whenCalledWithFood_thenCalorieReturned() {
		String foodQuery = "2 brown bread with butter";
		double calories = caloriesService.calories(foodQuery);
		assertTrue(calories > 0);
	}

	@Test(expected =Exception.class)
	public void whenCalledWithInvalidQuery_thenExceptionThrown() {
		String foodQuery = null;
		caloriesService.calories(foodQuery);
	}

}
