import java.util.ArrayList;


public class Builtins {
	public static EvalItem callBuiltin(String name, ArrayList<EvalItem> args, Environment env) {
		if (name.equals("+")) {
			int value = 0;
			for (EvalItem arg : args) {
				value += Integer.parseInt(arg.getValue());
			}
			return new EvalItem(new Integer(value).toString());
		} else if (name.equals("*")) {
			int value = 1;
			for (EvalItem arg : args) {
				value *= Integer.parseInt(arg.getValue());
			}
			return new EvalItem(new Integer(value).toString());
		} else if (name.equals("eq")) {
			EvalItem first = args.get(0);
			for (EvalItem arg : args) {
				if (!first.getValue().equals(arg.getValue())) {
					return new EvalItem("false");
				}
			}
			return new EvalItem("true");
		} else if (name.equals("printenv")) {
			System.out.println("<printenv>" + env);
			return new EvalItem(new ArrayList<EvalItem>());
		} else if (name.equals("print")) {
			System.out.println("<print> " + args);
			return new EvalItem(new ArrayList<EvalItem>());
		}
		
		return null;
	}
}
