/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wi.rc.server.opr;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.Response;
import wi.core.db.DSConn;
import wi.core.util.DateTimeUtil;
import wi.core.util.json.JsonUtil;
import wi.core.util.sql.SQLUtil;
import wi.rc.server.Status;

/**
 *
 * @author hermeschang
 */
public class OrderDataOpr {

    private static String generateSqlQueryString() {

        String sql = "SELECT o.order_id AS order_id , o.customer_id AS customer_id , cu.name AS customer_name, o.company_id AS company_id, \n"
                    + " c.name AS company_name, o.store_id AS store_id ,s.name AS store_name , o.pos_id AS pos_id ,p.name AS pos_name, \n"
                    + " o.employee_id AS employee_id, e.name AS employee_name,  o.ncode AS ncode, o.total_amount AS total_amount, o.order_datetime AS order_datetime, \n"
                    + " o.log_datetime AS log_datetime, o.status AS status, o.pos_order_id AS pos_order_id, o.memo  AS memo \n"
                    + " FROM `order` AS o \n"
                    + " LEFT JOIN `customer` AS cu ON o.customer_id = cu.customer_id \n"
                    + " LEFT JOIN `company` AS c ON o.company_id = c.company_id \n"
                    + " LEFT JOIN `store` AS s ON o.store_id = s.store_id \n"
                    + " LEFT JOIN `pos` AS p ON o.pos_id = p.pos_id \n"
                    + " LEFT JOIN `employee` AS e ON o.employee_id = e.employee_id \n";
        
        return sql;
    }
    
    private static String generateSqlQueryDetailString() {

        String sql = "SELECT od.order_id AS order_id, od.product_id AS product_id, od.price AS price, od.amount AS amount, od.total_amount AS total_amount"
                    + " FROM order_detail AS od"
                    + " LEFT JOIN `product` AS pd ON od.product_id = pd.product_id \n"
                    + " WHERE od.order_id = ?";
        
        return sql;
    }
    
    public static Response selectAllOrders(int company_id,String order_id,String store_id,String pos_id,String status,String start_date,String end_date,String expand) {

        Connection conn = null;
        Response resp;
        
        String sqlString;
        int sqlCount = 1;
        
        try {
            conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);
            
            sqlString = generateSqlQueryString() + " WHERE o.company_id = ? ";
            
            //check input to add sql query string
            if(order_id != null){ sqlString += " AND o.order_id = ? ";}
            if(store_id != null){ sqlString += " AND o.store_id = ? ";}
            if(pos_id != null){ sqlString += " AND o.pos_id = ? ";}
            if(status != null){ sqlString += " AND o.status = ? ";}
            if(start_date != null){ sqlString += " AND o.order_datetime >= ? ";}
            if(end_date != null){ sqlString += " AND o.order_datetime <= ? ";}
            
            //create statement and use 
            PreparedStatement pStmt = conn.prepareStatement(sqlString);

            //set input condition
            pStmt.setInt(sqlCount, company_id);
            sqlCount ++;
            
            if(order_id != null){ 
                pStmt.setLong(sqlCount, Long.parseLong(order_id));
                sqlCount ++;
            }
            
            if(store_id != null){ 
                pStmt.setInt(sqlCount, Integer.parseInt(store_id));
                sqlCount ++;
            }
            
            if(pos_id != null){ 
                pStmt.setInt(sqlCount, Integer.parseInt(pos_id));
                sqlCount ++;
            }
            
            if(status != null){ 
                pStmt.setLong(sqlCount, Long.parseLong(status));
                sqlCount ++;
            }
            
            if(start_date != null){ 
                pStmt.setString(sqlCount, start_date);
                sqlCount ++;
            }
            
            if(end_date != null){ 
                pStmt.setString(sqlCount, end_date);
                sqlCount ++;
            }
            
            
            ResultSet rs = pStmt.executeQuery();

            if (!rs.next()) {
                resp = Response.status(Response.Status.NOT_FOUND).build();
            } else {
                // back to first
                rs.previous();
                JsonArray jsonResult = new JsonArray();
                while (rs.next()) {
                    long orderId = rs.getLong("order_id");

                    // back because next
                    rs.previous();

                    JsonObject jsonRow = new JsonObject();
                    JsonElement jsonOrder = JsonUtil.toJsonElement(rs);
                    jsonRow.add("order", jsonOrder);

                    if (expand != null && expand.equals("detail")) {
                        PreparedStatement stmtOrderDetail = conn.prepareStatement(generateSqlQueryDetailString());
                        stmtOrderDetail.setLong(1, orderId);
                        ResultSet rsOrderDetail = stmtOrderDetail.executeQuery();
                        JsonElement elementOrderDetail = JsonUtil.toJsonArray(rsOrderDetail);
                        jsonRow.add("order_detail", elementOrderDetail);
                    }
                    jsonResult.add(jsonRow);
                }
                resp = Response.status(Response.Status.OK).entity(jsonResult.toString()).build();
            }

        } catch (JsonSyntaxException | NullPointerException ex) {
            resp = Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            resp = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception ex) {

                }
            }
        }

        
        return resp;
    }

    public static Response selectOrderByID(int company_id, long orderId, String expand) {

        Connection conn = null;
        Response resp;

        try {
            conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);
            PreparedStatement pStmt = conn.prepareStatement(generateSqlQueryString() + " WHERE o.company_id = ? AND o.order_id = ?");
            pStmt.setLong(1, company_id);
            pStmt.setLong(2, orderId);
            ResultSet rs = pStmt.executeQuery();

            if (!rs.next()) {
                resp = Response.status(Response.Status.NOT_FOUND).build();
            } else {
                // back to first
                rs.previous();

                JsonObject jsonResult = new JsonObject();
                JsonElement jsonOrder = JsonUtil.toJsonElement(rs);
                jsonResult.add("order", jsonOrder);

                if (expand != null && expand.equals("detail")) {
                    PreparedStatement stmtOrderDetail = conn.prepareStatement(generateSqlQueryDetailString());
                    stmtOrderDetail.setLong(1, orderId);
                    ResultSet rsOrderDetail = stmtOrderDetail.executeQuery();
                    JsonElement jsonOrderDetail = JsonUtil.toJsonArray(rsOrderDetail);
                    jsonResult.add("order_detail", jsonOrderDetail);
                }
                resp = Response.status(Response.Status.OK).entity(jsonResult.toString()).build();
            }
        } catch (JsonSyntaxException | NullPointerException ex) {
            resp = Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            resp = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception ex) {

                }
            }
        }
        return resp;
    }

    public static Response selectOrdersByPosId(int company_id, int posId, String expand) {

        Connection conn = null;
        Response resp;

        try {
            conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);
            PreparedStatement pStmt = conn.prepareStatement(generateSqlQueryString() + " WHERE o.company_id = ? AND o.pos_id = ?");
            pStmt.setInt(1, company_id);
            pStmt.setInt(2, posId);
            ResultSet rs = pStmt.executeQuery();

            if (!rs.next()) {
                resp = Response.status(Response.Status.NOT_FOUND).build();
            } else {
                // back to first
                rs.previous();
                JsonArray jsonResult = new JsonArray();
                while (rs.next()) {
                    long orderId = rs.getLong("order_id");

                    // back because next
                    rs.previous();

                    JsonObject jsonRow = new JsonObject();
                    JsonElement jsonOrder = JsonUtil.toJsonElement(rs);
                    jsonRow.add("order", jsonOrder);

                    if (expand != null && expand.equals("detail")) {
                        PreparedStatement stmtOrderDetail = conn.prepareStatement(generateSqlQueryDetailString());
                        stmtOrderDetail.setLong(1, orderId);
                        ResultSet rsOrderDetail = stmtOrderDetail.executeQuery();
                        JsonElement elementOrderDetail = JsonUtil.toJsonArray(rsOrderDetail);
                        jsonRow.add("order_detail", elementOrderDetail);
                    }
                    jsonResult.add(jsonRow);
                }
                resp = Response.status(Response.Status.OK).entity(jsonResult.toString()).build();
            }

        } catch (JsonSyntaxException | NullPointerException ex) {
            resp = Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            resp = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception ex) {

                }
            }
        }
        return resp;
    }

    public static Response selectOrderByStatus(int company_id, long status_id, String expand) {

        Connection conn = null;
        Response resp;

        try {
            conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);
            PreparedStatement pStmt = conn.prepareStatement(generateSqlQueryString() + " WHERE o.company_id = ? AND o.status = ?");
            pStmt.setInt(1, company_id);
            pStmt.setLong(2, status_id);
            ResultSet rs = pStmt.executeQuery();

            if (!rs.next()) {
                resp = Response.status(Response.Status.NOT_FOUND).build();
            } else {
                // back to first
                rs.previous();
                JsonArray jsonResult = new JsonArray();
                while (rs.next()) {
                    long orderId = rs.getLong("order_id");

                    // back because next
                    rs.previous();

                    JsonObject jsonRow = new JsonObject();
                    JsonElement jsonOrder = JsonUtil.toJsonElement(rs);
                    jsonRow.add("order", jsonOrder);

                    if (expand != null && expand.equals("detail")) {
                        PreparedStatement stmtOrderDetail = conn.prepareStatement(generateSqlQueryDetailString());
                        stmtOrderDetail.setLong(1, orderId);
                        ResultSet rsOrderDetail = stmtOrderDetail.executeQuery();
                        JsonElement elementOrderDetail = JsonUtil.toJsonArray(rsOrderDetail);
                        jsonRow.add("order_detail", elementOrderDetail);
                    }
                    jsonResult.add(jsonRow);
                }
                resp = Response.status(Response.Status.OK).entity(jsonResult.toString()).build();
            }

        } catch (JsonSyntaxException | NullPointerException ex) {
            resp = Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            resp = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception ex) {

                }
            }
        }
        return resp;
    }

    public static Response selectOrderByDate(int company_id, String begin_date, String end_date, String expand) {

        Connection conn = null;
        Response resp;

        try {
            conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);
            PreparedStatement pStmt = conn.prepareStatement(generateSqlQueryString() + " WHERE o.company_id = ? AND o.order_datetime >= ? AND o.order_datetime <= ?");
            pStmt.setInt(1, company_id);
            pStmt.setString(2, begin_date);
            pStmt.setString(3, end_date);
            ResultSet rs = pStmt.executeQuery();

            if (!rs.next()) {
                resp = Response.status(Response.Status.NOT_FOUND).build();
            } else {
                // back to first
                rs.previous();
                JsonArray jsonResult = new JsonArray();
                while (rs.next()) {
                    long orderId = rs.getLong("order_id");

                    // back because next
                    rs.previous();

                    JsonObject jsonRow = new JsonObject();
                    JsonElement jsonOrder = JsonUtil.toJsonElement(rs);
                    jsonRow.add("order", jsonOrder);

                    if (expand != null && expand.equals("detail")) {
                        PreparedStatement stmtOrderDetail = conn.prepareStatement(generateSqlQueryDetailString());
                        stmtOrderDetail.setLong(1, orderId);
                        ResultSet rsOrderDetail = stmtOrderDetail.executeQuery();
                        JsonElement elementOrderDetail = JsonUtil.toJsonArray(rsOrderDetail);
                        jsonRow.add("order_detail", elementOrderDetail);
                    }
                    jsonResult.add(jsonRow);
                }
                resp = Response.status(Response.Status.OK).entity(jsonResult.toString()).build();
            }

        } catch (JsonSyntaxException | NullPointerException ex) {
            resp = Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            resp = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception ex) {

                }
            }
        }
        return resp;
    }

    public static Response insertOrder(String jsonString) {

        Connection conn = null;
        Response resp;
        boolean ret = true;

        long order_id = -1;
        String ncode;
        JsonObject jsonResult = new JsonObject();

        String sql = null;
        try {

            conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);
            conn.setAutoCommit(false);

            Map<?, ?> map = JsonUtil.toMap(jsonString);
            Map<String, Object> mapOrder = (Map<String, Object>) map.get("order");

            //取得ncode
            ncode = (String) mapOrder.get("ncode");

            // add server info
            mapOrder.put("status", Status.Valid.getValue());
            mapOrder.put("log_datetime", DateTimeUtil.format(new Date(System.currentTimeMillis()), DateTimeUtil.DEFAULT_DATETIME_FORMAT));

            sql = SQLUtil.genInsertSQLString("`order`", mapOrder.keySet());
            PreparedStatement stmtOrder = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            int count = 1;
            for (String key : mapOrder.keySet()) {
                Object value = mapOrder.get(key);
                stmtOrder.setObject(count, value);
                count++;
            }

            if (stmtOrder.executeUpdate() > 0) {
                // execute success
                // get order_id
                ResultSet rs = stmtOrder.getGeneratedKeys();
                if (rs.next()) {
                    order_id = rs.getLong(1);
                }

                PreparedStatement stmtOrderDetail = null;
                List<Map<String, Object>> listOrderDetail = (List<Map<String, Object>>) map.get("order_detail");
                for (Map<String, Object> mapOrderDetail : listOrderDetail) {
                    // add order_id
                    mapOrderDetail.put("order_id", order_id);
                    // prepare sql and statement
                    if (stmtOrderDetail == null) {
                        sql = SQLUtil.genInsertSQLString("`order_detail`", mapOrderDetail.keySet());
                        stmtOrderDetail = conn.prepareStatement(sql);
                    }
                    count = 1;
                    for (String key : mapOrderDetail.keySet()) {
                        Object value = mapOrderDetail.get(key);
                        stmtOrderDetail.setObject(count, value);
                        count++;
                    }
                    if (stmtOrderDetail.executeUpdate() < 0) {
                        ret = false;
                        break;
                    }
                }

                // gen result
                jsonResult.addProperty("order_id", order_id);
            } else {
                // execute failure
                ret = false;
            }

            // check ret and commit or rollback
            if (ret) {
                conn.commit();
                resp = Response.status(Response.Status.CREATED).entity(jsonResult.toString()).build();
            } else {
                conn.rollback();
                resp = Response.status(Response.Status.BAD_REQUEST).build();
            }

        } catch (JsonSyntaxException | NullPointerException ex) {
            resp = Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            resp = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (Exception ex) {

                }
            }
        }

        return resp;
    }

    public static Response updateOrder(int company_id,long orderId, String jsonString) {
        Response resp;

        Connection conn = null;
        boolean ret = true;

        JsonObject jsonResult = new JsonObject();
        String sql = null;

        try {
            conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);

            Map<?, ?> map = JsonUtil.toMap(jsonString);
            Map<String, Object> mapOrderSet = (Map<String, Object>) map.get("order");
            Map<String, Object> mapOrderWhere = new LinkedHashMap<String, Object>();

            //where條件塞入
            mapOrderWhere.put("order_id", orderId);
            mapOrderWhere.put("company_id", company_id);
            //移除部分不可更新的input
            mapOrderSet.remove("order_id");// 避免Order Set statement裡面有order_id
            mapOrderSet.remove("company_id");// 避免Order Set statement裡面有company_id

            sql = SQLUtil.genUpdateSQLString("`order`", mapOrderSet, mapOrderWhere);
            PreparedStatement stmtOrder = conn.prepareStatement(sql);

            if (stmtOrder.executeUpdate() > 0) {
                // execute success
                //
                // update order detail , unfinished
                //
                List<Map<String, Object>> listOrderDetail = (List<Map<String, Object>>) map.get("order_detail");
                for (Map<String, Object> mapOrderDetailSet : listOrderDetail) {

                    // add order_id
                    Map<String, Object> mapOrderDetailWhere = new LinkedHashMap<String, Object>();

                    mapOrderDetailWhere.put("order_id", orderId);
                    mapOrderDetailWhere.put("product_id", mapOrderDetailSet.get("product_id"));

                    mapOrderDetailSet.remove("order_id");// 避免Order set statement裡面有order_id
                    mapOrderDetailSet.remove("product_id");// 避免OrderDetail set statement裡面有product_id

                    // prepare sql and statement
                    sql = SQLUtil.genUpdateSQLString("`order_detail`", mapOrderDetailSet, mapOrderDetailWhere);
                    PreparedStatement stmtOrderDetail = conn.prepareStatement(sql);
                    if (stmtOrderDetail.executeUpdate() < 0) {
                        ret = false;
                        break;
                    }
                }

                // gen result
                jsonResult.addProperty("order_id", orderId);
            } else {
                // execute failure
                ret = false;
            }

            // check ret and commit or rollback
            if (ret) {
                resp = Response.status(Response.Status.OK).entity(jsonResult.toString()).build();
            } else {
                resp = Response.status(Response.Status.BAD_REQUEST).entity(sql).build();
            }
        } catch (JsonSyntaxException | NullPointerException ex) {
            resp = Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            resp = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception ex) {

                }
            }
        }

        return resp;
    }

    public static Response deleteOrder(int company_id, long orderId) {

        Response resp;
        Connection conn = null;

        try {
            conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);
            PreparedStatement stmtOrder = conn.prepareStatement("UPDATE `order` SET  status = ? WHERE company_id = ? AND order_id = ?");
            stmtOrder.setLong(1, Status.Deleted.getValue());
            stmtOrder.setInt(2, company_id);
            stmtOrder.setLong(3, orderId);
            if (stmtOrder.executeUpdate() > 0) {
                resp = Response.status(Response.Status.OK).build();
            } else {
                resp = Response.status(Response.Status.NOT_FOUND).build();
            }
        } catch (JsonSyntaxException | NullPointerException ex) {
            resp = Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            resp = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception ex) {

                }
            }
        }

        return resp;
    }
}
