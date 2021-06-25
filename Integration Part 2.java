import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
 
// <Jason Exantus>
// Hangman - a simple command line implementation of hangman
// The user inputs a char at a time. If the char is in the word (WORD),
// you get the letter. If you guess every letter, you win.
// If you guess incorrectly too many times, you lose.
 
// Primitive Data Types
// byte - Stores whole numbers from -128 to 127
// short -  Stores whole numbers from -32,768 to 32,767
// int - Stores whole numbers from -2,147,483,648 to 2,147,483,647
// long - Stores whole numbers from -9,223,372,036,854,775,808 to 9,223,372,036,854,775,807
// float - Stores fractional numbers. Sufficient for storing 6 to 7 decimal digits
// double - Stores fractional numbers. Sufficient for storing 15 decimal digits
// boolean - Stores true or false values
// char - Stores a single character/letter or ASCII values
 
public class Main {
 
    private final String WORD = "Test Word";
    private final int INCORRECT_GUESS_LIMIT = 6;
   
    // Holds the values for guessed chars in word
    private char[] wordProgress = new char[WORD.length()];
    private char[] wordBank = new char[WORD.length()];
    private boolean isRunning = true;
    private int incorrectGuesses = 0;
   
    String[] hangmanArt = new String[] 
            { 
                    "  +---+\r\n" + 
                    "  |   |\r\n" + 
                    "      |\r\n" + 
                    "      |\r\n" + 
                    "      |\r\n" + 
                    "      |\r\n" + 
                    "=========", 
                    "+---+\r\n" + 
                    "  |   |\r\n" + 
                    "  O   |\r\n" + 
                    "      |\r\n" + 
                    "      |\r\n" + 
                    "      |\r\n" + 
                    "=========",
                    "  +---+\r\n" + 
                    "  |   |\r\n" + 
                    "  O   |\r\n" + 
                    "  |   |\r\n" + 
                    "      |\r\n" + 
                    "      |\r\n" + 
                    "=========",
                    " +---+\r\n" + 
                    "  |   |\r\n" + 
                    "  O   |\r\n" + 
                    " /|   |\r\n" + 
                    "      |\r\n" + 
                    "      |\r\n" + 
                    "=========",
                    " +---+\r\n" + 
                    "  |   |\r\n" + 
                    "  O   |\r\n" + 
                    " /|\\  |\r\n" + 
                    "      |\r\n" + 
                    "      |\r\n" + 
                    "=========",
                    " +---+\r\n" + 
                    "  |   |\r\n" + 
                    "  O   |\r\n" + 
                    " /|\\  |\r\n" + 
                    " /    |\r\n" + 
                    "      |\r\n" + 
                    "=========",
                    " +---+\r\n" + 
                    "  |   |\r\n" + 
                    "  O   |\r\n" + 
                    " /|\\  |\r\n" + 
                    " / \\  |\r\n" + 
                    "      |\r\n" + 
                    "=========",
            };
    
    // Variable: a holder for data held in memory
    // Scope: Defines where a variable or method can be accessed from within the program
   
    public static void main(String[] args) {
        System.out.println("Greetings! Starting game...");
        new Main();
       
    }
   
    public Main() {
        runGameLoop();
    }
   
    public void runGameLoop() {
        Scanner scanner = new Scanner(System.in);
        while(isRunning) {
            System.out.println("###########################");
            System.out.println("Please enter a letter");
            String nextLine = scanner.nextLine();
            if(nextLine.length() == 1 && !nextLine.isBlank()) {
                char input = nextLine.charAt(0);
                processInput(input);
                if(isGameOver()) 
                    isRunning = false;
            } else {
                System.out.println("Please only input one character");
                continue;
            }
        }
        
        System.out.println("Game Over!");
        System.out.println("Would you like to play again? (y/n)");
        String response = scanner.nextLine();
        if(response.equals("y"))
            restart();
         else 
            System.out.println("Thank you for playing!");
        
        scanner.close();
    }
 
    private void restart() {
        System.out.println("Restarting...");
        wordProgress = new char[WORD.length()];
        wordBank = new char[WORD.length()];
        isRunning = true;
        incorrectGuesses = 0;
        runGameLoop();
    } 
    
    /**
     * Handles game logic after user specifies input
     * @param input
     */
    private void processInput(char input) {
        if(charHasBeenGuessed(input)) {
            System.out.println("You've already guessed that!");
        } else {
            if(inputIsInWord(input)) {
                int index = WORD.toLowerCase().indexOf((input + "").toLowerCase());
                wordProgress[index] = input;
                System.out.println("You've guessed a character!");
                printFoundLetter();
            } else {
                wordBank[incorrectGuesses] = input;
                incorrectGuesses++;
                printNotInWord();
            }
        }
    }
 
    /**
     * @param Input
     * @return True or False depending on if the input is in the word bank
     */
    private boolean charHasBeenGuessed(char c) {
        for(char x : wordBank) {
            if((x + "").toLowerCase().equals((c + "").toLowerCase()))
                return true;
        }
        for(char x : wordProgress) {
            if((x + "").toLowerCase().equals((c + "").toLowerCase()))
                return true;
        }
        return false;
    }
   
    /**
     * Checks if the given input is in the word
     * @param input User in
     * @return
     */
    private boolean inputIsInWord(char input) {
        return WORD.toLowerCase().indexOf((input + "").toLowerCase()) != -1;
    }
    
    private void printNotInWord() {
        System.out.println("That character is not in the word!");
        System.out.println(hangmanArt[incorrectGuesses - 1]);
    }
    
    private void printFoundLetter() {
        String firstLine = "";
        String secondLine = "";
        for(int i = 0; i < WORD.length(); i++) {
            char letter = WORD.charAt(i);
            if(letter == ' ' || !charHasBeenGuessed(letter)) {
                firstLine += " ";
            } else {
                firstLine += letter;
            }
            if(letter == ' ') {
                secondLine += " ";
            } else {
                secondLine += "-";
            }
        }
        System.out.println(firstLine);
        System.out.println(secondLine);
    }
   
    private boolean isGameOver() {
        // check for too many incorrect answers
        if(incorrectGuesses == INCORRECT_GUESS_LIMIT) {
            System.out.println("You've guessed wrong too many times! You lose!");
            return true;
        }
        // check if player guessed entire word
        for(int i = 0; i < WORD.length(); i++) {
            char letter = WORD.charAt(i);
            if(letter != ' ' && !charHasBeenGuessed(letter)) 
                return false;
            }
        System.out.println("You've guessed the word! You win!");
        System.out.println("The word was: " + WORD);
        return true;
    }
}