package hangman;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.text.html.HTMLDocument;

/**
 * Created by brandonderbidge on 5/10/17.
 */

public class EvilHangmanGame implements IEvilHangmanGame{

    private TreeSet<String> Hangman = new TreeSet<String>();
    private TreeSet<String> charsGuessed = new TreeSet<String>();
    private Map<String, TreeSet<String>> wordGuess = new TreeMap<>();
    private StringBuffer finalWord = new StringBuffer();

    /**
     * Starts a new game of evil hangman using words from <code>dictionary</code>
     * with length <code>wordLength</code>.
     *	<p>
     *	This method should set up everything required to play the game,
     *	but should not actually play the game. (ie. There should not be
     *	a loop to prompt for input from the user.)
     *
     * @param dictionary Dictionary of words to use for the game
     * @param wordLength Number of characters in the word to guess
     */
    @Override
    public void startGame(File dictionary, int wordLength) throws FileNotFoundException {

        for(int i = 0; i < wordLength; i++)
            finalWord.append("-");


        Scanner S = new Scanner(dictionary);
        S.useDelimiter("((#[^\\n]*\\n)|(\\s+))+");
        while (S.hasNext()) {
            String word = S.next();
            if (word.length() == wordLength)
                Hangman.add(word);
        }
    }

    /**
     * Make a guess in the current game.
     *
     * @param guess The character being guessed
     *
     * @return The set of strings that satisfy all the guesses made so far
     * in the game, including the guess made in this call. The game could claim
     * that any of these words had been the secret word for the whole game.
     *
     * @throws GuessAlreadyMadeException If the character <code>guess</code>
     * has already been guessed in this game.
     */
    @Override
    public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {


        String guess1 = String.valueOf(guess);
        if (charsGuessed.contains(guess1))
            throw new GuessAlreadyMadeException();
        charsGuessed.add(guess1);

        TreeSet<String> temp = new TreeSet<String>();
        temp.addAll(Hangman);
        Hangman.clear();

        for (String letter: temp) {
            StringBuilder tempString = new StringBuilder();

                int index = letter.indexOf(guess);

                for(int i = 0; i < letter.length(); i++)
                {
                    tempString.append("-");
                }

                while(index >= 0){

                    tempString.setCharAt(index, guess);
                    index = letter.indexOf(guess, index + 1);
                }

                if(wordGuess.containsKey(tempString.toString())) {

                    TreeSet<String> tempSet = new TreeSet<>();
                    tempSet = wordGuess.get(tempString.toString());
                    tempSet.add(letter);
                    wordGuess.put(tempString.toString(), tempSet );

                }else {

                    TreeSet<String> tempSet = new TreeSet<>();
                    tempSet.add(letter);
                    wordGuess.put(tempString.toString(), tempSet);
                }

        }





        TreeSet<String> tempSet = new TreeSet<>();
        String tempWord = "";





        for(Map.Entry<String, TreeSet<String>> entry: wordGuess.entrySet()){

            if(entry.getValue().size() > tempSet.size() ){
                tempWord = entry.getKey();
                tempSet = entry.getValue();
            }
            else if(entry.getValue().size() == tempSet.size()) {

                int count1 = 0;
                int count2 = 0;
                for (int i = 0; i < entry.getKey().length() - 1; i++) {

                    if (entry.getKey().charAt(i) == guess)
                        count1++;

                    if (tempWord.charAt(i) == guess)
                        count2++;

                }

                if (count1 == count2) {

                    //first make sure to check which has the least number of letter occurances.
                    for (int i = entry.getKey().length() - 1; i >= 0; i--) {

                        if (tempWord.charAt(i) == guess && tempWord.charAt(i) != entry.getKey().charAt(i))
                        {
                            break;

                        } else if (entry.getKey().charAt(i) == guess && entry.getKey().charAt(i) != tempWord.charAt(i)) {

                            tempWord = entry.getKey();
                            tempSet = entry.getValue();
                            break;

                        }
                    }
                }
                else if(count1 < count2){
                    tempWord = entry.getKey();
                    tempSet = entry.getValue();
                }
            }
        }
        for (int i = 0; i <finalWord.length(); i++){

            if(tempWord.charAt(i) != '-')
                finalWord.setCharAt(i, tempWord.charAt(i));
        }

        System.out.println("Word: " + finalWord);

        if(tempWord.contains(guess1))
        {
            System.out.println("Yes, there is a " + guess);
        }
        else{
            System.out.println("Sorry there are no " + guess + "'s");
        }

        Hangman = tempSet;
        wordGuess.clear();


        return Hangman;
    }

    public Set<String> getMySet(){

        return charsGuessed;
    }

    public void winGame(){

        System.out.println("You Win!");
        System.out.println("The word is: " + Hangman.first());
    }

    public void loseGame(){

        System.out.println("You lose!");
        System.out.println("The word was: " + Hangman.first());
    }
}


