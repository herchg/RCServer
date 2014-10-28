<%-- 
    Document   : test_db
    Created on : Oct 27, 2014, 11:31:19 PM
    Author     : hermeschang

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

--%>
<%--<%@page import="java.sql.Connection, java.sql.SQLException, javax.naming.Context, javax.naming.InitialContext, javax.naming.NamingException, javax.sql.DataSource, wi.core.db.DSConn" %>--%>
<%@page import="java.sql.Connection, wi.core.db.DSConn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
<%
        Connection conn = null;
        try {
//            Context initContext = new InitialContext();
//            Context envContext  = (Context)initContext.lookup("java:/comp/env");
//            DataSource ds = (DataSource)envContext.lookup("jdbc/RcDB");
//            
//            conn = ds.getConnection();
            conn = DSConn.getConnection("jdbc/RcDB");
%>
            <%=conn.toString() %>
<%
            
//        } catch (NamingException | SQLException ex) {
        } catch (Exception ex) {
%>
            <%=ex.getMessage() %>
<%            
        } finally {
%>
            <h1>close connection! 0</h1>
<%
            if (conn != null) {
                try {
%>
            <h1>close connection! 1</h1>
<%
                    conn.close();
%>
            <h1>close connection! 2</h1>
<%
                } catch (Exception ex) {
                }
            }
            conn = null;
        }
%>
    </body>
</html>
