package com.cupriver.nutrition.expressionparser;

/**
 * The token types enumeration.
 * 
 * @author Chandra Prakash (www.cupriver.com)
 */
public enum FilterExpressionTokenType {
	OPEN(4, false), CLOSE(4, false), IDENTIFIER(0, true), AND(2, false), OR(1, false), GT(3, false), LT(3, false),
	EQ(3, false), NE(3, false), NUMERIC_VALUE(0, true), CHARACTER_VALUE(0, true), EOF(-1, true);

	private int precedence;
	private boolean operand;

	private FilterExpressionTokenType(int precedence, boolean operand) {
		this.precedence = precedence;
		this.operand = operand;
	}

	public boolean isOperand() {
		return this.operand;
	}

	public int getPrecedence() {
		return this.precedence;
	}

	public static FilterExpressionTokenType getTokenType(String word) {
		switch (word.toLowerCase()) {
		case "and":
			return AND;
		case "or":
			return OR;
		case "gt":
			return GT;
		case "lt":
			return LT;
		case "eq":
			return EQ;
		case "ne":
			return NE;
		}
		return IDENTIFIER;
	}
}