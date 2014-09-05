import java.util.ArrayList;


public class Evaluator {
	public static EvalItem evaluate(EvalItem evalItem, Environment env) throws Exception {
		
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
				ArrayList<EvalItem> list = evalItem.getList();
				list.remove(0);
				
				if (keyword.equals("quote")) {
					return new EvalItem(list);
				}

				if (keyword.equals("if")) {
					EvalItem test = list.remove(0);
					EvalItem conseq = list.remove(0);
					EvalItem alt = list.remove(0);
					EvalItem testResult = evaluate(test, env);
					if (testResult.isList() && testResult.getList().size() > 0) {
						return evaluate(conseq, env);
					} else {
						return evaluate(alt, env);
					}
				}

				if (keyword.equals("set!")) {
					String var = list.remove(0).getValue();
					Environment envWithVar = env.findEnvironmentWithVar(var);
					EvalItem itemToAdd;
					if (list.size() > 1) {
						itemToAdd = evaluate(new EvalItem(list), env);
					} else {
						itemToAdd = evaluate(list.remove(0), env);
					}
					if (envWithVar != null) {
						envWithVar.add(var, itemToAdd);
					}
					return new EvalItem(new ArrayList<EvalItem>());
				}

				if (keyword.equals("define")) {
					String var = list.remove(0).getValue();
					EvalItem itemToAdd;
					if (list.size() > 1) {
						itemToAdd = evaluate(new EvalItem(list), env);
					} else {
						itemToAdd = evaluate(list.remove(0), env);
					}
					env.add(var, itemToAdd);
					return new EvalItem(new ArrayList<EvalItem>());
				}
				
				if (keyword.equals("lambda")) {
					EvalItem params = list.remove(0);
					EvalItem body = list.remove(0);
					return new EvalItem(params, body, env);
				}
				
				if (keyword.equals("begin")) {
					EvalItem ret = null;
					for (EvalItem item : list) {
						ret = evaluate(item, env);
					}
					return ret;
				}
			}
		}
		throw new Exception("Eval error!");
	}
}
