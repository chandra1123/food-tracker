package com.cupriver.nutrition.expressionparser;

/**
 * Represents an individual search filter.
 * 
 * @author Chandra Prakash (www.cupriver.com)
 */
public class SearchCriteria {

	private String key;
	private FilterExpressionTokenType operation;
	private Object value;

	public SearchCriteria(String key, FilterExpressionTokenType operation, Object value) {
		super();
		this.key = key;
		this.operation = operation;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public FilterExpressionTokenType getOperation() {
		return operation;
	}

	public void setOperation(FilterExpressionTokenType operation) {
		this.operation = operation;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

}
