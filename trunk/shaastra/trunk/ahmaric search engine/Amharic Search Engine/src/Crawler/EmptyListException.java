/*
 * EmptyListException.java
 *
 * EmptyListException defines an exception to be used Queue List
 */

package Crawler;


class EmptyListException extends RuntimeException {
      
         // no-argument constructor
         public EmptyListException()
         {
          this( "List" );   // call other EmptyListException constructor
       }
    
       // constructor
       public EmptyListException( String name )
       {
          super( name + " is empty" );  // call superclass constructor
       }
    
    } // end class 
