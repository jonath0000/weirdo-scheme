package net.tapire_solutions.weirdoscheme;

import java.util.List;


public class ReplApp {
	
	public static String getHelp() {
		String help = "";
		help += "Control flow:\n";
		help += Evaluator.getHelp();
		help += "\nBuilt ins:\n";
		help += Builtins.getHelp();
		return help;
	}
	
	public static String evaluateLine(String line, Environment globalEnv) {
		line = line.replace("\n", " ");
		try {
			List<EvalItem> parsed = ExpressionParser.read(line);
			EvalItem output = null;
			for (EvalItem parsedItem : parsed) {
				output = Evaluator.evaluate(parsedItem, globalEnv); // only last result is returned.
			}
			return output.toString();
		} catch (LispParserException e1) {
			System.out.println("Invalid syntax (" + e1 + ")");
			return "ERROR: invalid syntax (" + e1 +")";
		} catch (LispEvaluatorException e2) {
			System.out.println("Error evaluating (" + e2 + ")");
			return "ERROR: error evaluating (" + e2 +")";
		}
	}
	
}