/*
 * SearchResult.java
 *
 * Created on April 15, 2002, 11:57 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package SearchDB;
import java.util.*;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hits;
import Analysis.*;
/**
 *
 * @author Belay
 */
public class SearchResult {
    IndexSearcher is;
    Hits hits;
    String user_query;
    String time_taken;
    /** Creates a new instance of SearchResult */
    public SearchResult() throws Exception {
       
       IndexSearcher is = new IndexSearcher("c:\\luceneindex");
       Analyzer analyzer = new AmharicAnalyzer();
    }
    
    public void searchIndex (String userquery) throws Exception{
        user_query=userquery;
        Date start = new Date();
       
        String search_query="title:"+userquery+ " OR content:"+userquery + " OR body:"+userquery;
        IndexSearcher is = new IndexSearcher("c:\\luceneindex");
        Analyzer analyzer = new AmharicAnalyzer();
        QueryParser parser = new QueryParser("content", analyzer);
        Query query = parser.parse(search_query);
        hits =is.search(query);
        Date end = new Date();
        long t=(end.getTime()-start.getTime());
        time_taken=String.valueOf(t/1000.)+" seconds";
    }
    
    public int totlaDocmentsFound(){
        return hits.length();
    }
    public String getUrl(int i) throws Exception
    {
        if(i<hits.length())
        return hits.doc(i).get("url");
        return "";
    }
    public String getTitle(int i) throws Exception
    {
        if(i<hits.length())
        return hits.doc(i).get("title");
         return "";
    }
    public String getContent(int i) throws Exception
    {
        if(i<hits.length())
        return hits.doc(i).get("content");
         return "";
    }
    public String getSize(int i) throws Exception
    {
        String s="";
        if(i<hits.length()){
         int n= Integer.parseInt(hits.doc(i).get("size"));
         if (n!=-1) n=n/1000;
         if(n==0) n=1;
         s= "-"+ String.valueOf(n)+"k";
         
        }
         return s;
    }
    
    public void close() throws Exception{
        is.close();
    }
     public String getUserQuery() throws Exception
    {
        return user_query;
    }
}
    

