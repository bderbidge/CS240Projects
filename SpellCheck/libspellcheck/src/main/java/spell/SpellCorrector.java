package spell;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by brandonderbidge on 5/5/17.
 */

public class SpellCorrector implements ISpellCorrector {

    private ITrie TrieStructure;
    private Set<String> wordSet = new TreeSet();

    public void useDictionary(String dictionaryFileName) throws IOException {

        TrieStructure = new Trie();
        
        FileReader f = new FileReader(dictionaryFileName);
        Scanner S = new Scanner(f);
        S.useDelimiter("((#[^\\n]*\\n)|(\\s+))+");

        while (S.hasNext() == true)
            TrieStructure.add(S.next());

        TrieStructure.toString();

    }


    public String suggestSimilarWord(String inputWord) {

        inputWord.toLowerCase();

        DoTheThings(inputWord);

        int value = 0;
        String myWord = "";

        for (String word: wordSet) {

            if(TrieStructure.find(word) != null) {

                if (value < TrieStructure.find(word).getValue()){
                    myWord = word;
                    value = TrieStructure.find(word).getValue();
            }


            }
        }

        if(myWord.equals("")){

            Set<String> wordSet2 = new TreeSet();
            wordSet2 = wordSet;

            for (String word:wordSet2) {

                DoTheThings(word);
            }

            for (String word: wordSet) {

                if(TrieStructure.find(word) != null) {

                    if (value < TrieStructure.find(word).getValue()){
                        myWord = word;
                        value = TrieStructure.find(word).getValue();
                    }


                }
            }

        }
        return myWord;
    }

    public void DoTheThings(String inputWord){



        StringBuilder str = new StringBuilder();
        str.append(inputWord);
        for(int i = 0; i < inputWord.length(); i ++) {
            for (char x = 'a'; x <= 'z'; x++) {
                str.insert(i,x);
                wordSet.add(str.toString());
                str.deleteCharAt(i);
            }
        }

        for(int i = 0; i < inputWord.length(); i++){

            char c = str.charAt(i);
            str.deleteCharAt(i);
            wordSet.add(str.toString());
            str.insert(i, c);
        }

        for (int i = 0; i <inputWord.length(); i ++){
            char c = str.charAt(i);
            for(char x = 'a'; x <= 'z'; x++){

                str.setCharAt(i, x);
                wordSet.add(str.toString());

            }
            str.setCharAt(i, c);
        }

        for(int i = 0; i < inputWord.length(); i++){

            for (int j = 0; i < inputWord.length(); i++){

                char d = str.charAt(j);
                char e = str.charAt(i);
                str.setCharAt(i, d);
                str.setCharAt(j,e);
                //str.setCharAt();
                wordSet.add(str.toString());

                str.setCharAt(j, d);
                str.setCharAt(i,e);
            }

        }

    }
}
