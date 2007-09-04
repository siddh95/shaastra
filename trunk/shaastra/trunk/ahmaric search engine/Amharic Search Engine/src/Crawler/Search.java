/*
 * Search.java
 *
 * Created on April 16, 2002, 10:36 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package Crawler;

/**
 *
 * @author Belay
 */
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.*;
import org.apache.lucene.search.Query;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hits;
import Analysis.*;

public class Search {
    
   
    static public long getLastModified (String url) throws Exception
    {
         long modified_date=0;
         String search_query="url:"+url;
         IndexSearcher is = new IndexSearcher("C:\\Users\\vijay\\Documents\\sanskrit search engine\\sanskrit search engine\\luceneindex");
         Analyzer analyzer = new AmharicAnalyzer();
         QueryParser parser = new QueryParser("url", analyzer);
         Query query = parser.parse(search_query);
         Hits hits =is.search(query);
         if(hits.length()>0) {
            
                    Long l=Long.getLong(hits.doc(0).get("last_updated"));
                    if(l!=null){
                        modified_date  =l.longValue();
                    }
                 //modified_date=999999999;  
        }
        is.close();
        return modified_date;
    }
    
    static public boolean isModified (String url, long date_last_modified) throws Exception
    {
         long date_modified=0;
         String search_query="url:"+url;
         IndexSearcher is = new IndexSearcher("C:\\Users\\vijay\\Documents\\sanskrit search engine\\sanskrit search engine\\luceneindex");
         Analyzer analyzer = new AmharicAnalyzer();
         QueryParser parser = new QueryParser("url", analyzer);
         Query query = parser.parse(search_query);
         Hits hits =is.search(query);
         if(hits.length()>0) {
            
                    Long l=Long.getLong(hits.doc(0).get("last_updated"));
                    if(l==null) return false;
                    
                     date_modified  =l.longValue();
                     if(date_last_modified >date_modified){
                         IndexReader reader = is.getIndexReader();
                         reader.deleteDocument(hits.id(0));
                         return true;
                     }
                    
                 
        }
        is.close();
        return false;
    }
    }

