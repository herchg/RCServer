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
import java.util.Map;
import javax.ws.rs.core.Response;
import wi.core.db.DSConn;
import wi.core.util.json.JsonUtil;
import wi.core.util.sql.SQLUtil;
import wi.rc.server.Status;
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
    
    public static Response selectProductById(int company_id,int product_id) {
        
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
                    + " WHERE p.company_id = c.company_id AND p.company_id = ? AND p.product_id = ?");
            
            pStmt.setInt(1, company_id);
            pStmt.setInt(2, product_id);
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
    
    public static Response selectProductByName(int company_id,String product_name) {
        
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
                    + " WHERE p.company_id = c.company_id AND p.company_id = ? AND p.name LIKE ?");
            
            pStmt.setInt(1, company_id);
            pStmt.setString(2, "%" + product_name + "%");
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
    
    public static Response selectProductByCompany(int company_id) {
        
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
                    + " WHERE p.company_id = c.company_id AND p.company_id = ?");
            
            pStmt.setInt(1, company_id);
            
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

    public static Response selectProductByCategory(int company_id,int category_id) {
        
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
                    + " WHERE p.company_id = c.company_id AND p.company_id = ? AND p.category_id = ?");
            
            pStmt.setInt(1, company_id);
            pStmt.setInt(2, category_id);
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

        Connection conn = null;
        Response resp;
        boolean ret = true;

        long product_id = -1;
        JsonObject jsonResult = new JsonObject();

        String sql = null;
        try {
            conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);
            conn.setAutoCommit(false);

            Map<?, ?> map = JsonUtil.toMap(jsonString);
            Map<String, Object> mapProduct = (Map<String, Object>) map.get("product");

            sql = SQLUtil.genInsertSQLString("`product`", mapProduct.keySet());
            PreparedStatement stmtProduct = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            int count = 1;
            for (String key : mapProduct.keySet()) {
                Object value = mapProduct.get(key);
                stmtProduct.setObject(count, value);
                count++;
            }

            if (stmtProduct.executeUpdate() > 0) {
                // execute success
                // get product_id
                ResultSet rs = stmtProduct.getGeneratedKeys();
                if (rs.next()) {
                    product_id = rs.getLong(1);
                }
                
                PreparedStatement stmtProductPrice = null;
                Map<String, Object> mapProductPrice = (Map<String, Object>) map.get("product_price");
                // add product_id
                mapProductPrice.put("product_id", product_id);
                // prepare sql and statement
                if (stmtProductPrice == null) {
                    sql = SQLUtil.genInsertSQLString("`product_price`", mapProductPrice.keySet());
                    stmtProductPrice = conn.prepareStatement(sql);
                }
                count = 1;
                for (String key : mapProductPrice.keySet()) {
                    Object value = mapProductPrice.get(key);
                    stmtProductPrice.setObject(count, value);
                    count++;
                }
                if (stmtProductPrice.executeUpdate() < 0) {
                    ret = false;
                }
                // gen result
                jsonResult.addProperty("product_id", product_id);
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
    
    public static Response deleteProduct(int company_id,int product_id) {
        
        Response resp;
        Connection conn = null;
        
        try {
            conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);
            PreparedStatement stmtOrder = conn.prepareStatement("UPDATE `product` SET  status = ? WHERE company_id = ? AND product_id = ?");
            stmtOrder.setLong(1, Status.Deleted.getValue());
            stmtOrder.setLong(2, company_id);
            stmtOrder.setLong(3, product_id);
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
