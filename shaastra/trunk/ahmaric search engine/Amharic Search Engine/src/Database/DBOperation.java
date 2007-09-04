/*
 * DBOperation.java
 *
 * DBOperation performs all the neccessary database operation
 */

package Database;



import java.util.*;
import java.util.Date;
import java.sql.*;
import java.io.*;
/*
 * C;ass DBOperation contains static methods for working in database
 */
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
    /*
     * insertIntoDocTab- inserts in the table Doc_tab the given details
     */
    public static void insertIntoDocTab( long doc_id,
                                         String doc_url,
                                         String title,
                                         int lastupdate,
                                         int outgoing_links){
        
        if(title.indexOf("'")!=-1)
	 {
	    title=title.replaceAll("'"," ");
	  }

         try
	 {
	    Statement stmt=con.createStatement();
	    stmt.execute("insert into Doc_tab values("+doc_id+ ",'" +doc_url+ "','" + title + "',"+lastupdate+ ","+ outgoing_links+ ",0);");
            stmt.close();
	 }
	  catch(Exception e)
	  {
	     System.out.println("in insertDocTab: "+e);
	  }
    }
    
    public static void insertIntoWordTab( Vector details ){
       
        Vector detail;
        Iterator iterator= details.iterator();
        while(iterator.hasNext()){
           Word word= (Word) iterator.next();
           try{
                      
                 Statement stmt=con.createStatement();
                 stmt.execute("insert into Word_tab values('"+
                         word.getWord() + "'," +
                         word.getDoc_id() + "," + 
                         word.getNo_of_occurences() + "," +
                         word.getPosition() + "," +
                         word.getWeightage() + ",0,0);");
                         stmt.close();
                       
            }
            catch(Exception e)
	    {
	     System.out.println("in insertDocTab: "+e);
	    }
        }
       
    }
    public static long getMaxDocId(){
        long max_doc_id=-1;
	  try
	  {
		
               Statement stmt=con.createStatement();
	       ResultSet rset=	stmt.executeQuery("select max(Doc_id) from Doc_tab");
		if(rset.next())
	        	max_doc_id=rset.getLong(1);
		rset.close();
		stmt.close();
		
	}
	 catch(Exception e)
	{
		System.out.println("in findMaxDoc "+e);
		e.printStackTrace();
		
	}
        return ++max_doc_id;

}
    
     public static void getfromDB(){
       
         try{
                      
                 Statement stmt=con.createStatement();
                ResultSet r=stmt.executeQuery("select * from Word_tab");
                 FileOutputStream fos = new FileOutputStream("C:\\Users\\vijay\\Documents\\sanskrit search engine\\sanskrit search engine\\dbresult.txt");
                Writer out = new OutputStreamWriter(fos, "UTF8");
                while(r.next())
                    out.write(r.getString("Word")+"\r\n");
               
                out.close();
                
                 stmt.close();
                       
            }
            catch(Exception e)
	    {
	     System.out.println("in insertDocTab: "+e);
	    }
        }
     public static void main(String args[]){
        System.out.print("Whats up....");
       DBOperation.getfromDB();
           
        
    }
       
    
    
}
