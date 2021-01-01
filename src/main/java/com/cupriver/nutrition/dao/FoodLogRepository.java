package com.cupriver.nutrition.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cupriver.nutrition.domain.entity.FoodLog;

/**
 * Interface for generic CRUD operations on a repository for Food Log entries.
 * 
 * @author Chandra Prakash (www.cupriver.com)
 *
 */
@Repository
public interface FoodLogRepository extends PagingAndSortingRepository<FoodLog, Long>, JpaSpecificationExecutor<FoodLog> {
	public List<FoodLog> findByUserNameAndDate(@Param("userName") String userName, @Param("date") LocalDate date);

}