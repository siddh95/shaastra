<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%--
The taglib directive below imports the JSTL library. If you uncomment it,
you must also add the JSTL library to the project. The Add Library... action
on Libraries node in Projects view can be used to add the JSTL 1.1 library.
--%>
<%--
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
--%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <Title>Shaastra Sanskrit Search‌ Engine</Title>
</head>
<body>

 <form method ="get" action= "/Geez_Search/Search">
     
<table width="100%">
    <tr><td align="center"><img src="images/Geez3.gif" alt="Shaastra"/>
    </td></tr>
     <tr><td align="center">
            <input type="text" name= "query" size="50">
         </td>
</tr>
<tr>
<td align="center">
       
         <input type="submit" value=<%="Search" %> >
         
       </td>
</tr>
</table>
</form>
    
    </body>
</html>
