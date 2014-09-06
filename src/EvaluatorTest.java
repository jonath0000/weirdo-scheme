import static org.junit.Assert.*;

import org.junit.Test;


public class EvaluatorTest {

	@Test
	public void test() {
		Environment globalEnv = new Environment(null);
		EvalItem parsed = null;
		try {
			parsed = ExpressionParser.read(" (define theVar ( quote 3 2) )");
			Evaluator.evaluate(parsed, globalEnv);
			assertNotNull(globalEnv.findValueOfVar("theVar"));
			
			parsed = ExpressionParser.read(" (set! theVar 3)");
			Evaluator.evaluate(parsed, globalEnv);
			assertEquals("3", globalEnv.findValueOfVar("theVar").getValue());
			
			parsed = ExpressionParser.read(" (if (quote ) (quote 4) (quote 5 4 3))");
			assertEquals("4", Evaluator.evaluate(parsed, globalEnv).getList().get(1).getValue());
			
			parsed = ExpressionParser.read(" (begin (quote 1) (quote 4) (quote 5))");
			assertEquals("5", Evaluator.evaluate(parsed, globalEnv).getList().get(0).getValue());
			
			parsed = ExpressionParser.read(" (lambda (arg1 arg2 arg3) (quote arg1 1 2 arg2 ))");
			assertTrue((Evaluator.evaluate(parsed, globalEnv).isLambda()));
			
			parsed = ExpressionParser.read(" (define theFunc (lambda (arg1 arg2 arg3) (if (eq arg3 3) (quote thatsRight) (quote noSorry) )))");
			Evaluator.evaluate(parsed, globalEnv);
			assertTrue(globalEnv.findValueOfVar("theFunc").isLambda());
			
			parsed = ExpressionParser.read(" (theFunc 1 2 3)");
			assertEquals("thatsRight", Evaluator.evaluate(parsed, globalEnv).getList().get(0).getValue());
			
			parsed = ExpressionParser.read("(define fact (lambda (n) (if ((= n 0) 1) ((= n 1) 1) (* n (fact (- n 1))))))");
			Evaluator.evaluate(parsed, globalEnv);
			parsed = ExpressionParser.read("(fact 3)");
			// Evaluator.evaluate(parsed, globalEnv); TODO: gets stuck in loop :(
			
		} catch (LispEvaluatorException e1) { 
			System.out.println(e1);
		} catch (LispParserException e2) {
			System.out.println(e2);
		}
		
		System.out.println(globalEnv);
	}

}
