package com.cupriver.nutrition.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cupriver.nutrition.SecurityConfig;
import com.cupriver.nutrition.dao.util.PageRequestBuilder;
import com.cupriver.nutrition.domain.entity.FoodLog;
import com.cupriver.nutrition.expressionparser.FilterExpressionParser;
import com.cupriver.nutrition.expressionparser.GenericSpecification;
import com.cupriver.nutrition.expressionparser.GenericSpecificationsBuilder;
import com.cupriver.nutrition.service.FoodLogService;
import com.cupriver.nutrition.service.impl.CaloriesServiceImpl;
import com.google.common.base.Preconditions;

/**
 * Rest API end-points to CRUD on Food Log entries.
 * 
 * @author Chandra Prakash (www.cupriver.com).
 */
@RestController
@RequestMapping("/foodlog")
public class FoodLogController {
	
	private static final Logger log = LoggerFactory.getLogger(FoodLogController.class);
	@Autowired
	private FoodLogService foodLogService;

	@Autowired
	private CaloriesServiceImpl caloriesService;

	@GetMapping
	@PostFilter ("hasAnyRole('ADMIN','MANAGER') || authentication.name == filterObject.userName")
	public List<FoodLog> findAll(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer pageSize,
			@RequestParam(defaultValue = "id") String sortBy,
			@RequestParam(defaultValue = "") String filter) {
		log.info("findAll food log pageNo {}, pageSize {},  sortBy {},  filter {}",pageNo, pageSize, sortBy, filter);
		Pageable pageable = PageRequestBuilder.getPageRequest(pageNo, pageSize, sortBy);
		Specification<FoodLog> specification = resolveSpecificationFromInfixExpr(filter);
		return foodLogService.getFoodLogs(specification, pageable);
	}

	@GetMapping(value = "/{id}")
	@PostAuthorize("hasAnyRole('ADMIN','MANAGER') || authentication.name == returnObject.userName")
	public FoodLog findById(@PathVariable("id") long id) {
		log.info("findById food log for id {}", id);
		FoodLog existingFoodLog = foodLogService.findById(id).orElseThrow(() -> new ResourceNotFoundException());
		return existingFoodLog;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@PreAuthorize("hasAnyRole('ADMIN','MANAGER') || #foodLog.userName.equals(authentication.name)")
	public Long create(@RequestBody FoodLog foodLog) {
		log.info("create food log entry {}",foodLog);
		Preconditions.checkNotNull(foodLog);
		foodLog.setId(null);
		if (foodLog.getNumberOfCalories() == null) {
			Double numberOfCalories = caloriesService.calories(foodLog.getText());
			foodLog.setNumberOfCalories(numberOfCalories);
		}
		foodLog = foodLogService.create(foodLog);
		return foodLog.getId();
	}

	@PutMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void update(@PathVariable("id") long id, @RequestBody FoodLog foodLog) {
		log.info("update food log entry  for id{} with {}",id, foodLog);
		Preconditions.checkNotNull(foodLog);
		FoodLog existingFoodLog = foodLogService.findById(id).orElseThrow(() -> new ResourceNotFoundException());
		SecurityConfig.checkAuthorization(existingFoodLog.getUserName());
		existingFoodLog.setDate(foodLog.getDate());
		existingFoodLog.setTime(foodLog.getTime());
		existingFoodLog.setText(existingFoodLog.getText());
		existingFoodLog.setNumberOfCalories(foodLog.getNumberOfCalories());
		if (existingFoodLog.getNumberOfCalories() == null) {
			Double numberOfCalories = caloriesService.calories(foodLog.getText());
			existingFoodLog.setNumberOfCalories(numberOfCalories);
		}
		foodLogService.update(existingFoodLog);
	}

	@DeleteMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable("id") long id) {
		log.info("delete food log for id {}",id);
		FoodLog existingFoodLog = foodLogService.findById(id).orElseThrow(() -> new ResourceNotFoundException());
		SecurityConfig.checkAuthorization(existingFoodLog.getUserName());
		foodLogService.delete(existingFoodLog);
	}

	/**
	 * Create specification from the search query.
	 * @param searchParameters
	 * @return
	 */
	protected Specification<FoodLog> resolveSpecificationFromInfixExpr(String searchParameters) {
		GenericSpecificationsBuilder<FoodLog> specBuilder = new GenericSpecificationsBuilder<>();
		return specBuilder.build(FilterExpressionParser.parse(searchParameters), GenericSpecification<FoodLog>::new);
	}
	

}