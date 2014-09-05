import static org.junit.Assert.*;

import org.junit.Test;


public class EvaluatorTest {

	@Test
	public void test() {
		Environment globalEnv = new Environment(null);
		EvalItem parsed = null;
		try {
			parsed = ExpressionParser.read(" (define nizze ( quote 3 2) )");
			System.out.println(Evaluator.evaluate(parsed, globalEnv));
			
			parsed = ExpressionParser.read(" (set! nizze 3)");
			System.out.println(Evaluator.evaluate(parsed, globalEnv));
			
			parsed = ExpressionParser.read(" (if (quote ) (quote 4) (quote 5 4 3))");
			System.out.println(Evaluator.evaluate(parsed, globalEnv));
			
			parsed = ExpressionParser.read(" (begin (quote 1) (quote 4) (quote 5))");
			System.out.println(Evaluator.evaluate(parsed, globalEnv));
			
			parsed = ExpressionParser.read(" (lambda (arg1 arg2 arg3) (quote arg1 1 2 arg2 ))");
			System.out.println(Evaluator.evaluate(parsed, globalEnv));
			
			parsed = ExpressionParser.read(" (define funkisen (lambda (arg1 arg2 arg3) (if (eq arg3 3) (quote sant) (quote falskt) )))");
			System.out.println(Evaluator.evaluate(parsed, globalEnv));
			
			parsed = ExpressionParser.read(" (funkisen 1 2 3)");
			System.out.println(Evaluator.evaluate(parsed, globalEnv));
			
		} catch (LispEvaluatorException e1) { 
			System.out.println(e1);
		} catch (LispParserException e2) {
			System.out.println(e2);
		}
		
		System.out.println(globalEnv);
	}

}
