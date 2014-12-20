package net.tapire_solutions.weirdoscheme;
import static org.junit.Assert.*;

import org.junit.Test;


public class EvaluatorTest {

	@Test
	public void keywordTest() {
		Environment globalEnv = new Environment(null);
		EvalItem parsed = null;
		try {
			parsed = ExpressionParser.read(" (define theVar ( quote 3 2) )");
			Evaluator.evaluate(parsed, globalEnv);
			assertNotNull(globalEnv.findValueOfVar("theVar"));
			
			parsed = ExpressionParser.read(" (set! theVar 3)");
			Evaluator.evaluate(parsed, globalEnv);
			assertEquals("3", globalEnv.findValueOfVar("theVar").getValue());
			
			parsed = ExpressionParser.read(" (if (quote ()) (quote 4) (quote 5 4 3))");
			assertEquals("4", Evaluator.evaluate(parsed, globalEnv).getList().get(1).getValue());
			
			parsed = ExpressionParser.read(" (if (= theVar 3 ) (quote 7) (quote 5 4 3))");
			assertEquals("7", Evaluator.evaluate(parsed, globalEnv).getValue());
			
			parsed = ExpressionParser.read(" (begin (quote 1) (quote 4) (quote 5))");
			assertEquals("5", Evaluator.evaluate(parsed, globalEnv).getValue());
			
			parsed = ExpressionParser.read(" (lambda (arg1 arg2 arg3) (quote arg1 1 2 arg2 ))");
			assertTrue((Evaluator.evaluate(parsed, globalEnv).isLambda()));
			
			parsed = ExpressionParser.read(" (define theFunc (lambda (arg1 arg2 arg3) (if (= arg3 3) (quote thatsRight) (quote noSorry) )))");
			Evaluator.evaluate(parsed, globalEnv);
			assertTrue(globalEnv.findValueOfVar("theFunc").isLambda());
			
			parsed = ExpressionParser.read(" (theFunc 1 2 3)");
			assertEquals("thatsRight", Evaluator.evaluate(parsed, globalEnv).getValue());
			
			parsed = ExpressionParser.read(" (define add10 (lambda (arg1) (+ arg1 10)))");
			Evaluator.evaluate(parsed, globalEnv);
			parsed = ExpressionParser.read("(add10 1)");
			assertEquals("11", Evaluator.evaluate(parsed, globalEnv).getValue());
			
			parsed = ExpressionParser.read("(define iterate-until (lambda (limit num-iters) (if (= limit num-iters) num-iters (iterate-until limit (+ num-iters 1)) ) ))");
			Evaluator.evaluate(parsed, globalEnv);
			parsed = ExpressionParser.read("(iterate-until 10 0)");
			assertEquals("10", Evaluator.evaluate(parsed, globalEnv).getValue());
			
		} catch (LispEvaluatorException e1) {
			System.out.println(e1);
		} catch (LispParserException e2) {
			System.out.println(e2);
		}
	}
	
	@Test
	public void recursionTest() {
		Environment globalEnv = new Environment(null);
		EvalItem parsed = null;
		try {
			parsed = ExpressionParser.read("(define fact (lambda (n) (if (= n 0) (quote 1) (* n (fact (+ n -1))))))");
			
			Evaluator.evaluate(parsed, globalEnv);
			parsed = ExpressionParser.read("(fact 3)");
			assertEquals("6", Evaluator.evaluate(parsed, globalEnv).getValue());
			
			parsed = ExpressionParser.read("(fact 10)");
			assertEquals("3628800", Evaluator.evaluate(parsed, globalEnv).getValue());
			
		} catch (LispEvaluatorException e1) {
			System.out.println(e1);
		} catch (LispParserException e2) {
			System.out.println(e2);
		}
	}

}
