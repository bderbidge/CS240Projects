import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hangman.EvilHangmanGame;
import hangman.IEvilHangmanGame;

/**
 * Created by derbidgb on 5/11/2017.
 */


public class Main {

    /**
     * Give the dictionary file name as the first argument and the word to correct
     * as the second argument.
     */
    public static void main(String[] args) throws IOException {

        String dictionaryFileName = args[0];
        int wordLength = Integer.parseInt(args[1]);
        int guesses = Integer.parseInt(args[2]);

        if (wordLength < 2)
            return;
        else if (guesses < 1)
            return;

        File f = new File(dictionaryFileName);

        if (!f.exists())
            return;
        /**
         * Create an instance of your corrector here
         */
        EvilHangmanGame Game = new EvilHangmanGame();

        Game.startGame(f, wordLength);

        InputStreamReader r = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(r);
        while (guesses != 0) {

            boolean validInput = false;

            System.out.println("You have " + guesses + " guesses left");
            System.out.print("Used Letters: ");
            for (String letter : Game.getMySet()) System.out.print(letter + " ");
            System.out.println();
            System.out.println("Enter a guess: ");
            String guess = br.readLine();
            Pattern p = Pattern.compile("^[a-z]");
            Matcher m = p.matcher(guess);
            boolean b = m.matches();

            while (!validInput) {

                if (guess.length() > 1 || !b) {
                    System.out.println("Please enter a lowercase letter");
                    guess = br.readLine();
                    p = Pattern.compile("^[a-z]");
                    m = p.matcher(guess);
                    b = m.matches();
                } else validInput = true;
            }

            char validGuess = guess.charAt(0);

            try {

                Set<String> newstring = new TreeSet<>();
                newstring = Game.makeGuess(validGuess);

                if ( newstring.size() == 1) {
                    Game.winGame();
                    return;
                }
                guesses--;


            } catch (IEvilHangmanGame.GuessAlreadyMadeException e) {
                System.out.println("You already entered " + validGuess + ", please enter a different letter.");
            }

        }
        Game.loseGame();
    }
}

