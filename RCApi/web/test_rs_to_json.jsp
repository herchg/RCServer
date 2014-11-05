
<%@page import="java.sql.Connection, wi.core.db.DSConn, wi.core.util.json.JsonUtil" %>
<%@page import="com.google.gson.JsonElement" %> 

<%@page import="java.sql.PreparedStatement, java.sql.ResultSet, java.sql.ResultSetMetaData" %>
<%@page import="wi.core.util.json.GsonUtil, com.google.gson.Gson" %>

<%@page import="java.util.List, java.util.ArrayList, java.util.Map, java.util.LinkedHashMap" %>

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
            conn = DSConn.getConnection("jdbc/RcDB");
            
            PreparedStatement pStmt = conn.prepareStatement("SELECT `order_id`, `customer_id`, `company_id`, `store_id`, `pos_id`, `employee_id`, `ncode`, `total_amount`, `order_datetime`, `log_datetime`, `status`, `pos_order_id`, `memo` FROM `order` WHERE order_id > 0 AND order_id < 4");
            ResultSet rs = pStmt.executeQuery();
            
            JsonElement jsonElement = JsonUtil.toJsonArray(rs);
%>
            jsonElement String: <%=jsonElement.toString() %>
<%
            rs.beforeFirst();
            JsonElement jsonElement2 = JsonUtil.toJsonElement(rs);
%>
            jsonElement2 String: <%=jsonElement2.toString() %>
<%
        } catch (Exception ex) {
%>
            <%=ex.getMessage() %>
<%            
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception ex) {
                }
            }
            conn = null;
        }
%>
    </body>
</html>
