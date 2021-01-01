package com.cupriver.nutrition.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.cupriver.nutrition.domain.entity.FoodLog;

public interface FoodLogService {
	public List<FoodLog> getFoodLogs(Specification<FoodLog> specification, Pageable pageable);

	public Optional<FoodLog> findById(long id);

	public FoodLog create(FoodLog foodLog);

	public FoodLog update(FoodLog foodLog);

	public void delete(FoodLog foodLog);

	public void updateOnTrack(String userName, LocalDate date);
}
