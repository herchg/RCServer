
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
            
            PreparedStatement pStmt = conn.prepareStatement("SELECT `order_id`, `customer_id`, `company_id`, `store_id`, `pos_id`, `employee_id`, `ncode`, `total_amount`, `order_datetime`, `log_datetime`, `status`, `pos_order_id`, `memo` FROM `order` WHERE order_id = 1");
            ResultSet rs = pStmt.executeQuery();
            
            try {
                List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
                ResultSetMetaData metaData = rs.getMetaData();
                while (rs.next()) {

                    Map<String, Object> map = new LinkedHashMap<String, Object>();
                    for (int i = 1; i <= metaData.getColumnCount(); i++) {
                        String columnType = metaData.getColumnTypeName(i);
                        String columnLabel = metaData.getColumnLabel(i);
                        out.println(columnLabel);
                        out.println("-");
                        out.println(columnType);
                        out.println();
//                        switch (columnType) {
//                            case "String": {
//                                map.put(columnLabel, rs.getString(columnLabel));
//                            }
//                                break;
//                            case "Integer": {
//                                map.put(columnLabel, rs.getInt(columnLabel));
//                            }
//                                break;
//                            case "Long": {
//                                map.put(columnLabel, rs.getLong(columnLabel));
//                            }
//                                break;
//                            case "Double": {
//                                map.put(columnLabel, rs.getDouble(columnLabel));
//                            }
//                                break;
//                            case "Date": {
//                                map.put(columnLabel, rs.getDate(columnLabel));
//                            }
//                                break;
//                            default: {
//                                map.put(columnLabel, rs.getObject(columnLabel));
//                            }
//                        }
                    }
                    result.add(map);
                }
                // to json
                Gson gson = GsonUtil.getGson();
                out.println();
                out.println(gson.toJson(result));
                out.println();

            } catch (Exception ex) {
                out.println(ex.getMessage());
            }
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
