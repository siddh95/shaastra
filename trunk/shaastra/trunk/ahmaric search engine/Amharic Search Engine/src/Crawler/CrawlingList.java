/*
 * CrawlingList.java
 *
 * CrawlingList implements the FIFO datastructure used for the crawling
 * process
 */

package Crawler;


import java.util.*;
import java.io.*;
public class CrawlingList {
    private Queue links_queue; /* stores list documents to be crawled */
    private Vector crawled_list; /* stores list of crawled documents */
    private Vector error_pages;
    
    /** Creates a new instance of CrawlingList */
    public CrawlingList() {
        links_queue= new Queue();
        crawled_list= new Vector();
        error_pages= new Vector();
        
    }
    /** Creates a new instance of CrawlingList from a file */
    public CrawlingList(String filename){
        
    }
    
    /** adds a new link to the CrawlingList */
    public void addLink(String link){
        links_queue.enqueue(link);
       // System.out.println("****************QUEUe check"+links_queue.isEmpty());
    }
    /** adds a new links(in vector form) to crawlingList */
    public void addLink(Vector link){
        if(!link.isEmpty()){
            Enumeration items = link.elements();
            while(items.hasMoreElements()){
            links_queue.enqueue(items.nextElement().toString());
             
            }
         // System.out.println("****************QUEUe check"+links_queue.isEmpty());
       }
      
       
    }
    
    /** This returns a link to be crawled from the queue. Before returns it verifies 
     *  whether the link is crawled or not.
     */ 
    public String getLink(){
        while(!links_queue.isEmpty()){
            String url=links_queue.dequeue().toString();
            if(!crawled_list.contains(url)) return url;
            
        }
       /// System.out.println("List Empty");
        return null;
    }
    /** This add a link to the crawled list */
    public void addToCrawledList(String link){
        crawled_list.add(new String(link));
        
        
    }
    
    
    
     /** This add a link to the crawled list */
    public void addToErorPagesList(String link){
        error_pages.add(new String(link));
        
    }
    public void addToErorPagesList(Vector link){
        error_pages= new Vector(link);
        
    }
     public void addToCrawledList(Vector link){
         
         crawled_list = new Vector(link);
        
    }
     
    public Vector getCrawledList(){
         
        return crawled_list;
        
    }
    public Vector getLinksTocrawl(){
         
        return links_queue.getQueueElements();
        
    }
    public Vector getErrorPages(){
         
        return error_pages;
        
    }
     public void print(){
         
        links_queue.print();
        
    }
    public void saveList(){
        //code saving lists gose here
        
        try{
            //BufferedWriter out= new BufferedWriter(new FileWriter("c:\\CrawlingInfo\\links_to_crawl.txt"));
            FileOutputStream fo= new FileOutputStream("C:\\Users\\vijay\\Documents\\sanskrit search engine\\sanskrit search engine\\CrawlingInfo\\links_to_crawl.txt");
            OutputStreamWriter osw = new OutputStreamWriter(fo,"UTF8");//
            BufferedWriter out= new BufferedWriter(osw);
            String link;
            while((link=getLink())!=null) 
            {
            out.write(link);
            out.newLine();
            }
            out.flush();
            out.close();
            fo= new FileOutputStream("C:\\Users\\vijay\\Documents\\sanskrit search engine\\sanskrit search engine\\CrawlingInfo\\crawled_list.txt");
           osw = new OutputStreamWriter(fo,"UTF8");//
           out= new BufferedWriter(osw);
           // out= new BufferedWriter(new FileWriter("c:\\CrawlingInfo\\crawled_list.txt"));
            if(!crawled_list.isEmpty()){
                Enumeration items = crawled_list.elements();
                 while(items.hasMoreElements()){
                 //JAYA Im trying here 
                    //System.out.println("Im here");
                    String tmp=items.nextElement().toString();
                     out.write(tmp);
                   //  System.out.println("***** Writing links to file:"+tmp+"\n");
                         out.newLine();
           
                }
            }
            
            out.flush();
            out.close();
             fo= new FileOutputStream("C:\\Users\\vijay\\Documents\\sanskrit search engine\\sanskrit search engine\\CrawlingInfo\\error_pages_list.txt");
           osw = new OutputStreamWriter(fo,"UTF8");//
           out= new BufferedWriter(osw);
         //   out= new BufferedWriter(new FileWriter("c:\\CrawlingInfo\\error_pages_list.txt"));
            if(!error_pages.isEmpty()){
                Enumeration items = crawled_list.elements();
                 while(items.hasMoreElements()){
                     out.write(items.nextElement().toString());
                         out.newLine();
           
                }
            }
            
            out.flush();
            out.close();
        }
        catch(Exception e)
        {
            
        }
       
    }
    public void openSavedList(){
        //opening goes here
    }
}
    

