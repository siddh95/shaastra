package SearchDB;
/*
 * Search.java
 *
 * Created on March 19, 2002, 8:42 AM
 */

import java.io.*;
import java.net.*;
import java.util.*;
import Database.*;

import javax.servlet.*;
import javax.servlet.http.*;
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
import Search.*;
import java.util.*;

/**
 *
 * @author Belay
 * @version
 */
public class Search extends HttpServlet {
   
    
    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException{
        
        HttpSession session = request.getSession(true);
        SearchResult search_result = (SearchResult) session.getAttribute("geez.result");
        String pagerequested = request.getParameter("pagenumber");
        String userquery=request.getParameter("query");
        PrintWriter out;
        int page_no=1;
       
        
        
         if (!(search_result!=null && pagerequested!=null)) 
         {
             try{
            search_result = new SearchResult();
            search_result.searchIndex(userquery);
            session.setAttribute("geez.result",search_result);
            page_no=1;
             }catch(Exception e){}
         }
         else{
              page_no= Integer.parseInt(pagerequested);   
         }
         
        response.setContentType("text/html;charset=UTF-8");
        
        out = response.getWriter();
         try{       
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Result</title>");
        out.println("<style type = 'text/css'>.url{color:green; font-size:12pt}" +
                "a:link{color:#00c}" +
                ".a,.a:link{color:green}" +
                ".t{background:#e5ecf9;color:#000}" +
                 ".f{color:black;font-size:12pt}" +
                "</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<table width = '100%'>");
        out.println("<tr>");
        out.println("<td>");
        out.println("<img src=\"images/Geezsmall.gif\" align=\"left\" width=\"100\" alt=\"logo\"/>");
        
      out.println( "<form method =\"get\" action= \"/Geez_Search/Search\">");
          out.println(" <input value=\""+ search_result.getUserQuery()+"\" type=\"text\" name= \"query\" size=\"40\">");
          out.println("<input type=\"submit\" value=\"\u1348\u120D\u130Dsearch\">");
      out.println("</form>");
      
      out.println(" </td>");
         out.println("</tr>");
        out.println("<tr><td bakground ='yellow'>");
        TextFragmenter fragmented_text= new TextFragmenter(search_result.getUserQuery());
               
         out.println("<font class='f'> \u1208 " +search_result.getUserQuery() + "  "+ search_result.totlaDocmentsFound() + " \u12EB\u1205\u120D \u12CD\u1324\u1275 \u1270\u1308\u129D\u1277\u120D" + "</font><br>");
         out.println("<font class='f'>" + search_result.totlaDocmentsFound()+ " Results found for "+ search_result.getUserQuery() +" search took " + search_result.time_taken+"</font>");
         
         for (int i=(page_no-1)*10; i<(page_no-1)*10+10; i++) {
             
            
            out.println("<p><a href=" + search_result.getUrl(i) + ">"+ search_result.getTitle(i) + "</a>");
            out.println("<br><font class=f>"+fragmented_text.highlightedBestFragmentedText(search_result.getContent(i))+"</font>");
            out.println("<br><font class=url>"+ search_result.getUrl(i)+"  " + search_result.getSize(i)+ "</font></p>");
        }
         if(search_result.totlaDocmentsFound()/10<page_no+11) page_no=search_result.totlaDocmentsFound()/10+1;
         for(int i= 1;i<=search_result.totlaDocmentsFound()/10+1;i++){
           out.print("<a href=/Geez_Search/Search?pagenumber="+i+">"+i+"</a> ");
         }
             
         
         
        }catch(Exception e){
         out.println("<br><font class=url>"+e+"</font></p>");}
        out.println("</tr></td></table>");
        out.println("</body>");
        out.println("</html>");
        out.close();
    }
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException{
        processRequest(request, response);
    }
    
    /** Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /** Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Short description";
    }
    // </editor-fold>
}