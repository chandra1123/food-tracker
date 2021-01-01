package com.cupriver.nutrition.expressionparser;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import org.springframework.data.jpa.domain.Specification;

/**
 * Builder for Specification.
 * 
 * @author Chandra Prakash (www.cupriver.com)
 */
public class GenericSpecificationsBuilder<U> {

	/**
	 * Builds specification from list of tokens in post-fix order.
	 * @param postFixTokens
	 * @param converter converts a searchCriteris to Specification.
	 * @return
	 */
	@SuppressWarnings("incomplete-switch")
	public Specification<U> build(List<FilterExpressionToken> postFixTokens,
			Function<SearchCriteria, Specification<U>> converter) {

		Deque<Object> stack = new LinkedList<>();
		Deque<Specification<U>> specStack = new LinkedList<>();

		for (FilterExpressionToken t : postFixTokens) {
			System.out.println("in builder" + t);
			FilterExpressionTokenType tokenType = t.tokenType;
			switch (tokenType) {
			case IDENTIFIER:
				stack.push(t.textValue);
				break;
			case NUMERIC_VALUE:
				stack.push(t.numericValue);
				break;
			case CHARACTER_VALUE:
				stack.push(t.textValue);
				break;
			case EQ:
			case NE:
			case GT:
			case LT:
				Object value = stack.pop();
				String key = (String) stack.pop();
				specStack.push(converter.apply(new SearchCriteria(key, tokenType, value)));
				break;
			case AND:
				Specification<U> first = specStack.pop();
				Specification<U> other = specStack.pop();
				specStack.push(Specification.where(first).and(other));
				break;
			case OR:
				other = specStack.pop();
				first = specStack.pop();
				specStack.push(Specification.where(first).or(other));
			}
		}

		if (specStack.isEmpty()) {
			return null;
		}

		return specStack.pop();
	}
}