package net.tapire_solutions.weirdoscheme;


public class ReplApp {
	
	public static String evaluateLine(String line, Environment globalEnv) {
		try {
			EvalItem parsed = ExpressionParser.read(line);
			EvalItem output = Evaluator.evaluate(parsed, globalEnv);
			return output.toString();
		} catch (LispParserException e1) {
			System.out.println("Invalid syntax (" + e1 + ")");
		} catch (LispEvaluatorException e2) {
			System.out.println("Error evaluating (" + e2 + ")");
		}
		return null;
	}
	
}