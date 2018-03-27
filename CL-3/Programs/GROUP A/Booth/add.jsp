<%-- 

    Document   : add

    Created on : 19 Jan, 2016, 10:28:16 AM

    Author     : comp-sl1-32

--%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ page import="algo.Booth" %>

<%@ page import="java.util.*"%>

<!DOCTYPE html>



<html>

    <head>

        <title>Enter two numbers to add up</title>

    </head>

   

    <body>

    <% Booth b = new Booth();%>

    <%= "<h1> Multiplication is "+ b.multiply(Integer.parseInt(request.getParameter("t1")),Integer.parseInt(request.getParameter("t2")))+"</h1>"%>

        



    </body>

</html>

