package com.cupriver.nutrition.expressionparser;

import java.time.LocalDate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

/**
 * A generic specification from search criteria.
 * 
 * @author Chandra Prakash (www.cupriver.com)
 */
public class GenericSpecification<U> implements Specification<U> {

	private static final long serialVersionUID = 1L;
	private SearchCriteria criteria;

	public GenericSpecification(final SearchCriteria criteria) {
		super();
		this.criteria = criteria;
	}

	public SearchCriteria getCriteria() {
		return criteria;
	}

	@Override
	public Predicate toPredicate(final Root<U> root, final CriteriaQuery<?> query, final CriteriaBuilder builder) {
		String key = criteria.getKey();
		Object value = criteria.getValue();
		
		// This is required due to LocalDate not being supported in the current version of JPA.
		if ("date".equalsIgnoreCase(key) && value instanceof String) {
			value = LocalDate.parse((CharSequence) value);
		}
		switch (criteria.getOperation()) {
		case EQ:
			return builder.equal(root.get(key), value);
		case NE:
			return builder.notEqual(root.get(key), value);
		case GT:
			return builder.greaterThan(root.get(key), value.toString());
		case LT:
			return builder.lessThan(root.get(key), value.toString());
		default:
			return null;
		}
	}
}
