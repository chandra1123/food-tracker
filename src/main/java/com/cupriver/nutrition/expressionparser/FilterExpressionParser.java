package com.cupriver.nutrition.expressionparser;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * Filter expression parser.
 * 
 * @author Chandra Prakash (www.cupriver.com)
 */
public class FilterExpressionParser {
	
	/**
	 * Generates the tokens from the expression.
	 * @param query
	 * @return
	 */
	public static List<FilterExpressionToken> tokenize(String query) {
		StreamTokenizer st = new StreamTokenizer(new StringReader(query));
		List<FilterExpressionToken> tokens = new ArrayList<>();
		st.quoteChar('\'');
		st.wordChars('_', '_');
		boolean eof = false;
		do {
			int token = StreamTokenizer.TT_EOF;
			try {
				token = st.nextToken();
			} catch (IOException e) {
				e.printStackTrace();
			}
			switch (token) {
			case StreamTokenizer.TT_EOF:
				// tokens.add(new Token(TokenType.EOF));
				eof = true;
				break;

			case StreamTokenizer.TT_WORD:
				System.out.println("Word: " + st.sval);
				tokens.add(new FilterExpressionToken(FilterExpressionTokenType.getTokenType(st.sval), st.sval));
				break;

			case StreamTokenizer.TT_NUMBER:
				System.out.println("Number: " + st.nval);
				tokens.add(new FilterExpressionToken(FilterExpressionTokenType.NUMERIC_VALUE, st.nval));
				break;

			case '(':
				System.out.println("(");
				tokens.add(new FilterExpressionToken(FilterExpressionTokenType.OPEN));
				break;

			case ')':
				System.out.println(")");
				tokens.add(new FilterExpressionToken(FilterExpressionTokenType.CLOSE));
				break;

			case '\'':
				System.out.println("'" + st.sval);
				tokens.add(new FilterExpressionToken(FilterExpressionTokenType.CHARACTER_VALUE, st.sval));
				break;

			default:
				System.out.println((char) token + " encountered.");
			}

		} while (!eof);

		return tokens;

	}

	/**
	 * Generates tokens in post-fix order from the filter expression.
	 * @param searchParam
	 * @return
	 */
	public static List<FilterExpressionToken> parse(String searchParam) {

		List<FilterExpressionToken> postFixTokens = new ArrayList<>();
		Deque<FilterExpressionToken> stack = new LinkedList<>();

		List<FilterExpressionToken> tokens = tokenize(searchParam);
		tokens.forEach(t -> {
			if (t.tokenType.isOperand()) {
				postFixTokens.add(t);
			} else {
				boolean done = true;
				do {
					done = true;
					if (stack.isEmpty() || stack.peek().tokenType == FilterExpressionTokenType.OPEN) {
						stack.push(t);
					} else if (t.tokenType == FilterExpressionTokenType.OPEN) {
						stack.push(t);
					} else if (t.tokenType == FilterExpressionTokenType.CLOSE) {
						for (FilterExpressionToken operator = stack.pop(); operator.tokenType != FilterExpressionTokenType.OPEN; operator = stack
								.pop()) {
							postFixTokens.add(operator);
						}
					} else if (t.tokenType.getPrecedence() > stack.peek().tokenType.getPrecedence()) {
						stack.push(t);
					} else if (t.tokenType.getPrecedence() == stack.peek().tokenType.getPrecedence()) {
						postFixTokens.add(stack.pop());
						stack.push(t);
					} else if (t.tokenType.getPrecedence() < stack.peek().tokenType.getPrecedence()) {
						postFixTokens.add(stack.pop());
						done = false;
					}
				} while (!done);

			}

		});

		while (!stack.isEmpty()) {
			postFixTokens.add(stack.pop());
		}

		return postFixTokens;
	}

	/**
	 * Main class to test the functionality.
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		final String query = "(date eq '2016-05-01') AND ((number_of_calories gt 20) OR (number_of_calories lt 10))";
		List<FilterExpressionToken> tokens = parse(query);
		tokens.forEach(System.out::println);
	}
}
