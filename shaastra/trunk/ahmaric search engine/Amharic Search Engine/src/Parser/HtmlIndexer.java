/*
 * HtmlIndexer.java
 *
 * Created on April 15, 2002, 5:22 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package Parser;

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
 *
 */
public class HtmlIndexer {
    static String indexDirectory = "c:\\luceneindex";
    static IndexWriter writer;
    static AmharicAnalyzer analyzer;
     
   
   static {
        try{
          analyzer= new AmharicAnalyzer();
          writer =  new IndexWriter(indexDirectory, analyzer,true);
        }
        catch(Exception e)
        {
            System.out.println("Error in htmlindexer:"+e);
        }
    }
    
    static public void indexDocument(String url,
                                    String title,
                                    String content,
                                    String last_updated,
                                    String size)throws Exception{
      Document document = new Document();
     /* System.out.println("url "+url);
      System.out.println("title"+title);
      System.out.println("Content"+content);
      System.out.println("Last_updated"+last_updated);
      System.out.println("Size"+size);
      */
      document.add(new Field("url",url,Field.Store.YES, Field.Index.UN_TOKENIZED));
      document.add(new Field("title",title,Field.Store.YES, Field.Index.TOKENIZED));      
      document.add(new Field("content",content,Field.Store.YES, Field.Index.TOKENIZED));
     document.add(new Field("body",content,Field.Store.YES, Field.Index.UN_TOKENIZED));
      document.add(new Field("last_updated",last_updated,Field.Store.YES, Field.Index.UN_TOKENIZED));      
      document.add(new Field("size",size,Field.Store.YES,Field.Index.NO));
     writer.addDocument(document);

    }
    
    static public void optimizeIndex () throws Exception
    {

        writer.optimize();
        writer.close();       
    }
}




