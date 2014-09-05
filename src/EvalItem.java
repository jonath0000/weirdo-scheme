import java.util.ArrayList;


public class EvalItem {
	
	// value properties
	private String value = null;
	
	// list properties
	private ArrayList<EvalItem> list = null;
	
	// lambda properties
	private EvalItem params = null;
	private EvalItem body = null;
	private Environment env = null;
	
	/**
	 * As value.
	 * @param value
	 */
	public EvalItem(String value) {
		this.value = value;
	}
	
	/**
	 * As list.
	 * @param list
	 */
	public EvalItem(ArrayList<EvalItem> list) {
		this.list = list;
	}
	
	/**
	 * As lambda.
	 * @param params
	 * @param body
	 * @param env
	 */
	public EvalItem(EvalItem params, EvalItem body, Environment env) {
		this.params = params;
		this.body = body;
		this.env = env;
	}
	
	public boolean isList() {
		return (list != null);
	}
	
	public boolean isValue() {
		return (value != null);
	}
	
	public boolean isLambda() {
		return (params != null && body != null && env != null);
	}
	
	public ArrayList<EvalItem> getList() {
		return list;
	}
	
	public String getValue() {
		return value;
	}
	
	public EvalItem callLambda(ArrayList<EvalItem> args) throws Exception {
		Environment innerEnv = new Environment(env);
		for (int i = 0; i < params.getList().size(); i++) {
			innerEnv.add(params.getList().get(i).getValue(), args.get(i));
		}
		return Evaluator.evaluate(body, innerEnv);
	}
	
	@Override
	public String toString() {
		if (isValue()) {
			return value;
		}
		if (isList()) {
			String listFormatted = "(";
			for (EvalItem i : list) {
				listFormatted += " " + i + " ";
			}
			listFormatted += ")";
			return listFormatted;
		}
		if (isLambda()) {
			return "LAMBDA<<" + params + body + env + ">>";
		}
		return "ERROR";
	}
}
