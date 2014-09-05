import java.util.ArrayList;


public class Builtins {
	public static EvalItem callBuiltin(String name, ArrayList<EvalItem> args) {
		if (name.equals("+")) {
			int value = 0;
			for (EvalItem arg : args) {
				value += Integer.parseInt(arg.getValue());
			}
			return new EvalItem(new Integer(value).toString());
		} else if (name.equals("eq")) {
			EvalItem first = args.get(0);
			for (EvalItem arg : args) {
				if (first.getValue().equals(arg.getValue())) {
					return new EvalItem("true");
				}
			}
			return new EvalItem("false");
		}
		
		return null;
	}
}
