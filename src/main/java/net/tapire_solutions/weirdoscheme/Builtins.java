package net.tapire_solutions.weirdoscheme;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Builtins {
	
	private interface BuiltIn {
		EvalItem call(String name, ArrayList<EvalItem> args, Environment env);
		String getSymbol();
		String getHelp();
	}
	
	private static class PlusBuiltIn implements BuiltIn {
		@Override
		public EvalItem call(String name, ArrayList<EvalItem> args, Environment env) {
			int value = 0;
			for (EvalItem arg : args) {
				value += Integer.parseInt(arg.getValue());
			}
			return new EvalItem(new Integer(value).toString());
		}
		@Override
		public String getSymbol() {
			return "+";
		}
		@Override
		public String getHelp() {
			return "Syntax: (+ arg0 arg1 ...)";
		}
	}
	
	private static class MinusBuiltIn implements BuiltIn {
		@Override
		public EvalItem call(String name, ArrayList<EvalItem> args, Environment env) {
			int value = Integer.parseInt(args.get(0).getValue());
			for (int i = 1; i < args.size(); i++) {
				EvalItem arg = args.get(i);
				value -= Integer.parseInt(arg.getValue());
			}
			return new EvalItem(new Integer(value).toString());
		}
		@Override
		public String getSymbol() {
			return "-";
		}
		@Override
		public String getHelp() {
			return "Syntax: (- arg0 arg1 ...)";
		}
	}
	
	private static class MultiplyBuiltIn implements BuiltIn {
		@Override
		public EvalItem call(String name, ArrayList<EvalItem> args, Environment env) {
			int value = 1;
			for (EvalItem arg : args) {
				value *= Integer.parseInt(arg.getValue());
			}
			return new EvalItem(new Integer(value).toString());
		}
		@Override
		public String getSymbol() {
			return "*";
		}
		@Override
		public String getHelp() {
			return "Syntax: (* arg0 arg1 ...)";
		}
	}
	
	private static class DivideBuiltIn implements BuiltIn {
		@Override
		public EvalItem call(String name, ArrayList<EvalItem> args, Environment env) {
			int value = Integer.parseInt(args.get(0).getValue());
			for (int i = 1; i < args.size(); i++) {
				EvalItem arg = args.get(i);
				value /= Integer.parseInt(arg.getValue());
			}
			return new EvalItem(new Integer(value).toString());
		}
		@Override
		public String getSymbol() {
			return "/";
		}
		@Override
		public String getHelp() {
			return "Syntax: (/ arg0 arg1 ...)";
		}
	}
	
	private static class TestEqualBuiltIn implements BuiltIn {
		@Override
		public EvalItem call(String name, ArrayList<EvalItem> args, Environment env) {
			EvalItem first = args.get(0);
			for (EvalItem arg : args) {
				if (!first.getValue().equals(arg.getValue())) {
					return new EvalItem("false");
				}
			}
			return new EvalItem("true");
		}
		@Override
		public String getSymbol() {
			return "=";
		}
		@Override
		public String getHelp() {
			return "Syntax: (= arg0 arg1 ...)";
		}
	}
	
	private static class TestNotEqualBuiltIn implements BuiltIn {
		@Override
		public EvalItem call(String name, ArrayList<EvalItem> args, Environment env) {
			EvalItem first = args.get(0);
			for (EvalItem arg : args) {
				if (!first.getValue().equals(arg.getValue())) {
					return new EvalItem("true");
				}
			}
			return new EvalItem("false");
		}
		@Override
		public String getSymbol() {
			return "!=";
		}
		@Override
		public String getHelp() {
			return "Syntax: (!= arg0 arg1 ...)";
		}
	}
	
	private static class PrintEnvBuiltIn implements BuiltIn {
		@Override
		public EvalItem call(String name, ArrayList<EvalItem> args, Environment env) {
			System.out.println(env);
			return new EvalItem(new ArrayList<EvalItem>());
		}
		@Override
		public String getSymbol() {
			return "printenv";
		}
		@Override
		public String getHelp() {
			return "Syntax: (printenv) Prints current environment.";
		}
	}
	
	private static class PrintBuiltIn implements BuiltIn {
		@Override
		public EvalItem call(String name, ArrayList<EvalItem> args, Environment env) {
			System.out.println(args);
			return new EvalItem(new ArrayList<EvalItem>());
		}
		@Override
		public String getSymbol() {
			return "printenv";
		}
		@Override
		public String getHelp() {
			return "Syntax: (print arg0 arg1 ...) Prints to standard text out.";
		}
	}
	
	private static class ListFirstBuiltIn implements BuiltIn {
		@Override
		public EvalItem call(String name, ArrayList<EvalItem> args, Environment env) {
			if (args.isEmpty() || !args.get(0).isList() || 
					args.get(0).getList().isEmpty()) return new EvalItem(new ArrayList<EvalItem>());
			return args.get(0).getList().get(0);
		}
		@Override
		public String getSymbol() {
			return "first";
		}
		@Override
		public String getHelp() {
			return "Syntax: (first list) Returns first element in list or empty list.";
		}
	}
	
	private static class ListSecondBuiltIn implements BuiltIn {
		@Override
		public EvalItem call(String name, ArrayList<EvalItem> args, Environment env) {
			if (args.isEmpty() || !args.get(0).isList() || 
					args.get(0).getList().size() < 2) return new EvalItem(new ArrayList<EvalItem>());
			return args.get(0).getList().get(1);
		}
		@Override
		public String getSymbol() {
			return "second";
		}
		@Override
		public String getHelp() {
			return "Syntax: (second list) Returns second element in list or empty list.";
		}
	}
	
	private static class ListRestBuiltIn implements BuiltIn {
		@Override
		public EvalItem call(String name, ArrayList<EvalItem> args, Environment env) {
			if (args.isEmpty() || !args.get(0).isList() || 
					args.get(0).getList().size() < 2) return new EvalItem(new ArrayList<EvalItem>());
			if (args.get(0).getList().size() == 2) return args.get(0).getList().get(1);
			return new EvalItem(args.get(0).getList().subList(1, args.get(0).getList().size()));
		}
		@Override
		public String getSymbol() {
			return "rest";
		}
		@Override
		public String getHelp() {
			return "Syntax: (rest list) Returns list elements second, third... etc or empty list.";
		}
	}
	
	private static class MapBuiltIn implements BuiltIn {
		@Override
		public EvalItem call(String name, ArrayList<EvalItem> args, Environment env) {
			if (args.size() < 2) return new EvalItem(new ArrayList<EvalItem>());
			if (!args.get(0).isLambda()) return new EvalItem(new ArrayList<EvalItem>());
			
			List<EvalItem> in = new ArrayList<EvalItem>();
			if (args.get(1).isList()) {
				in = args.get(1).getList();
			}
			if (args.get(1).isValue()) {
				in.add(args.get(1));
			}
			
			List<EvalItem> out = new ArrayList<EvalItem>();
			for (EvalItem item : in) {
				try {
					List<EvalItem> mapped = new ArrayList<EvalItem>();
					mapped.add(args.get(0));
					mapped.add(item);
					out.add(Evaluator.evaluate(new EvalItem(mapped), env));
				} catch (LispEvaluatorException e1) {
					System.out.println(e1.getMessage());
				}
			}
			return new EvalItem(out);
		}
		@Override
		public String getSymbol() {
			return "map";
		}
		@Override
		public String getHelp() {
			return "Syntax: (map lambda list) Applies lambda with element in list as first argument.";
		}
	}
	
	private static final Map<String, BuiltIn> builtIns;
	static {
		builtIns = new HashMap<String, BuiltIn>();
		BuiltIn plus = new PlusBuiltIn();
		builtIns.put(plus.getSymbol(), plus);
		BuiltIn minus = new MinusBuiltIn();
		builtIns.put(minus.getSymbol(), minus);
		BuiltIn multiply = new MultiplyBuiltIn();
		builtIns.put(multiply.getSymbol(), multiply);
		BuiltIn divide = new DivideBuiltIn();
		builtIns.put(divide.getSymbol(), divide);
		BuiltIn equal = new TestEqualBuiltIn();
		builtIns.put(equal.getSymbol(), equal);
		BuiltIn notEqual = new TestNotEqualBuiltIn();
		builtIns.put(notEqual.getSymbol(), notEqual);
		BuiltIn printEnv = new PrintEnvBuiltIn();
		builtIns.put(printEnv.getSymbol(), printEnv);
		BuiltIn print = new PrintBuiltIn();
		builtIns.put(print.getSymbol(), print);
		
		BuiltIn first = new ListFirstBuiltIn();
		builtIns.put(first.getSymbol(), first);
		BuiltIn second = new ListSecondBuiltIn();
		builtIns.put(second.getSymbol(), second);
		BuiltIn rest = new ListRestBuiltIn();
		builtIns.put(rest.getSymbol(), rest);
		BuiltIn map = new MapBuiltIn();
		builtIns.put(map.getSymbol(), map);
	}
	
	public static String getHelp() {
		String s = "";
		for (Map.Entry<String, BuiltIn> entry : builtIns.entrySet()) {
			s += entry.getValue().getHelp() + "\n";
		}
		return s;
	}
	
	public static EvalItem callBuiltin(String name, ArrayList<EvalItem> args, Environment env) {
		BuiltIn builtIn = builtIns.get(name);
		if (builtIn == null) return null;
		return builtIn.call(name, args, env);
	}
}
