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

import hangman.IEvilHangmanGame;
import hangman.EvilHangmanGame;

/**
 * Created by derbidgb on 5/11/2017.
 */


public class Main {

    /**
     * Give the dictionary file name as the first argument and the word to correct
     * as the second argument.
     */
    public static void main(String[] args) throws IOException, IEvilHangmanGame.GuessAlreadyMadeException {

        String FileName = args[0];
        int wordLength = Integer.parseInt(args[1]);
        int guesses = Integer.parseInt(args[2]);

        EvilHangmanGame evilGame = new EvilHangmanGame();

        File f = new File(FileName);

        if(!f.exists())
            return;

        evilGame.startGame(f, wordLength);
        TreeSet<String> charSet = new TreeSet<>();

        while (guesses != 0) {

            System.out.println("You have " + guesses + " left");
            System.out.println("Used letters: ");
            for (String word: charSet) {
                System.out.print(word + " ");
            }
            System.out.println("Enter guess: ");
            InputStreamReader r = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(r);
            String guess = br.readLine();

            Pattern p = Pattern.compile("^[a-z]");
            Matcher m = p.matcher(guess);
            boolean b = m.matches();

            while (guess.length() > 1 || !b){

                System.out.println("Enter guess: ");
                r = new InputStreamReader(System.in);
                br = new BufferedReader(r);
                guess = br.readLine();

                p = Pattern.compile("^[a-z]");
                m = p.matcher(guess);
                b = m.matches();

            }

            char letter = guess.charAt(0);
            TreeSet<String> wordSet = new TreeSet<>();
            wordSet.add(guess);
            boolean valid = false;
            while (valid){



            }



            if(evilGame.makeGuess(letter).size() == 1){
                //Win Game
            }

            guesses--;
        }

        //End Game


    }
}

