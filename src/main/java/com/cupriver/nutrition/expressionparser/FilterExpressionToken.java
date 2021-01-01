package com.cupriver.nutrition.expressionparser;

/**
 * The tokens in filter expression.
 * 
 * @author Chandra Prakash (www.cupriver.com)
 */
class FilterExpressionToken {
	@Override
	public String toString() {
		return "Token [tokenType=" + tokenType + ", textValue=" + textValue + ", numericValue=" + numericValue + "]";
	}

	FilterExpressionTokenType tokenType;
	String textValue;
	Number numericValue;

	public FilterExpressionToken(FilterExpressionTokenType tokenType) {
		this.tokenType = tokenType;
	}

	public FilterExpressionToken(FilterExpressionTokenType tokenType, String textValue) {
		this.tokenType = tokenType;
		this.textValue = textValue;
	}

	public FilterExpressionToken(FilterExpressionTokenType tokenType, Number numericValue) {
		this.tokenType = tokenType;
		this.numericValue = numericValue;
	}
}