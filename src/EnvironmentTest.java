import static org.junit.Assert.*;

import org.junit.Test;


public class EnvironmentTest {

	@Test
	public void test() {
		Environment env1 = new Environment(null);
		env1.add("var1", new EvalItem("value1"));
		assertEquals("value1", env1.findValueOfVar("var1").getValue());
		
		Environment env2 = new Environment(env1);
		env2.add("var2", new EvalItem("value2"));
		assertEquals("value1", env2.findEnvironmentWithVar("var1").findValueOfVar("var1").getValue());
		assertEquals("value2", env2.findEnvironmentWithVar("var2").findValueOfVar("var2").getValue());
		
		Environment env3 = new Environment(env2);
		env3.add("var1", new EvalItem("value12"));
		assertEquals("value12", env3.findEnvironmentWithVar("var1").findValueOfVar("var1").getValue());
		assertEquals("value2", env3.findEnvironmentWithVar("var2").findValueOfVar("var2").getValue());
	}

}
