package spell;

import java.lang.reflect.Array;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by brandonderbidge on 5/5/17.
 */

public class Trie implements ITrie {

    private WordNode root = new WordNode();
    private int wordCount = 0;
    private int nodeCount = 1;
    private StringBuilder ultimateString = new StringBuilder();
    SortedSet<String> trieSet = new TreeSet<>();

    public void add(String word) {



        int index = 0;
        int lengthOfWord = word.length();
        root.addNode(word, index, lengthOfWord);


    }


    public ITrie.INode find(String word) {

        int index = 0;
        int lengthOfWord = word.length();

        return root.findNode(word, index, lengthOfWord) ;
    }


    public int getWordCount() {
        return wordCount;
    }



    public int getNodeCount() {
        return nodeCount;
    }

    @Override
    public String toString() {

        StringBuilder str = new StringBuilder(), str2 = new StringBuilder();

        for (int i = 0; i < root.alphabet.length; i++) {


            if(root.alphabet[i] != null){

               int x = i + 'a';
                char c = (char)x;
                str.append(c);





               str2.append(root.alphabet[i].getString(str));
                str.deleteCharAt(str.length() -1);
            }
        }



        return str2.toString();
    }

    @Override
    public int hashCode() { return (wordCount * nodeCount); }

    @Override
    public boolean equals(Object o) {
        if (o != this) {
            return false;
        } else if (o.getClass() != this.getClass()) {
            return false;
        }
        else {
            Trie temp = (Trie) o;

            if (temp.hashCode() != this.hashCode()) {
                return false;
            }
            if(this.toString() == temp.toString()){
                return true;
            }else {
                return false;
            }
        }
    }

    public class WordNode implements ITrie.INode {

        private int frequency = 0;
        private WordNode alphabet[] = new WordNode[26];;

        public int getValue() {
            return frequency;
        }

        public void addNode(String word, int index, int lengthOfWord) {


            if(index == lengthOfWord){

                if(frequency == 0)
                    wordCount++;;

                frequency++;
                return;
            }
            else{
                int nodeIndex = word.charAt(index) - 'a';
                index++;

                if(alphabet[nodeIndex] == null)
                    alphabet[nodeIndex] = new WordNode();

                alphabet[nodeIndex].addNode(word, index, lengthOfWord);
            }
            nodeCount++;


        }

        public ITrie.INode findNode(String word, int index, int lengthOfWord){

            if(index == lengthOfWord){
                if(frequency > 0)
                    return this;
                else
                    return null;
            }
            else{

                int nodeIndex = word.charAt(index) - 'a';
                index++;

                if(alphabet[nodeIndex] == null)
                    return null;

                return alphabet[nodeIndex].findNode(word, index, lengthOfWord);

            }

        }

        public StringBuilder getString( StringBuilder str){

            StringBuilder str2 = new StringBuilder();

            if(frequency > 0 )
            {
               str2.append(str + "\n");
            }
            for(int i = 0; i < alphabet.length; i ++){

                if(alphabet[i] != null){

                    int x = i + 'a';
                    char c = (char)x;

                    str.append(c);

                    str2.append(alphabet[i].getString(str));

                    str.deleteCharAt(str.length() - 1);

                }
            }

            return str2;
        }


    }
}