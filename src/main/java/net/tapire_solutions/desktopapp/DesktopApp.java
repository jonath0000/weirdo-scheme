package net.tapire_solutions.desktopapp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import net.tapire_solutions.weirdoscheme.Environment;
import net.tapire_solutions.weirdoscheme.ReplApp;

public class DesktopApp {
	private static void showHelp() {
		System.out.println(
				"-------------------------\n" +
				"--file [file] : Run file.\n" +
				"--help Show this message.\n");
		
		System.out.println(
				"-------------------------\n" +
				"Language:\n" +
				ReplApp.getHelp());
	}
	
	private static String readFileIntoString(String path) throws IOException {
		File file = new File(path);
		FileInputStream fis = new FileInputStream(file);
		byte[] data = new byte[(int) file.length()];
		fis.read(data);
		fis.close();
		String str = new String(data, "UTF-8");
		return str;
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
	
	public static void main(String args[]) {
		Environment globalEnv = new Environment(null);
		
		boolean exit = false;
		
		Map<String, String> argsMap = parseArguments(args);
		if (argsMap.containsKey("help")) {
			showHelp();
			exit = true;
		}
		
		String fileToParse = argsMap.get("file");
		if (fileToParse != null) {
			String line;
			try {
				line = readFileIntoString(fileToParse);
				String output = ReplApp.evaluateLine(line, globalEnv);
				System.out.println("===> " + output);
			} catch (IOException e) {
				e.printStackTrace();
			}
			exit = true;
		}
		
		if (!exit) System.out.println("------ Welcome to weirdo-scheme. -------");
		
		while (!exit) {
			System.out.print(">> ");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String line = null;
			try {
				line = br.readLine();
				if (line.trim().equals("exit") || line == null || line.isEmpty()) {
					exit = true;
				} else if (line.trim().equals("help")) {
					showHelp();
				} else {
					String output = ReplApp.evaluateLine(line, globalEnv);
					System.out.println("===> " + output);
				}
			} catch (IOException ioe) {
				System.out.println("IO error.");
				exit = true;
			}
			
		}
	}
}
