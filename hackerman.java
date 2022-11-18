//Max Pagels 30063064
//Version: Sept 23 2022

/*
This class was created to run a text-based hangman-style password cracking game

*/

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;

public class hackerman{

    //static variables
    private static ArrayList<String> passwordList;
    private static int lives;
    private static int level;

	public static void main(String[] args){
	
        //setup
        passwordList = initPasswordList();
        lives = 5;
        level = 1;

        //run a level
        runLevel(passwordList.get(randInt(0, passwordList.size())));

	}

    /* Description: Read through file named "passwordData" and adds each line as a String in an ArrayList
     * In: None
     * Out: A String ArrayList containing all passwords in the passwordData file
     */
    //NOTE: at the moment it only reads the first 10 lines
    private static ArrayList<String> initPasswordList(){

        ArrayList<String> tempList = new ArrayList<String>();

        BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(
					"dataTest"));
			String line = reader.readLine();

		    while (line != null) {
           // for(int i = 0; i < 10; i++){
				tempList.add(line);
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return tempList;
    }

    /* Description: Generates a random integer within a range
    * In: An integer for the min and max 
    * Out: A random int within the given range
    */
    private static int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

     /* Description: Runs one game where the user has to guess the given password
    * In: A string containing a password
    * Out: None
    */
    private static void runLevel(String password) {
        
        char[] passwordCharArray = password.toCharArray();
        ArrayList<Character> guessedChars = new ArrayList<Character>();

        while(lives > 0){

            int charsToGuess = 0;

            //print hidden password as a series of dashes
            System.out.print("Password: ");
            printHiddenPassword(passwordCharArray, guessedChars);
            System.out.print("\n");

            //print the users previous guesses
            System.out.print("Previously guessed characters: ");
            for(int i = 0; i < guessedChars.size(); i++){
                System.out.print(guessedChars.get(i) + " ");
            }
            System.out.print("\n\n");

            //get users guess and adds to guessedChars arraylist
            char userChar = getUserChar();
            guessedChars.add(userChar);
            
            //check if the users guess was correct
            boolean guessCorrect = false;
            for(int i = 0; i < passwordCharArray.length; i++){
                if(userChar == passwordCharArray[i]){
                    guessCorrect = true;
                }
            }

            //prints message about the users guess, if increments/decrements lives accordingly
            if(guessCorrect){
                System.out.println("[" + userChar + "] was found in the password.");
                lives++;
            }else{
                System.out.println("[" + userChar + "] was NOT found in the password.");
                lives--;
            }

            //check to see if password complete
            if(passwordGuessed(passwordCharArray, guessedChars)){
                break;
            }

            System.out.println("Remaining lives: " + lives);
            waitForEnter();

        }
        
        //prints end of level message
        if(lives > 0){
            System.out.println("\nYou have correctly guessed the password.");
        }else{
            System.out.println("\nYou have run out of lives.");
        }

    }

    /* Description: Gets a single character input from the user
    * In: None
    * Out: Returns a char from the user
    */
    private static char getUserChar(){

        Scanner scanner = new Scanner(System.in);  
        boolean accept = false;
        String inputString = "";

        while(!accept){
            System.out.print("Enter input character: ");
            inputString = scanner.nextLine();
            if(inputString.length() != 1){
                System.out.println("Input must be a single character");
            }else{
                accept = true;
            }
        }
        
        return inputString.charAt(0);
    }

    /* Description: Prints the given char array as a series of dashes, if a character was already discovered will replace
    *the dash with the correct character. The number of dashes used will be returned as output. 
    * In: A char array containing the password, a char ArrayList containing already guessed characters
    * Out: An int for the number of dashes used
    */
    private static void printHiddenPassword(char[] passwordCharArray, ArrayList<Character> guessedChars) {

        for(int i = 0; i < passwordCharArray.length; i++){

            boolean charDetected = false;
            for(int j = 0; j < guessedChars.size(); j++){
                if(passwordCharArray[i] == guessedChars.get(j)){
                    charDetected = true;
                } 
            }

            if(charDetected){
                System.out.print(passwordCharArray[i] + " ");
            }else{
                System.out.print("_ ");
            }
        }
    }
    
     /* Description: Prompts the user to press enter and waits until they do so
    * In: none
    * Out: none
    */
    private static void waitForEnter(){
        Scanner scanner = new Scanner(System.in); 
        System.out.print("Press enter to continue... ");
        String temp = scanner.nextLine();
        System.out.print("\n\n");
    }

    /* Description: checks to see if the user has guessed the full password
    * In: A char array containing the password, a char ArrayList containing already guessed characters
    * Out: A boolean that is true if the full password had been discovered
    */
    private static boolean passwordGuessed(char[] passwordCharArray, ArrayList<Character> guessedChars){
       
        int correctGuesses = 0;

        for(int i = 0; i < passwordCharArray.length; i++){
            for(int j = 0; j < guessedChars.size(); j++){
                if(passwordCharArray[i] == guessedChars.get(j)){
                    correctGuesses++;
                } 
            }
        }

        if(passwordCharArray.length - correctGuesses == 0){
            return true;
        }
        return false;
    }

}