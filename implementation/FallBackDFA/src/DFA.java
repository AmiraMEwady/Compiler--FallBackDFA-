import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;



public class DFA {

	private static final String filename = "D:/GUC intranet folders/Semester 10/AdvancedComputerLab/Task2_23071/Task2/Task2/fallbackdfa.in";
	private static final String inputFile = "D:/GUC intranet folders/Semester 10/AdvancedComputerLab/Task2_23071/Task2/Task2/Lab2.in";

	public static String initialState;
	public static ArrayList<String> alphabet = new ArrayList<String>();
	public static ArrayList<String> states = new ArrayList<String>();
	public static ArrayList<String> AcceptStates = new ArrayList<String>();
	public static ArrayList<String[]> transitions = new ArrayList<String[]>();
	// public static ArrayList<String[]> regularDefinitions= new
	// ArrayList<String[]>();
	// public static ArrayList<String[]> labels= new ArrayList<String[]>();
	public static HashMap<Integer, String> labels = new HashMap<Integer, String>();
	public static HashMap<String, String> regularDefinition = new HashMap<String, String>();

	public static String[] parts;
	public static String[] parts2;
	public static String[] parts3;
	public static String[] parts4;
	public static String[] parts5;
	public static String[] parts6;

	public static int AcceptNum = 0;
	public static Stack<String> stack = new Stack<String>();
	public static ArrayList<String> tape = new ArrayList<String>();
	public static int left = 0;
	public static int right = 0;
	public static String action = "";
	public static String outputString = "";
	public static String string = "";
	public static String inputString = "";

	

	public static void readInput() {
		BufferedReader br = null;
		FileReader fr = null;
		BufferedWriter bw = null;
		FileWriter fw = null;
		File file = new File("Lab2.out");
		int count=0;
		try {
			fr = new FileReader(inputFile);
			br = new BufferedReader(fr);
			
			fw = new FileWriter(file);
			bw = new BufferedWriter(fw);
		
			String sCurrentLine;

		

			while ((sCurrentLine = br.readLine()) != null) {
				outputString ="";
				
				System.out.println(sCurrentLine);
				
				if(sCurrentLine.contains("input")){
					count++;
					bw.write("output" + count +":");
					bw.newLine();
				}else{
				
					fallBack(sCurrentLine);
					String output = outputString;
					bw.write(outputString);
					bw.newLine();
				}
			

			}

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (br != null)
					br.close();

				if (fr != null)
					fr.close();

				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();
			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}

	}

	public static void fallBack(String input) {
		left=0;
		right=0;
		inputString = input;
		stack.push(initialState);
		
		boolean accept;
		// loop 3l input string
		//System.out.println("Input Length: " + input.length());
		for (int i = 0; i < input.length(); i++) {

			String character = input.charAt(i) + "";
			
			

			accept = false;
			String current = stack.peek();
			
			String nextState;

			for (int j = 0; j < transitions.size(); j++) {
				String[] array = transitions.get(j);
				if (array[0].equals(current) && array[1].equals(character)) {

					nextState = array[2];
					accept = true;
					stack.push(nextState);
					left++;
					//System.out.println("HEYY " + left);
					break;
				}
			}
			if (accept == false) {
				stack.push("!");
				
				left++;
				boolean found = rejectState(stack, input);
				if (!found) {
					outputString = outputString +"  " + "Error! Not in the language";
					return;
				}
				i = left - 1;
				//System.out.println("Left " + left + " Right " + right + " i "
				//		+ i);

			}
			
			if (left == input.length() && !stack.isEmpty()) {
				rejectState(stack,input);
				i = left - 1;
				
			}

			

		}
		
		boolean found = rejectState(stack, input);
		if (!found) {
			//outputString = outputString+"Error! Not in the language";
			//System.out.println("Error!");
		return;
		}
		
		}
	public static void write(String output){
		BufferedWriter bw = null;
		FileWriter fw = null;
		File file = new File("Lab2.out");
		try {

			String content = output;

			fw = new FileWriter(file);
			bw = new BufferedWriter(fw);
			bw.write(content);
			bw.newLine();

			System.out.println("Done");

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}

	}

	

	public static boolean rejectState(Stack<String> stack, String input) {
		
		
		String outputt="";
		boolean found = false;
		while (!stack.isEmpty()) {
			String peek = stack.peek();
			if (peek.equals("!")) {
				left--;
				
				stack.pop();

			} else {
				left--;
				
				String currentState = stack.pop();
				if (!AcceptStates.contains(currentState)) {
					continue;
				}
				found = true;
				String currentLabel = labels
						.get(Integer.parseInt(currentState));

				for (Map.Entry entry : regularDefinition.entrySet()) {

					if (currentLabel.equals(entry.getValue())) {
						String key = (String) entry.getKey();
						// int temp = left+1;
						String output = inputString.substring(right, left + 1);
					//	System.out.println("output bs is " + output);
						outputString = outputString + " " + key + "," + output;
						outputt = outputString;
						System.out.println("FINALSTRING  IS: " + outputString);
						//write(outputString);
						break;
					}
				}

				

				left++;
				right = left;
				System.out.println(left);
				System.out.println(right);
				stack.clear();

			}

		}

		stack.push(initialState);
		return found;
	}

	public static void main(String[] args) {
		boolean split = false;
		boolean alpha = false;
		boolean transition = false;
		boolean start = false;
		boolean stop = false;
		boolean acceptState = false;
		boolean label = false;
		boolean definition = false;
		BufferedReader br = null;
		FileReader fr = null;
		
		

		String tempString = "";
		String alphabets = "";
		String startS = "";
		String trans = "";
		String acceptS = "";
		String def = "";
		String lab = "";

		try {

			fr = new FileReader(filename);
			br = new BufferedReader(fr);
			
		
			
			String sCurrentLine;
		

			br = new BufferedReader(new FileReader(filename));
		
			while ((sCurrentLine = br.readLine()) != null) {
				if (split == true) {
					tempString = sCurrentLine;
					parts = tempString.split(",");
					split = false;
				

				}
				if (alpha == true) {
					alphabets = sCurrentLine;
					parts2 = alphabets.split(",");
					

				}
				if (label == true) {
					lab = sCurrentLine;

					parts6 = lab.split(",");
					try {
						labels.put(Integer.parseInt(parts6[0]), parts6[1]);
						
					} catch (NumberFormatException e) {
						// System.out.println("This is not a number");
						// System.out.println(e.getMessage());
					}

				}

				if (start == true) {
					startS = sCurrentLine;
					transition = false;

					initialState = startS;

					start = false;
					transition = false;
				}

				if (transition == true) {
					trans = sCurrentLine;
					parts3 = trans.split(",");
					transitions.add(parts3);

				}

				if (definition == true) {
					def = sCurrentLine;
					parts5 = def.split(",");

					regularDefinition.put(parts5[0], parts5[1]);
				
				}

				if (acceptState == true) {
					try {
						acceptS = sCurrentLine;
						parts4 = acceptS.split(",");

						for (int i = 0; i < parts4.length; i++) {
							AcceptStates.add(parts4[i]);

						}
						
						acceptState = false;
					} catch (NumberFormatException e) {
						// System.out.println("This is not a number");
						// System.out.println(e.getMessage());
					}

				}

				if (sCurrentLine.equals("#set of states")) {
					split = true;
					stop = true;

				}
				if (sCurrentLine.equals("#alphabet")) {
					alpha = true;
				}

				if (sCurrentLine.equals("#transitions")) {
					transition = true;
				}
				if (sCurrentLine.equals("#start state")) {
					start = true;
					transition = false;

				}

				if (sCurrentLine.equals("#set of accept states")) {
					acceptState = true;

				}

				if (sCurrentLine.equals("#label")) {
					label = true;
					acceptState = false;
				}

				if (sCurrentLine.equals("#regulardefinition")) {
					definition = true;
					label = false;
				}
				
				
				
				System.out.println(sCurrentLine);
				

			}
		
			readInput();
		
			transitions.remove(transitions.size() - 1);
			for (int j = 0; j < transitions.size(); j++) {
				String[] temp = transitions.get(j);
				for (int k = 0; k < temp.length; k++) {
					// System.out.print( temp[k]+ ",");
				}
				// System.out.println("TRANISITONS ARE " + transitions.get(j));
			}

			//System.out.println(labels.toString());
			 //readInput();
			
			

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (br != null)
					br.close();

				if (fr != null)
					fr.close();
				

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}

	}
}
