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
import wi.rc.data.product.Product;
import wi.rc.data.product.ProductPrice;
import wi.rc.data.product.ProductSet;
/**
 *
 * @author Lucas
 */
public class ProductDataOpr {
    
    public static Response selectAllProduct() {
        
        Connection conn = null;
        Response resp;
        try {
            conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);
            PreparedStatement pStmt = conn.prepareStatement("SELECT p.product_id AS product_id , c.company_id AS company_id, c.name AS company_name,p.name AS name, \n" 
                    + " p.name_4_short AS name_4_short ,p.description AS description, p.description_4_short AS description_4_short, p.product_code AS product_code, \n" 
                    + " p.barcode AS barcode, p.category_id AS category_id,ca.name AS category_name, p.option0 AS option0, p.option1 AS option1,p.option2 AS option2, \n" 
                    + " p.option3 AS option3, p.option4 AS option4, p.option5 AS option5,p.option6 AS option6, p.option7 AS option7,p.option8 AS option8,p.option9 AS option9, \n" 
                    + " p.status AS status,pp.price AS price ,pp.ncode AS ncode,pp.amount AS amount \n" 
                    + " FROM `product` AS p \n" 
                    + " LEFT JOIN `category` AS ca ON p.category_id = ca.category_id \n" 
                    + " LEFT JOIN `product_price` AS pp ON p.product_id = pp.product_id \n" 
                    + " ,company AS c \n" 
                    + " WHERE p.company_id = c.company_id");
            
            ResultSet rs = pStmt.executeQuery();
            
            if (!rs.next()) {
                resp = Response.status(Response.Status.NOT_FOUND).build();
            } else {
                // back to first
                rs.previous();
     
                JsonObject jsonResult = new JsonObject();
                JsonElement jsonProduct = JsonUtil.toJsonArray(rs);
                jsonResult.add("product", jsonProduct);
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
    
    public static Response selectProductByName(String product_name) {
        
        Connection conn = null;
        Response resp;
        try {
            conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);
  
            PreparedStatement pStmt = conn.prepareStatement("SELECT p.product_id AS product_id , c.company_id AS company_id, c.name AS company_name,p.name AS name, \n"
                    + " p.name_4_short AS name_4_short , p.description AS description, p.description_4_short AS description_4_short, p.product_code AS product_code, \n"
                    + " p.barcode AS barcode, p.category_id AS category_id, ca.name AS category_name, p.option0 AS option0, p.option1 AS option1, p.option2 AS option2, \n"
                    + " p.option3 AS option3, p.option4 AS option4, p.option5 AS option5, p.option6 AS option6, p.option7 AS option7,p.option8 AS option8, \n"
                    + " p.option9 AS option9, p.status AS status ,pp.price AS price ,pp.ncode AS ncode,pp.amount AS amount \n"
                    + " FROM `product` AS p \n" 
                    + " LEFT JOIN `category` AS ca ON p.category_id = ca.category_id \n" 
                    + " LEFT JOIN `product_price` AS pp ON p.product_id = pp.product_id \n" 
                    + " , company AS c \n" 
                    + " WHERE p.company_id = c.company_id AND p.name LIKE %?%");
            
            pStmt.setString(1, product_name);
            
            ResultSet rs = pStmt.executeQuery();
            
            if (!rs.next()) {
                resp = Response.status(Response.Status.NOT_FOUND).build();
            } else {
                // back to first
                rs.previous();
                
                JsonObject jsonResult = new JsonObject();
                JsonElement jsonProduct = JsonUtil.toJsonArray(rs);
                jsonResult.add("product", jsonProduct);
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

    public static Response selectProductByCategory(int category_id) {
        
        Connection conn = null;
        Response resp;
        try {
            conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);
  
            PreparedStatement pStmt = conn.prepareStatement("SELECT p.product_id AS product_id , c.company_id AS company_id, c.name AS company_name,p.name AS name, \n"
                    + " p.name_4_short AS name_4_short , p.description AS description, p.description_4_short AS description_4_short, p.product_code AS product_code, \n"
                    + " p.barcode AS barcode, p.category_id AS category_id, ca.name AS category_name, p.option0 AS option0, p.option1 AS option1, p.option2 AS option2, \n"
                    + " p.option3 AS option3, p.option4 AS option4, p.option5 AS option5, p.option6 AS option6, p.option7 AS option7,p.option8 AS option8, \n"
                    + " p.option9 AS option9, p.status AS status ,pp.price AS price ,pp.ncode AS ncode,pp.amount AS amount \n"
                    + " FROM `product` AS p \n" 
                    + " LEFT JOIN `category` AS ca ON p.category_id = ca.category_id \n" 
                    + " LEFT JOIN `product_price` AS pp ON p.product_id = pp.product_id \n" 
                    + " , company AS c \n" 
                    + " WHERE p.company_id = c.company_id AND p.category_id = ?");
            
            pStmt.setInt(1, category_id);
            
            ResultSet rs = pStmt.executeQuery();
            
            if (!rs.next()) {
                resp = Response.status(Response.Status.NOT_FOUND).build();
            } else {
                // back to first
                rs.previous();
                
                JsonObject jsonResult = new JsonObject();
                JsonElement jsonProduct = JsonUtil.toJsonArray(rs);
                jsonResult.add("product", jsonProduct);
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
    
    
    public static Response insertProduct(String jsonString) {

        /*
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
        */
        return null;
    }
    
    public static Response updateProduct(int orderId, String jsonOrderSet) {
        
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
    
    public static Response deleteProduct(int orderId) {

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
