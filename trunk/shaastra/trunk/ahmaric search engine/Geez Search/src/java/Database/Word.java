/*
 * WordTable.java
 *
 * Created on March 17, 2002, 4:48 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package Database;

/**
 *
 * @author Belay
 */
public class Word {
    private String word;
    private long doc_id;
    private int no_of_occurences;
    private int weightage=0;
    private int position;
    
    /** Creates a new instance of WordTable */
    public Word() {
    }
    public String getWord(){return word;}
    public long getDoc_id(){ return doc_id;}
    public int getNo_of_occurences() {return no_of_occurences;}
    public int getWeightage(){return weightage;}
    public int getPosition(){return position;}
    
    public void setWord(String w){word=new String(w);}
    public void setDoc_id(long id){ doc_id=id;}
    public void setNo_of_occurences(int no) { no_of_occurences=no;}
    public void setWeightage(int w){weightage=w;}
    public void setPosition(int p){position=p;}
}
