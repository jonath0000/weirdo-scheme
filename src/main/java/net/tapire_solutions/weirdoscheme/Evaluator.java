package net.tapire_solutions.weirdoscheme;
import java.util.ArrayList;
import java.util.List;


public class Evaluator {
	
	public static final int maxRecursion = 100;
	
	public static boolean verbose = false;
	
	public static String getHelp() {
		String s = "";
		s += "Syntax: (quote arg0, arg1) Return (arg0 arg1 ...)\n";
		s += "Syntax: (if (test) (conseq) (alt)) Execute conseq if test true, else execute alt.\n";
		s += "Syntax: (set! var arg0 arg1 ...) If var is defined anywhere, set it to evaluation of (arg0 arg1 ...)\n";
		s += "Syntax: (define var arg0 arg1 ...) Define var in current inner environment, to evaluation of (arg0 arg1 ...)\n";
		s += "Syntax: (lambda (arg0 arg1 ...) (execute)) Return lambda that can be called eg. (LAMBDA-NAME 3 arg1)\n";
		s += "Syntax: (begin (list0) (list1) ...) Execute each list in order.\n";
		return s;
	}
	
	public static EvalItem evaluate(EvalItem evalItem, Environment env) throws LispEvaluatorException {
		
		int loops = 0;
		while (true) {
			
			loops++;
			if (loops > maxRecursion) {
				throw new LispEvaluatorException("Max recursion reached (" + loops + ")");
			}
			
			if (evalItem.isValue()) {
				Environment envWithVar = env.findEnvironmentWithVar(evalItem.getValue());
				if (envWithVar == null) {
					return evalItem;
				} else {
					return envWithVar.findValueOfVar(evalItem.getValue());
				}
			}
			
			if (evalItem.isList()) {
				EvalItem first = evalItem.getList().get(0);
				if (first.isValue()) {
					String keyword = first.getValue();
					List<EvalItem> list = evalItem.getList();
					
					if (keyword.equals("quote")) {
						EvalItem ret = new EvalItem(list.subList(1, list.size()));
						if (ret.isList() && ret.getList().size() <= 1) {
							ret = ret.getList().get(0);
						}
						return ret;
					}
	
					if (keyword.equals("if")) {
						EvalItem test = list.get(1);
						EvalItem conseq = list.get(2);
						EvalItem alt = list.get(3);
						EvalItem testResult = evaluate(test, env);
						if ((testResult.isList() && testResult.getList().size() > 0)
								|| testResult.isValue() && testResult.getValue().equals("true")) {
							return evaluate(conseq, env);
						} else {
							return evaluate(alt, env);
						}
					}
	
					if (keyword.equals("set!")) {
						String var = list.get(1).getValue();
						Environment envWithVar = env.findEnvironmentWithVar(var);
						EvalItem itemToAdd;
						if (list.size() > 3) {
							itemToAdd = evaluate(new EvalItem(list.subList(2, list.size())), env);
						} else {
							itemToAdd = evaluate(list.get(2), env);
						}
						if (envWithVar != null) {
							envWithVar.add(var, itemToAdd);
						}
						return new EvalItem(new ArrayList<EvalItem>());
					}
	
					if (keyword.equals("define")) {
						String var = list.get(1).getValue();
						EvalItem itemToAdd;
						if (list.size() > 3) {
							itemToAdd = evaluate(new EvalItem(list.subList(2, list.size())), env);
						} else {
							itemToAdd = evaluate(list.get(2), env);
						}
						env.add(var, itemToAdd);
						return new EvalItem(new ArrayList<EvalItem>());
					}
					
					if (keyword.equals("lambda")) {
						EvalItem params = list.get(1);
						EvalItem body = list.get(2);
						return new EvalItem(params, body, env);
					}
					
					if (keyword.equals("begin")) {
						EvalItem ret = null;
						for (EvalItem item : list) {
							ret = evaluate(item, env);
						}
						return ret;
					}
					
					// keyword treated as function name.
					EvalItem proc = evaluate(first, env);
					ArrayList<EvalItem> args = new ArrayList<EvalItem>();
					for (int i = 1; i < list.size(); i++) {
						EvalItem item = list.get(i);
						args.add(evaluate(item, env));
					}
					if (proc.isLambda()) {
						evalItem = proc.getBody();
						env = new Environment(env);
						for (int i = 0; i < proc.getParams().getList().size(); i++) {
							env.add(proc.getParams().getList().get(i).getValue(), args.get(i));
						}
						if (verbose) {
							System.out.println("Calling lambda " + first.getValue() + " with env=" + env);
						}
					} else {
						if (verbose) {
							System.out.println("Calling builtin " + proc.getValue() + " with args=" + args);
						}
						EvalItem returnVal = Builtins.callBuiltin(proc.getValue(), args, env);
						if (returnVal == null) {
							throw new LispEvaluatorException("Undefined procedure " + proc.getValue());
						} else {
							return returnVal;
						}
					}
				}
			}
		}
	}
}
