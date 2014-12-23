package net.tapire_solutions.weirdoscheme;
import java.util.HashMap;


public class Environment {
	private Environment outer;
	private HashMap<String, EvalItem> map;
	
	public Environment(Environment outer) {
		this.outer = outer;
		map = new HashMap<String, EvalItem>();
	}
	
	public void add(String var, EvalItem value) {
		map.put(var, value);
	}
	
	public Environment findEnvironmentWithVar(String var) {
		if (map.get(var) != null) {
			return this;
		}
		if (outer == null) {
			return null;
		}
		return outer.findEnvironmentWithVar(var);
	}
	
	public EvalItem findValueOfVar(String var) {
		return map.get(var);
	}
	
	@Override
	public String toString() {
		String descr = "Environment=(";
		for (String key : map.keySet()) {
			descr += key + "=" + map.get(key).toStringSelectRecurse(false) + ", ";
		}
		if (outer != null) {
			descr += "OuterEnvironment={" + outer + "}";
		}
		return descr + ")";
	}
}
