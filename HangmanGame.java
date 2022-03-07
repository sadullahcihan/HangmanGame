import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class HangmanGame {
	private static FileReader fr; // File reading variables
	private static BufferedReader br;
	private static int countryAmount; // in order to generate random number
	private static MultiLinkedList listOfAllCountries = new MultiLinkedList(); // Country List
	private static CircularSingleLinkedList answerWord = new CircularSingleLinkedList(); // CSLL1
	private static CircularSingleLinkedList displayedWord = new CircularSingleLinkedList(); // CSLL2
	private static DoubleLinkedList alphabet = new DoubleLinkedList(); // all alphabet DLL
	private static SingleLinkedList highScoreTable = new SingleLinkedList(); // high-score SLL

	public static void main(String[] args) throws Exception {
		int score = 0, leaves = 6; // player's score and leaves
		Scanner sc = new Scanner(System.in); // user input
		readAllFiles(); // Text files reading process
		int randomNum = (int) (Math.random() * countryAmount) + 1; // Generates_Random number on the interval of [1,165)
		System.out.println("***HANGMAN***\nRandomly generated number: " + randomNum + "\n");
		String keyWord = listOfAllCountries.findRandomWord(randomNum).toUpperCase(); // Finds random word
		for (int i = 0; i < keyWord.length(); i++) {
			if (keyWord.charAt(keyWord.length() - i - 1) != '�') // In_order to covert '�' to 'I'
				answerWord.add(keyWord.charAt(keyWord.length() - i - 1)); // adds random word to CSLL sequentially
			else
				answerWord.add('I'); // In_order to covert '�' to 'I'
			displayedWord.add('-'); // adds displayed word's dashes
		}
		for (int i = 65; i < 91; i++)
			alphabet.addToTheEnd((char) i); // all alphabet has been added to alphabet DLL
		do {
			System.out.print("Leaves: " + leaves + "  Score: " + score + " \nThe rest of letters: ");
			alphabet.displayFromHead(); // All alphabet is printed
			System.out.print("Word : ");
			displayedWord.display(); // hidden word like (------)
			System.out.print("Guess: ");
			String inputLetter = sc.nextLine();
			boolean f=false;
			if (inputLetter.equalsIgnoreCase("i")) // in case of user writes "i"or "�", it converts "I"{
			{
				inputLetter = "I";
				f=true;
			}
			if(!f)
				inputLetter = inputLetter.toUpperCase();
			while (inputLetter.length() == 0 || inputLetter.length() > 1 || inputLetter.charAt(0) < 65
					|| inputLetter.charAt(0) > 92) { // User input's error controls (ASCII chars A to Z)
				System.out.print("Guess: ");
				inputLetter = sc.nextLine();
				if (inputLetter.equalsIgnoreCase("i")) // in case of user writes "i"or "�", it converts "I"
					inputLetter = "I";
				inputLetter = inputLetter.toUpperCase();
			}
			if (alphabet.delete(inputLetter.charAt(0))) { // Deleting procedure andReturns is item deleted control flag
				if (answerWord.search(inputLetter.charAt(0))) { // taken letter is exist or not control
					displayedWord.change(answerWord, inputLetter.charAt(0)); // the method changes each correct letter
					if (inputLetter.charAt(0) == 'A' || inputLetter.charAt(0) == 'E' || inputLetter.charAt(0) == 'I'
							|| inputLetter.charAt(0) == 'O' || inputLetter.charAt(0) == 'U')
						score += 5; // if letter is vowel then user gets 5 points
					else
						score += 10; // if letter is consonant then user gets letter 10 points
				} else
					leaves--; // if user guesses wrong letter, leaves are decreased.
			}
			System.out.println();
		} while (leaves != 0 && displayedWord.search('-')); // if leaves finished or word is completed the game is ended
		System.out.print("Leaves: " + leaves + "  Score: " + score + " \nThe rest of letters: ");
		alphabet.displayFromHead(); // All alphabet is printed
		System.out.print("Word : ");
		displayedWord.display(); // hidden word like (------)
		if (leaves == 0) { // Lost message
			System.out.print("\nYou lost...\nThe answer is ");
			answerWord.display();
		} else { // Won message
			System.out.println("\nYou won...\nPlease enter your name: ");
			String name = sc.nextLine();
			highScoreTable(name, score); // user is added to high-score table
		}
		SingleLinkedList.Node temp = highScoreTable.getHead();
		int i = 0; // ranking counter
		while (temp != null) { // printing high score table
			System.out.println(i + 1 + "-" + temp.getData().toString().split(";")[0] + " -> "
					+ temp.getData().toString().split(";")[1]);
			i++;
			temp = temp.getLink();
		}
		System.out.println(
				"\nIf you aren't in the high-score table, \nyou lost or you aren't in the top 10 players.\nThe game has ended.");
	}

	public static void readAllFiles() throws Exception {
		fr = new FileReader("input.txt"); // country list reading
		br = new BufferedReader(fr);
		String data;
		while ((data = br.readLine()) != null) {
			listOfAllCountries.addAlphebetically(data); // this method sorts all words
			countryAmount++;
		}
		fr.close();
		fr = new FileReader("HighScoreTable.txt"); // High Score Table reading
		br = new BufferedReader(fr);
		while ((data = br.readLine()) != null)
			highScoreTable.addSorted(data);
		fr.close();
	}

	public static void highScoreTable(String name, Integer score) {
		System.out.println("***HIGH-SCORE TABLE***");// Prints high score table and writes player's score to txt_file
		highScoreTable.addSorted(name + ";" + score); // adds sequentially according to their points
		highScoreTable.deleteLast(); // To save just top 10 players, 11th player is deleted
		SingleLinkedList.Node head = highScoreTable.getHead();
		SingleLinkedList.Node temp = head;
		String newHighScores = ""; // in order to save the text file
		while (temp != null) { // new players are taken from highScoreTable SLL
			if (temp.getLink() != null)
				newHighScores += temp.getData().toString() + "\r\n"; // players
			else
				newHighScores += temp.getData().toString(); // last player
			temp = temp.getLink();
		}
		writeToFile("HighScoreTable.txt", newHighScores); // new player and old players are added to txt_file
	}

	public static void writeToFile(String path, String newTxtContent) { // writes top ten player to file
		try {
			FileOutputStream outputStream = new FileOutputStream(path);
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8"); // turns UTF-8
			BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
			bufferedWriter.write(newTxtContent); // writes to file
			bufferedWriter.close();
		} catch (Exception e) {
			System.out.println("Error!");
		}
	}
}
