import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class ReplApp {
	public static void main(String args[]) {
		Environment globalEnv = new Environment(null);
		
		System.out.println("------ Welcome to weirdo-scheme. -------");
		
		while (true) {
			System.out.print(">> ");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String line = null;
			try {
				line = br.readLine();
				if (line.trim().equals("exit")) {
					System.exit(0);
				}
			} catch (IOException ioe) {
				System.out.println("IO error.");
				System.exit(1);
			}
			try {
				EvalItem parsed = ExpressionParser.read(line);
				EvalItem output = Evaluator.evaluate(parsed, globalEnv);
				System.out.println("===> " + output);
			} catch (LispParserException e1) {
				System.out.println("Invalid syntax (" + e1 + ")");
			} catch (LispEvaluatorException e2) {
				System.out.println("Error evaluating (" + e2 + ")");
			}	
		}
	}
}
