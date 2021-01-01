package com.cupriver.nutrition.dao.util;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

/**
 * Helper class to create the PageRequest from the different parameters.
 * 
 * @author Chandra Prakash (www.cupriver.com)
 *
 */
public class PageRequestBuilder {


	public static PageRequest getPageRequest(Integer pageNumber, Integer pageSize,  String sortBy) {

		Set<String> sortingFileds = new LinkedHashSet<>(Arrays.asList(sortBy.split(",")));

		List<Order> sortingOrders = sortingFileds.stream().map(PageRequestBuilder::getOrder)
				.collect(Collectors.toList());

		return PageRequest.of(pageNumber, pageSize, Sort.by(sortingOrders));
	}

	private static Order getOrder(String value) {
		if (value.startsWith("-")) {
			return new Order(Direction.DESC, value.substring(1, value.length()));
		} else if (value.startsWith("+")) {
			return new Order(Direction.ASC, value.substring(1, value.length()));
		} else {
			return new Order(Direction.ASC, value.trim());
		}
	}

}
