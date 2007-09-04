/*
 * Queue.java
 *
 * Queue implements the Queue data structure to be used by the crawler.
 */

package Crawler;
import java.util.*;

 public class Queue {
         private List queueList;
      
         // construct queue
         public Queue() 
       { 
          queueList = new List( "queue" );
       }
    
       // add object to queue
       public synchronized void enqueue( Object object )
       { 
         queueList.insertAtBack( object );
        //   queueList.insertAtFront( object );
       }
    
       // remove object from queue
       public synchronized Object dequeue() throws EmptyListException
       { 
          return queueList.removeFromFront();
       }
// determine if queue is empty
       public synchronized boolean isEmpty()
       {
          return queueList.isEmpty();
       }
    
       // output queue contents
       public synchronized Vector getQueueElements()
       {
          return queueList.getElements();
       }
        public synchronized void print()
       {
          queueList.print();
       }
    
   } // end class Queue
