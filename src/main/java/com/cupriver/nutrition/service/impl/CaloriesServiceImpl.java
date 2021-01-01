package com.cupriver.nutrition.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cupriver.nutrition.domain.Foods;
import com.cupriver.nutrition.service.CaloriesService;

/**
 * Adapter to external Calories API provider.
 * 
 * @author Chandra Prakash (www.cupriver.com)
 */
@Service
public class CaloriesServiceImpl implements CaloriesService {

	private static final Logger log = LoggerFactory.getLogger(CaloriesServiceImpl.class);

	@Value("${food.tracker.calorie.api.provider.url}")
	private String apiPrviderUrl;

	@Value("${food.tracker.calorie.api.appId}")
	private String appId;

	@Value("${food.tracker.calorie.api.appKey}")
	private String appKey;

	@Value("${food.tracker.calorie.api.userId}")
	private String userId;

	@Autowired
	private RestTemplate restTemplate;

	
	/**
	 * Gets the number of calories for an entered meal from an external Calories API provider.
	 * 
	 * @param foodQuery
	 * @return
	 */
	public double calories(String foodQuery) {
		Map<String, String> map = new HashMap<>();
		map.put("query", foodQuery);
		HttpEntity<Map<String, String>> entity = new HttpEntity<>(map, getHeader());

		ResponseEntity<Foods> response = restTemplate.postForEntity(apiPrviderUrl, entity, Foods.class);
		log.info(response.toString());
		Foods foods = response.getBody();
		return foods.getCalories();
	}

	private HttpHeaders getHeader() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("x-app-id", appId);
		headers.add("x-app-key", appKey);
		headers.add("x-remote-user-id", userId);
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}

}
