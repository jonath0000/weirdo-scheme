import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ReplApp {
	
	private static void showHelp() {
		System.out.println(
				"-------------------------\n" +
				"--file [file] : Run file.\n" +
				"--help Show this message.\n");
	}
	
	private static Map<String, String> parseArguments(String args[]) {
		Map<String, String> argsMap = new HashMap<String, String>();
		int i = 0;
		while (i < args.length) {
			if (args[i].equals("--file") && i + 1 < args.length) {
				argsMap.put("file", args[i+1]);
				i += 2;
			} else if (args[i].equals("--help")) {
				argsMap.put("help", "YES");
				i++;
			} else {
				i++;
			}
		}
		return argsMap;
	}
	
	private static String readFileIntoString(String path) throws IOException {
	    File file = new File(path);
	    StringBuilder fileContents = new StringBuilder((int)file.length());
	    Scanner scanner = new Scanner(file);
	    try {
	        while(scanner.hasNextLine()) {   
	        	String line = scanner.nextLine();
	        	if (!line.trim().startsWith(";")) {
	        		fileContents.append(line + " ");
	        	}
	        }
	        return fileContents.toString();
	    } finally {
	        scanner.close();
	    }
	}
	
	private static String evaluateLine(String line, Environment globalEnv) {
		try {
			EvalItem parsed = ExpressionParser.read(line);
			EvalItem output = Evaluator.evaluate(parsed, globalEnv);
			return output.toString();
		} catch (LispParserException e1) {
			System.out.println("Invalid syntax (" + e1 + ")");
		} catch (LispEvaluatorException e2) {
			System.out.println("Error evaluating (" + e2 + ")");
		}
		return null;
	}
	
	public static void main(String args[]) {
		Environment globalEnv = new Environment(null);
		
		Map<String, String> argsMap = parseArguments(args);
		if (argsMap.containsKey("help")) {
			showHelp();
			System.exit(0);
		}
		
		String fileToParse = argsMap.get("file");
		if (fileToParse != null) {
			String line;
			try {
				line = readFileIntoString(fileToParse);
				String output = evaluateLine(line, globalEnv);
				System.out.println("===> " + output);
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}
			System.exit(0);
		}
		
		System.out.println("------ Welcome to weirdo-scheme. -------");
		
		while (true) {
			System.out.print(">> ");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String line = null;
			try {
				line = br.readLine();
				if (line.trim().equals("exit") || line == null) {
					System.exit(0);
				}
			} catch (IOException ioe) {
				System.out.println("IO error.");
				System.exit(1);
			}
			String output = evaluateLine(line, globalEnv);
			System.out.println("===> " + output);
		}
	}
}
