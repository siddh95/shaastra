/*
 * Indexer.java
 *
 * Created on March 14, 2002, 10:30 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package Parser;

/**
 *
 * @author Belay
 */
import java.util.*;
import Stemmer.*;
import Database.*;
public class Indexer {
    Vector words;
    public Indexer(){
        words = new Vector ();
    }
    
    public void Index(String s, long doc_id){
        String word,stemmedword;
        int occurence;
        
        StringTokenizer tokens = new StringTokenizer(s," :<>'\",/\\\u135F\u1360\u1362\u1363\u1364\u1365\u1366\u1367\u1368");
        while(tokens.hasMoreTokens()){
            occurence=0;
            word= tokens.nextToken().trim();
            Stemmer stemmer = new Stemmer();
            char wordarray[] = word.toCharArray();
            
            stemmer.add(wordarray, wordarray.length);
            stemmer.stem();
            stemmedword= stemmer.toString();
            Iterator iterator= words.iterator();
            while(iterator.hasNext()){
             Word w= (Word) iterator.next();
             if(stemmedword.equals(w.getWord())){
                 occurence=w.getNo_of_occurences();
                 iterator.remove();
                 
             }
                 
                 
             }
            
            Word w= new Word();
            w.setWord(stemmedword);
            w.setDoc_id(doc_id);
            w.setNo_of_occurences(occurence+1);
            words.add(w);
            
            
        }
        
    }
    
    public void addToDB(){
        DBOperation.insertIntoWordTab(words);
    }
    
    
}
