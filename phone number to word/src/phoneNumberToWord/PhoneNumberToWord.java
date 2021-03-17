package phoneNumberToWord;

import java.io.*;
import java.util.*;

public class PhoneNumberToWord {								//CHANGE PATH AT LINE 72 BEFORE RUNNING WILL WORK CORRECTLY

	public static void main(String[] args) {
		ArrayList<String> words = new ArrayList<String>();
		ArrayList<Double> numbers = new ArrayList<Double>();
		ArrayList<Integer> hashedNums = new ArrayList<Integer>();
		Key[][] hashTable = new Key[10003199][10];
		
		readFile(words);
		
		makeHashTable(words, numbers, hashedNums, hashTable);
		
		showPossibleWords(hashTable);
	}
	
	public static double changeWordToNumber(String word) {		//change word to correct number representation
		double zeroes = 0;
		double number = 0;
		double temp = 0;
		for (int i=0; i<word.length(); i++) {
			char letter = word.charAt(i);
			zeroes = (word.length() - i) - 1;
			if (letter == 'a' || letter == 'b' || letter == 'c') {
				temp =  (2 * (Math.pow(10, zeroes)));
				number = number + temp;
			}
			if (letter == 'd' || letter == 'e' || letter == 'f') {
				temp =  (3 * (Math.pow(10, zeroes)));
				number = number + temp;
			}
			if (letter == 'g' || letter == 'h' || letter == 'i') {
				temp =  (4 * (Math.pow(10, zeroes)));
				number = number + temp;
			}
			if (letter == 'j' || letter == 'k' || letter == 'l') {
				temp =  (5 * (Math.pow(10, zeroes)));
				number = number + temp;
			}
			if (letter == 'm' || letter == 'n' || letter == 'o') {
				temp =  (6 * (Math.pow(10, zeroes)));
				number = number + temp;
			}
			if (letter == 'p' || letter == 'q' || letter == 'r' || letter == 's') {
				temp =  (7 * (Math.pow(10, zeroes)));
				number = number + temp;
			}
			if (letter == 't' || letter == 'u' || letter == 'v') {
				temp =  (8 * (Math.pow(10, zeroes)));
				number = number + temp;
			}
			if (letter == 'w' || letter == 'x' || letter == 'y' || letter == 'z') {
				temp =  (9 * (Math.pow(10, zeroes)));
				number = number + temp;
			}
		}
		return number;
	}
	
	public static double hash(double number) {		//hash number
		double h = number % 10003199;
		return h;
	}
	
	public static void readFile(ArrayList<String> words) {		//fill array list with words from given file
		
		try {
			File myObj = new File("C:\\Users\\elies\\OneDrive\\Documents\\words\\words\\words.txt");		//THIS NEEDS TO BE CHANGED TO WHEREVER THE WORDS.TXT IS IN YOUR FILE DIRECTORY
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				if (data.length() == 10 || data.length() == 7 || data.length() == 4 || data.length() == 3) {
					words.add(data);
				}
		}
		myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

	public static void makeHashTable(ArrayList<String> words, ArrayList<Double> numbers, ArrayList<Integer> hashedNums, Key[][] hashTable) {		//take words from file, turn them into numbers, and make a hashTable with words and numbers stored at indices

		double number = 0;
		double hashed = 0;
		for(int i=0; i<10003199; i++) {
			hashTable[i][0] = new Key(-1,"empty");
		}
		
		for (int i=0; i<words.size(); i++) {
			number = changeWordToNumber(words.get(i));
			numbers.add(number);
		}
		
		for (int i=0; i<numbers.size(); i++) {
			hashed = hash(numbers.get(i));
			hashedNums.add((int) hashed);
		}

		for (int i=0; i<numbers.size(); i++) {		//fills table with words at their respective hashed indices
			for (int j=0; j<10; j++) {
				if (hashTable[hashedNums.get(i)][j].getWord() == "empty") {
					hashTable[hashedNums.get(i)][j] = new Key(numbers.get(i),words.get(i));
					for (int k=j+1; k<10; k++) {		//fills spaces below an inserted word with "empty" places (helps avoid null pointer)
						hashTable[hashedNums.get(i)][k] = new Key(-1,"empty");
					}
					j=10;
				}
			}
		}
		
	}
	
	public static void showPossibleWords(Key[][] hashTable) {
		Scanner input = new Scanner(System.in);
		System.out.println("Enter phone number:");		//i assume that only 10 digit phones numbers are input
		
		String inputNumber = input.nextLine();
		
		double hashedInput = Double.parseDouble(inputNumber);
		hashedInput = hash(hashedInput);
		
		String tempNumber;
		String tempNumber2;
		int tempHashed;
		int tempHashed2;
		String cutoff;
		String cutoff2;
		boolean nothingFound = true;
		
		if (hashTable[(int)hashedInput][0].getWord() != "empty") {		//if there is 10 letter representation
			for (int i=0; i<10; i++) {
				if (hashTable[(int)hashedInput][i].getWord() != "empty") {
					System.out.println(hashTable[(int)hashedInput][i].getWord());
				}
				else {
					i=10;
				}
			}
			nothingFound = false;
		}
		
		tempNumber = inputNumber.substring(3,10);
		tempHashed = (int) Double.parseDouble(tempNumber);
		cutoff = inputNumber.substring(0,3);
		
		if(hashTable[tempHashed][0].getWord() != "empty" && nothingFound == true) {
			for (int i=0; i<10; i++) {
				if (hashTable[tempHashed][i].getWord() != "empty") {
					System.out.println(cutoff + "-" + hashTable[tempHashed][i].getWord());
				}
				else {
					i=10;
				}
			}
			nothingFound = false;
		}
		
		tempNumber = inputNumber.substring(3,6);
		tempNumber2 = inputNumber.substring(6,10);
		tempHashed = (int) Double.parseDouble(tempNumber);
		tempHashed2 = (int) Double.parseDouble(tempNumber2);
		cutoff = inputNumber.substring(0,3);
		
		if(hashTable[tempHashed][0].getWord() != "empty" && hashTable[tempHashed2][0].getWord() != "empty" && nothingFound == true) {
			for (int i=0; i<10; i++) {
				for (int j=0; j<10; j++) {
					if (hashTable[tempHashed][i].getWord() != "empty" && hashTable[tempHashed2][j].getWord() != "empty") {
						System.out.println(cutoff + "-" + hashTable[tempHashed][i].getWord() + "-" + hashTable[tempHashed2][j].getWord());
					}
					else {
						j=10;
					}
				}
			}
			nothingFound = false;
		}
		
		tempNumber = inputNumber.substring(3,6);
		tempHashed = (int) Double.parseDouble(tempNumber);
		cutoff = inputNumber.substring(0,3);
		cutoff2 = inputNumber.substring(6,10);
		
		if(hashTable[tempHashed][0].getWord() != "empty" && nothingFound == true) {
			for (int i=0; i<10; i++) {
				if (hashTable[tempHashed][i].getWord() != "empty") {
					System.out.println(cutoff + "-" + hashTable[tempHashed][i].getWord() + "-" + cutoff2);
				}
				else {
					i=10;
				}
			}
			nothingFound = false;
		}
		
		tempNumber = inputNumber.substring(6,10);
		tempHashed = (int) Double.parseDouble(tempNumber);
		cutoff = inputNumber.substring(0,3);
		cutoff2 = inputNumber.substring(3,6);
		
		if(hashTable[tempHashed][0].getWord() != "empty" && nothingFound == true) {
			for (int i=0; i<10; i++) {
				if (hashTable[tempHashed][i].getWord() != "empty") {
					System.out.println(cutoff + "-" + cutoff2 + "-" + hashTable[tempHashed][i].getWord());
				}
				else {
					i=10;
				}
			}
			nothingFound = false;
		}
		
		tempNumber = inputNumber.substring(0,3);
		cutoff = inputNumber.substring(3,6);
		cutoff2 = inputNumber.substring(6,10);
		
		if (nothingFound == true){
			System.out.println(tempNumber + "-" + cutoff + "-" + cutoff2);
		}
		
		input.close();
	}
}
