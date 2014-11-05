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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.Map;
import javax.ws.rs.core.Response;
import wi.core.db.DSConn;
import wi.core.util.json.JsonUtil;
import wi.core.util.sql.SQLUtil;
import wi.rc.data.order.OrderDetail;
import wi.rc.data.order.OrderSet;

/**
 *
 * @author hermeschang
 */
public class OrderDataOpr {

    public static Response selectOrder(int orderId, String expand) {

        Connection conn = null;
        Response resp;

        try {
            conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);
            PreparedStatement pStmt = conn.prepareStatement("SELECT o.order_id AS order_id , o.customer_id AS customer_id , cu.name AS customer_name, o.company_id AS company_id, \n"
                    + " c.name AS company_name, o.store_id AS store_id ,s.name AS store_name , o.pos_id AS pos_id ,p.name AS pos_name, \n"
                    + " o.employee_id AS employee_id, e.name AS employee_name,  o.ncode AS ncode, o.total_amount AS total_amount, o.order_datetime AS order_datetime, \n"
                    + " o.log_datetime AS log_datetime, o.status AS status, o.pos_order_id AS pos_order_id, o.memo  AS memo \n"
                    + " FROM `order` AS o \n"
                    + " LEFT JOIN `customer` AS cu ON o.customer_id = cu.customer_id \n"
                    + " LEFT JOIN `company` AS c ON o.company_id = c.company_id \n"
                    + " LEFT JOIN `store` AS s ON o.store_id = s.store_id \n"
                    + " LEFT JOIN `pos` AS p ON o.pos_id = p.pos_id \n"
                    + " LEFT JOIN `employee` AS e ON o.employee_id = e.employee_id \n"
                    + " LEFT JOIN `order_detail` AS od ON o.order_id = od.order_id \n"
                    + " WHERE  o.order_id = ?");
            pStmt.setLong(1, orderId);

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
                    PreparedStatement stmtOrderDetail = conn.prepareStatement("SELECT od.order_id AS order_id, od.product_id AS product_id, od.price AS price, od.amount AS amount, od.total_amount AS total_amount"
                            + " FROM order_detail AS od"
                            + " LEFT JOIN `product` AS pd ON od.product_id = pd.product_id \n"
                            + " WHERE od.order_id = ?");
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
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (Exception ex) {

                }
            }
        }
        return resp;
    }

    public static Response selectOrdersByPosId(int posId, String expand) {

        Connection conn = null;
        Response resp;

        try {
            conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);
            PreparedStatement pStmt = conn.prepareStatement("SELECT o.order_id AS order_id , o.customer_id AS customer_id , cu.name AS customer_name, o.company_id AS company_id, \n"
                    + " c.name AS company_name, o.store_id AS store_id ,s.name AS store_name , o.pos_id AS pos_id ,p.name AS pos_name, \n"
                    + " o.employee_id AS employee_id, e.name AS employee_name,  o.ncode AS ncode, o.total_amount AS total_amount, o.order_datetime AS order_datetime, \n"
                    + " o.log_datetime AS log_datetime, o.status AS status, o.pos_order_id AS pos_order_id, o.memo  AS memo \n"
                    + " FROM `order` AS o \n"
                    + " LEFT JOIN `customer` AS cu ON o.customer_id = cu.customer_id \n"
                    + " LEFT JOIN `company` AS c ON o.company_id = c.company_id \n"
                    + " LEFT JOIN `store` AS s ON o.store_id = s.store_id \n"
                    + " LEFT JOIN `pos` AS p ON o.pos_id = p.pos_id \n"
                    + " LEFT JOIN `employee` AS e ON o.employee_id = e.employee_id \n"
                    + " LEFT JOIN `order_detail` AS od ON o.order_id = od.order_id \n"
                    + " WHERE o.pos_id = ?");
            pStmt.setInt(1, posId);
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
                        PreparedStatement stmtOrderDetail = conn.prepareStatement("SELECT od.order_id AS order_id, od.product_id AS product_id, od.price AS price, od.amount AS amount, od.total_amount AS total_amount"
                                + " FROM order_detail AS od"
                                + " LEFT JOIN `product` AS pd ON od.product_id = pd.product_id \n"
                                + " WHERE od.order_id = ?");
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
                    conn.setAutoCommit(true);
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
        
        long order_id;
        JsonObject jsonResult = new JsonObject();
        
        try {
            conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);
            conn.setAutoCommit(false);

            Map<?, ?> map = JsonUtil.toMap(jsonString);
            Map<String, Map<String, Object>> mapOrder = (Map<String, Map<String, Object>>)map.get("order");

            PreparedStatement stmtOrder = conn.prepareStatement(SQLUtil.genInsertSQLString(mapOrder.keySet()), PreparedStatement.RETURN_GENERATED_KEYS);
            int count = 1;
            for (String key: mapOrder.keySet()) {
                Object value = mapOrder.get(key);
                stmtOrder.setObject(count, value);
                count++;
            }

            if (stmtOrder.executeUpdate() > 0) {
                // execute success
                ResultSet rs = stmtOrder.getGeneratedKeys();
                order_id = rs.getLong(1);
                
                jsonResult.addProperty("order_id", order_id);
//                PreparedStatement stmtOrderDetail = conn.prepareStatement(OrderDataOpr.DBInfo.OrderDetailInfo.SQL_ORDER_DETAIL_INSERT);
//                for (OrderDetail orderDetail : orderSet.mOrderDetail) {
//                    // set order_id
//                    orderDetail.mOrderId = orderSet.mOrder.mOrderId;
//
//                    // execute order_detail
//                    stmtOrderDetail.setLong(1, orderDetail.mOrderId);
//                    stmtOrderDetail.setInt(2, orderDetail.mProductId);
//                    stmtOrderDetail.setInt(3, orderDetail.mPrice);
//                    stmtOrderDetail.setInt(4, orderDetail.mAmount);
//                    stmtOrderDetail.setInt(5, orderDetail.mTotalAmount);
//                    if (stmtOrderDetail.executeUpdate() < 0) {
//                        ret = false;
//                        break;
//                    }
//                }
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
    
    public static Response updateOrder(int orderId, String jsonOrderSet) {
        
//        OrderSet orderSet;
//
//        Connection conn = null;
//        Response resp;
//        boolean ret = true;
//        String sql = "UPDATE rc.order SET";
//        try {
//            orderSet = OrderSet.fromJson(OrderSet._VERSION, jsonOrderSet);
//
//            conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);
//            
//            //if dont need to update the value will be -1
//            
//            if (orderSet.mOrder.mCustomerId != -1)
//                sql += " customer_id = " + orderSet.mOrder.mCustomerId;
//            if (orderSet.mOrder.mCompanyId != -1)
//                sql += " company_id = " + orderSet.mOrder.mCompanyId;
//            if (orderSet.mOrder.mStoreId != -1)
//                sql += " store_id = " + orderSet.mOrder.mStoreId;
//            if (orderSet.mOrder.mPosId != -1)
//                sql += " pos_id = " + orderSet.mOrder.mPosId;
//            if (orderSet.mOrder.mEmployeeId != -1)
//                sql += " employee_id = " + orderSet.mOrder.mEmployeeId;
//            if (orderSet.mOrder.mNcode != null) 
//                sql += " ncode = " + orderSet.mOrder.mNcode;
//            if (orderSet.mOrder.mOrderDatetime != null) 
//                sql += " order_datetime = " + orderSet.mOrder.mOrderDatetime;
//            if (orderSet.mOrder.mStatus != null) 
//                sql += " status = " + orderSet.mOrder.mStatus;
//            if (orderSet.mOrder.mPosOrderId == null) 
//                sql += " pos_order_id = " + orderSet.mOrder.mPosOrderId;
//            if (orderSet.mOrder.mMemo == null) 
//                sql += " memo = " + orderSet.mOrder.mMemo;
//            
//            PreparedStatement stmtOrder = conn.prepareStatement(sql);
//            //
//            // update order detail , unfinished
//            //
//            if (stmtOrder.executeUpdate() > 0) {
//                // execute success
//                ResultSet rs = stmtOrder.getGeneratedKeys();
//                if (rs.next()) {
//                    orderSet.mOrder.mOrderId = rs.getLong(1);
//                }
//
//                PreparedStatement stmtOrderDetail = conn.prepareStatement(OrderDataOpr.DBInfo.OrderDetailInfo.SQL_ORDER_DETAIL_INSERT);
//                for (OrderDetail orderDetail : orderSet.mOrderDetail) {
//                    // set order_id
//                    orderDetail.mOrderId = orderSet.mOrder.mOrderId;
//
//                    // execute order_detail
//                    stmtOrderDetail.setLong(1, orderDetail.mOrderId);
//                    stmtOrderDetail.setInt(2, orderDetail.mProductId);
//                    stmtOrderDetail.setInt(3, orderDetail.mPrice);
//                    stmtOrderDetail.setInt(4, orderDetail.mAmount);
//                    stmtOrderDetail.setInt(5, orderDetail.mTotalAmount);
//                    if (stmtOrderDetail.executeUpdate() < 0) {
//                        ret = false;
//                        break;
//                    }
//                }
//            } else {
//                // execute failure
//                ret = false;
//            }
//
//            // check ret and commit or rollback
//            if (ret) {
//                conn.commit();
//                resp = Response.status(Response.Status.CREATED).entity(orderSet.toJson(OrderSet._VERSION)).build();
//            } else {
//                conn.rollback();
//                resp = Response.status(Response.Status.BAD_REQUEST).build();
//            }
//        } catch (JsonSyntaxException | NullPointerException ex) {
//            resp = Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
//        } catch (Exception ex) {
//            resp = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
//        } finally {
//            if (conn != null) {
//                try {
//                    conn.setAutoCommit(true);
//                    conn.close();
//                } catch (Exception ex) {
//
//                }
//            }
//        }
//
//        return resp;
        return null;
    }
    
    public static Response deleteOrder(int orderId) {

//        Connection conn = null;
//        Response resp;
//        
//        try {
//            conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);
//            PreparedStatement stmtOrder = conn.prepareStatement(OrderDataOpr.DBInfo.OrderInfo.SQL_ORDER_DELETE);
//            // "DELETE FROM rc.order WHERE order_id = ?";
//            stmtOrder.setLong(1, orderId);
//            stmtOrder.execute();
//            PreparedStatement stmtOrderDetail = conn.prepareStatement(OrderDataOpr.DBInfo.OrderDetailInfo.SQL_ORDER_DETAIL_DELETE);
//            // "DELETE FROM rc.order_detail WHERE order_id = ?"
//            stmtOrderDetail.setLong(1, orderId);
//            stmtOrderDetail.execute();
//            resp = Response.status(Response.Status.CREATED).entity(null).build();
//            
//        } catch (JsonSyntaxException | NullPointerException ex) {
//            resp = Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
//        } catch (Exception ex) {
//            resp = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
//        } finally {
//            if (conn != null) {
//                try {
//                    conn.setAutoCommit(true);
//                    conn.close();
//                } catch (Exception ex) {
//
//                }
//            }
//        }
//        return resp;
        return null;
    }
}
