package com.cupriver.nutrition.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cupriver.nutrition.dao.FoodLogRepository;
import com.cupriver.nutrition.dao.UserProfileRepository;
import com.cupriver.nutrition.domain.entity.FoodLog;
import com.cupriver.nutrition.service.FoodLogService;

/**
 * Business Logic for Food Log entry CRUD methods.
 * 
 * @author Chandra Prakash (www.cupriver.com)
 */
@Service
@Transactional
public class FoodLogServiceImpl implements FoodLogService{

	@Autowired
	private FoodLogRepository foodLogRepository;

	@Autowired
	private UserProfileRepository userProfileRepository;

	/**
	 * Searches food log entries.
	 * @param specification
	 * @param pageable
	 * @return
	 */
	public List<FoodLog> getFoodLogs(Specification<FoodLog> specification, Pageable pageable) {
		Page<FoodLog> pagedResult = foodLogRepository.findAll(specification, pageable);
		List<FoodLog> result = new ArrayList<>();
		if (pagedResult.hasContent()) {
			result.addAll(pagedResult.getContent());
		}
		return result;
	}

	/**
	 * Gets food log entry by id.
	 * @param id
	 * @return
	 */
	public Optional<FoodLog> findById(long id) {
		return foodLogRepository.findById(id);
	}

	/**
	 * Creates food log entry.
	 * @param foodLog
	 * @return
	 */
	public FoodLog create(FoodLog foodLog) {
		foodLogRepository.save(foodLog);
		updateOnTrack(foodLog.getUserName(), foodLog.getDate());
		return foodLog;
	}

	/**
	 * Updates food log entry.
	 * @param foodLog
	 * @return
	 */
	public FoodLog update(FoodLog foodLog) {
		foodLogRepository.save(foodLog);
		updateOnTrack(foodLog.getUserName(), foodLog.getDate());
		return foodLog;
	}

	/**
	 * Deletes food log entry.
	 * @param id
	 */
	public void delete(FoodLog foodLog) {
		foodLogRepository.delete(foodLog);
		updateOnTrack(foodLog.getUserName(), foodLog.getDate());
	}

	/**
	 * Updates the onTrack flag for food entries.
	 * @param userName
	 * @param date
	 */
	public void updateOnTrack(String userName, LocalDate date) {
		int dailyCaloriesLimit = userProfileRepository.findById(userName).get().getDailyCaloriesLimit();
		List<FoodLog> foodLogs = foodLogRepository.findByUserNameAndDate(userName, date);
		double totalCalories = foodLogs.stream().mapToDouble(o -> o.getNumberOfCalories()).sum();
		boolean onTrack = totalCalories <= dailyCaloriesLimit;
		foodLogs.forEach(o -> o.setOnTrack(onTrack));
		foodLogRepository.saveAll(foodLogs);
	}
}