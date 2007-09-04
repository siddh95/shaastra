/*
 * DBOperation.java
 *
 * Created on March 15, 2002, 2:14 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package Database;


/**
 *
 * @author Belay
 */
import java.util.*;
import java.util.Date;
import java.sql.*;
import java.io.*;
import Stemmer.*;
public class DBOperation {
    static Connection con;
    static {
        try
        {
        Properties prop = System.getProperties();
        prop.put("user","");
        prop.put("password","");
        prop.put("charSet","UTF8");
        Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
        con=DriverManager.getConnection("jdbc:odbc:GeezSearch",prop);
        
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        
    }
     
     public static Vector searchDB(String search_string){
        Vector result= new Vector();
        Stemmer stemmer= new Stemmer();
        stemmer.add(search_string.toCharArray(),search_string.length());
        stemmer.stem();
        String stemmed_word= stemmer.toString();
         try{
             
                
                Statement stmt=con.createStatement();
                ResultSet r=stmt.executeQuery("select Doc_url,Title from Doc_tab, Word_tab where Doc_tab.Doc_id=Word_tab.Doc_id and Word='"+stemmed_word+"'");
                while(r.next()){
                    SearchResult w= new SearchResult();
                    w.setTitle(r.getString("Title"));
                    w.setLink(r.getString("Doc_url"));
                   result.add(w);
                }
                
                
                 stmt.close();
                       
            }
            catch(Exception e)
	    {
	     System.out.println("in insertDocTab: "+e);
	    }
        return result;
        }
    
       
    
    
}
