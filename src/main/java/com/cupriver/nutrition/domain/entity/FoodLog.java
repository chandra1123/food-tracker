package com.cupriver.nutrition.domain.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

/**
 * Entity class for Food Log entry information.
 * 
 * @author Chandra Prakash (www.cupriver.com)
 *
 */
@Entity
public class FoodLog {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "food_log_seq_gen")
	@SequenceGenerator(name = "food_log_seq_gen", sequenceName = "FOOD_LOG_SEQ", allocationSize = 1)
	private Long id;

	private LocalDate date;
	private LocalTime time;
	private String text;
	private Double numberOfCalories;
	private boolean onTrack;
	
	public FoodLog(Long id, LocalDate date, LocalTime time, String text, Double numberOfCalories, boolean onTrack,
			String userName) {
		super();
		this.id = id;
		this.date = date;
		this.time = time;
		this.text = text;
		this.numberOfCalories = numberOfCalories;
		this.onTrack = onTrack;
		this.userName = userName;
	}

	public FoodLog() {
		super();
	}

	private String userName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Double getNumberOfCalories() {
		return numberOfCalories;
	}

	public void setNumberOfCalories(Double numberOfCalories) {
		this.numberOfCalories = numberOfCalories;
	}

	public boolean isOnTrack() {
		return onTrack;
	}

	public void setOnTrack(boolean onTrack) {
		this.onTrack = onTrack;
	}

	@Override
	public String toString() {
		return "FoodLog [id=" + id + ", date=" + date + ", time=" + time + ", text=" + text + ", numberOfCalories="
				+ numberOfCalories + ", onTrack=" + onTrack + ", userName=" + userName + "]";
	}

}