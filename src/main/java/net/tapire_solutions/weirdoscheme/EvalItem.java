package net.tapire_solutions.weirdoscheme;
import java.util.List;


public class EvalItem {
	
	// value properties
	private String value = null;
	
	// list properties
	private List<EvalItem> list = null;
	
	// lambda properties
	private EvalItem params = null;
	private EvalItem body = null;
	private Environment env = null;
	
	@Override	
	public boolean equals(Object obj) {
		if (!(obj instanceof EvalItem)) return false;
		EvalItem other = (EvalItem)obj;
		if (other.isValue() && isValue() && other.getValue().equals(this.getValue()))
			return true;
		if (other.isList() && isList() && other.getList().size() == getList().size()) {
			for (int i = 0; i < getList().size(); i++) {
				if (!other.getList().get(i).equals(getList().get(i))) 
					return false;
			}
		}
		return super.equals(obj);
	}
	
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
	public EvalItem(List<EvalItem> list) {
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
	
	public List<EvalItem> getList() {
		return list;
	}
	
	public String getValue() {
		return value;
	}
	
	public Environment getEnv() {
		return env;
	}
	
	public EvalItem getBody() {
		return body;
	}
	
	public EvalItem getParams() {
		return params;
	}
	
	public String toStringSelectRecurse(boolean recurse) {
		if (isValue()) {
			return value;
		}
		if (isList()) {
			String listFormatted = "(";
			for (EvalItem i : list) {
				listFormatted += " " + i.toStringSelectRecurse(recurse) + " ";
			}
			listFormatted += ")";
			return listFormatted;
		}
		if (isLambda()) {
			if (recurse) {
				return "LAMBDA<<" + params + body + env + ">>";
			} else {
				return "LAMBDA<" + params + body + ">";
			}
		}
		return "ERROR";
	}

	@Override
	public String toString() {
		return toStringSelectRecurse(true);
	}
}
