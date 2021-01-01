package com.cupriver.nutrition.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import com.cupriver.nutrition.domain.entity.FoodLog;

/**
 * Unit Test for FoodLogService.
 * 
 * @author Chandra Prakash (www.cupriver.com)
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FoodLogServiceUnitTest {

	private final String TEST_USER = "user";

	@Autowired
	private FoodLogService foodLogService;

	@Test(expected = Exception.class)
	public void whenCreateNull_ThenException() {
		foodLogService.create(null);
	}

	@Test
	public void whenCreateValid_ThenRecordCreatedAndIdPopulated() {
		FoodLog foodLog = createFoodLog(null, LocalDate.now(), LocalTime.now(), "test food create", 100.0, true,
				TEST_USER);
		assertNotNull(foodLog.getId());
	}

	@Test
	public void whenRecordAdded_thenItIsFetched() {
		List<FoodLog> foodLogs = foodLogService.getFoodLogs(null, Pageable.unpaged());
		int count = foodLogs.size();
		createFoodLog(null, LocalDate.now(), LocalTime.now(), "test record fetch", 100.0, true, TEST_USER);
		foodLogs = foodLogService.getFoodLogs(null, Pageable.unpaged());
		assertEquals(count + 1, foodLogs.size());
	}

	@Test
	public void whenRecordCreatedAndLookedUpById_ThenCorrectRecordReturned() {
		FoodLog foodLog = createFoodLog(null, LocalDate.now(), LocalTime.now(), "test food fetch by id", 100.0, true,
				TEST_USER);
		Long id = foodLog.getId();
		FoodLog newFoodLog = foodLogService.findById(id).get();
		assertNotNull(newFoodLog);
		assertEquals(id, newFoodLog.getId());
	}

	@Test
	public void whenRecordLookedUpByInvalidId_ThenNotFound() {
		Optional<FoodLog> foodLog = foodLogService.findById(Long.MIN_VALUE);
		assertFalse(foodLog.isPresent());
	}

	@Test(expected = Exception.class)
	public void whenUpdateNull_ThenException() {
		foodLogService.update(null);
	}

	@Test
	public void whenRecordUpdatedAndFetched_thenValueUpdated() {
		FoodLog foodLog = createFoodLog(null, LocalDate.now(), LocalTime.now(), "test record update 1", 100.0, true,
				TEST_USER);
		updateFoodLog(foodLog.getId(), LocalDate.now(), LocalTime.now(), "test record update 2", 123.0, true,
				TEST_USER);
		FoodLog newFoodLog = foodLogService.findById(foodLog.getId()).get();
		assertEquals(newFoodLog.getId(), foodLog.getId());
		assertNotEquals(newFoodLog.getText(), foodLog.getText());
	}

	@Test(expected = Exception.class)
	public void whenDeleteNull_ThenException() {
		foodLogService.delete(null);
	}

	@Test
	public void whenRecordDeletedAndFetched_thenNotFound() {
		FoodLog foodLog = createFoodLog(null, LocalDate.now(), LocalTime.now(), "test food delete", 100.0, true,
				TEST_USER);
		Long id = foodLog.getId();
		foodLogService.delete(foodLog);
		Optional<FoodLog> newFoodLog = foodLogService.findById(id);
		assertFalse(newFoodLog.isPresent());
	}

	@Test
	public void whenEntryCreatedWithHighCalorie_ThenOnTracIsFalse() {
		FoodLog foodLog = createFoodLog(null, LocalDate.now(), LocalTime.now(), "test food delete", Double.MAX_VALUE,
				true, TEST_USER);
		assertFalse(foodLog.isOnTrack());
	}

	private FoodLog createFoodLog(Long id, LocalDate date, LocalTime time, String text, Double numberOfCalories,
			boolean onTrack, String userName) {
		FoodLog foodLog = new FoodLog(id, date, time, text, numberOfCalories, onTrack, userName);
		foodLogService.create(foodLog);
		return foodLog;
	}

	private FoodLog updateFoodLog(Long id, LocalDate date, LocalTime time, String text, Double numberOfCalories,
			boolean onTrack, String userName) {
		FoodLog foodLog = new FoodLog(id, date, time, text, numberOfCalories, onTrack, userName);
		foodLogService.update(foodLog);
		return foodLog;
	}
}
