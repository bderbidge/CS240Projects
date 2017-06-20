package hangman;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Created by brandonderbidge on 5/15/17.
 */

public class EvilHangmanGame implements IEvilHangmanGame {

    TreeSet<String> Hangman = new TreeSet<>();
    StringBuilder finalWord = new StringBuilder();
    TreeMap<String, TreeSet<String>> HangmanMap = new TreeMap<>();

    @Override
    public void startGame(File dictionary, int wordLength) throws FileNotFoundException {


        Scanner S = new Scanner(dictionary);
        S.useDelimiter("((#[^\\n]*\\n)|(\\s+))+");
        while (S.hasNext()) {

            String word = S.next();
            if (word.length() == wordLength) {
                Hangman.add(word);
            }

        }

        for (int i = 0; i < wordLength; i++) {
            finalWord.append('-');
        }


    }

    @Override
    public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {


        for (String word : Hangman) {

            StringBuilder tempWord = new StringBuilder();
            for (int i = 0; i < word.length(); i++)
                tempWord.append("-");


            for (int i = 0; i < word.length(); i++) {

                if (word.charAt(i) == guess)
                    tempWord.setCharAt(i, guess);

            }

            if (!HangmanMap.containsKey(tempWord.toString())) {

                TreeSet<String> tempSet = new TreeSet<>();
                tempSet.add(word);
                HangmanMap.put(tempWord.toString(), tempSet);

            } else {

                HangmanMap.get(tempWord.toString()).add(word);

            }



        }

        int value = 0;
        String tempWord = "";
        TreeSet<String> tempSet = new TreeSet<>();

        for(Map.Entry<String, TreeSet<String>> entry: HangmanMap.entrySet()){

            if(entry.getValue().size() > value){
                tempWord = entry.getKey();
                value = entry.getValue().size();
                tempSet = entry.getValue();
            }else if(value == entry.getValue().size()){

                int count = 0;
                int count2 = 0;
                for(int i = 0; i < entry.getKey().length(); i++){

                    if(entry.getKey().charAt(i) == guess)
                        count++;
                    if (tempWord.charAt(i) == guess)
                        count2++;
                }


                if(count == count2){

                    for(int i = entry.getKey().length(); i >= 0; i--){

                        if(entry.getKey().charAt(i)== guess && tempWord.charAt(i) != guess)
                        {
                            tempWord = entry.getKey();
                            value = entry.getValue().size();
                            tempSet = entry.getValue();
                            break;
                        }
                        else if(entry.getKey().charAt(i)!= guess && tempWord.charAt(i) == guess)
                            break;
                    }

                }else if(count > count2){
                    tempWord = entry.getKey();
                    value = entry.getValue().size();
                    tempSet = entry.getValue();
                }

            }
        }

        for(int i = 0; i < finalWord.length(); i++){

            if(tempWord.charAt(i) != '-') {
                finalWord.setCharAt(i, tempWord.charAt(i));
            }
        }

        System.out.println(finalWord);
        Hangman = tempSet;
        HangmanMap.clear();

        return Hangman;
    }
}

