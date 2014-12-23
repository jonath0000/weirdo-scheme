package net.tapire_solutions.weirdoscheme;
import java.util.ArrayList;
import java.util.List;


public class ExpressionParser {
	
	public static List<EvalItem> read(String program) throws LispParserException {
		List<String> tokens = tokenize(program);
		List<List<String>> splitted = splitExpressions(tokens);
		List<EvalItem> items = new ArrayList<EvalItem>();
		for (List<String> expressionTokens : splitted) {
			items.add(readFrom(expressionTokens));
		}
		return items;
	}
	
	private static EvalItem readFrom(List<String> tokens) throws LispParserException {
		if (tokens.isEmpty()) {
			throw new LispParserException("Empty expression!");
		}
		String token = tokens.remove(0);
		if (token.equals("(")) {
			ArrayList<EvalItem> evalItems = new ArrayList<EvalItem>();
			while (!tokens.get(0).equals(")")) {
				evalItems.add(readFrom(tokens));
			}
			tokens.remove(0);
			return new EvalItem(evalItems);
		}
		if (token.equals(")")) {
			throw new LispParserException("Unexpected ) !");
		} 
		return atom(token);
	}
	
	private static EvalItem symbol(String token) {
		return new EvalItem(token);
	}
	
	private static EvalItem atom(String token) {
		try {
			Integer.parseInt(token);
			return new EvalItem(token);
		} catch (NumberFormatException e1) {
			try {
				Float.parseFloat(token);
				return new EvalItem(token);
			} catch (NumberFormatException e2) {
				return symbol(token);
			}
		}
	}
	
	public static boolean hasInbalancedParentheses(List<String> tokens) {
		int parenthesesDepth = 0;
		for (int i = 0; i < tokens.size(); i++) {
			if (tokens.get(i).equals("(")) {
				parenthesesDepth++;
			} else if (tokens.get(i).equals(")")) {
				parenthesesDepth--;
			}
			if (parenthesesDepth < 0) return true;
		}
		if (parenthesesDepth == 0) return false;
		return true;
	}
	
	public static List<List<String>> splitExpressions(List<String> tokens) {
		List<List<String>> expressions = new ArrayList<List<String>>();
		List<String> temp = new ArrayList<String>();
		int parenthesesDepth = 0;
		for (int i = 0; i < tokens.size(); i++) {
			temp.add(tokens.get(i));
			if (tokens.get(i).equals("(")) {
				parenthesesDepth++;
			} else if (tokens.get(i).equals(")")) {
				parenthesesDepth--;
				if (parenthesesDepth == 0) {
					expressions.add(temp);
					temp = new ArrayList<String>();
				}
			}
			
		}
		return expressions;
	}
	
	public static List<String> tokenize(String program) {
		program = (program.replaceAll("\\(", " ( ")).replaceAll("\\)", " ) ");
		String[] tokensRaw = program.split(" ");
		ArrayList<String> tokens = new ArrayList<String>();
		for (String token : tokensRaw) {
			if (!token.isEmpty()) {
				token = token.trim();
				tokens.add(token);
			}
		}
		return tokens;
	}
}
