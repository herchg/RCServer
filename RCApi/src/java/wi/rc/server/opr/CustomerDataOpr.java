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
 * @author samuelatwistron
 */
public class CustomerDataOpr {
    
    public static Response selectAllCustomer(int company_id) {
        
        Connection conn = null;
        Response resp;
        try {
            conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);
            PreparedStatement pStmt = conn.prepareStatement("SELECT cu.customer_id AS customer_id, cu.name AS name, cu.login_id AS login_id, cu.login_password AS login_password, "
                    + " cu.point AS point, cu.company_id AS company_id, c.name AS company_name, cu.store_id AS store_id,s.name AS store_name,cu.active_datetime AS active_datetime, "
                    + " cu.expiry_datetime AS expiry_datetime, cu.reg_datetime AS reg_datetime, cu.status AS status \n" 
                    + " FROM `customer` AS cu\n" 
                    + " LEFT JOIN `company` AS c ON cu.company_id = c.company_id \n" 
                    + " LEFT JOIN `store` AS s ON cu.store_id = s.store_id \n" 
                    + " WHERE cu.company_id = ?");
            
            pStmt.setInt(1, company_id);
            ResultSet rs = pStmt.executeQuery();
            
            if (!rs.next()) {
                resp = Response.status(Response.Status.NOT_FOUND).build();
            } else {
                // back to first
                rs.previous();
     
                JsonObject jsonResult = new JsonObject();
                JsonElement jsonProduct = JsonUtil.toJsonArray(rs);
                jsonResult.add("customer", jsonProduct);
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
    
    public static Response selectCustomerById(int company_id , int customer_id) {
        
        Connection conn = null;
        Response resp;
        try {
            conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);
            PreparedStatement pStmt = conn.prepareStatement("SELECT cu.customer_id AS customer_id, cu.name AS name, cu.login_id AS login_id, cu.login_password AS login_password, "
                    + " cu.point AS point, cu.company_id AS company_id, c.name AS company_name, cu.store_id AS store_id,s.name AS store_name,cu.active_datetime AS active_datetime, "
                    + " cu.expiry_datetime AS expiry_datetime, cu.reg_datetime AS reg_datetime, cu.status AS status \n" 
                    + " FROM `customer` AS cu\n" 
                    + " LEFT JOIN `company` AS c ON cu.company_id = c.company_id \n" 
                    + " LEFT JOIN `store` AS s ON cu.store_id = s.store_id \n" 
                    + " WHERE cu.company_id = ? AND cu.customer_id = ?");
            
            pStmt.setInt(1, company_id);
            pStmt.setInt(2, customer_id);
            ResultSet rs = pStmt.executeQuery();
            
            if (!rs.next()) {
                resp = Response.status(Response.Status.NOT_FOUND).build();
            } else {
                // back to first
                rs.previous();
     
                JsonObject jsonResult = new JsonObject();
                JsonElement jsonProduct = JsonUtil.toJsonArray(rs);
                jsonResult.add("customer", jsonProduct);
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
    
    public static Response selectCustomerByStore(int company_id , int store_id) {
        
        Connection conn = null;
        Response resp;
        try {
            conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);
            PreparedStatement pStmt = conn.prepareStatement("SELECT cu.customer_id AS customer_id, cu.name AS name, cu.login_id AS login_id, cu.login_password AS login_password, "
                    + " cu.point AS point, cu.company_id AS company_id, c.name AS company_name, cu.store_id AS store_id,s.name AS store_name,cu.active_datetime AS active_datetime, "
                    + " cu.expiry_datetime AS expiry_datetime, cu.reg_datetime AS reg_datetime, cu.status AS status \n" 
                    + " FROM `customer` AS cu\n" 
                    + " LEFT JOIN `company` AS c ON cu.company_id = c.company_id \n" 
                    + " LEFT JOIN `store` AS s ON cu.store_id = s.store_id \n" 
                    + " WHERE cu.company_id = ? AND cu.store_id = ?");
            
            pStmt.setInt(1, company_id);
            pStmt.setInt(2, store_id);
            ResultSet rs = pStmt.executeQuery();
            
            if (!rs.next()) {
                resp = Response.status(Response.Status.NOT_FOUND).build();
            } else {
                // back to first
                rs.previous();
     
                JsonObject jsonResult = new JsonObject();
                JsonElement jsonProduct = JsonUtil.toJsonArray(rs);
                jsonResult.add("customer", jsonProduct);
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
    
    public static Response selectCustomerByName(int company_id , String customer_name) {
        
        Connection conn = null;
        Response resp;
        try {
            conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);
            PreparedStatement pStmt = conn.prepareStatement("SELECT cu.customer_id AS customer_id, cu.name AS name, cu.login_id AS login_id, cu.login_password AS login_password, "
                    + " cu.point AS point, cu.company_id AS company_id, c.name AS company_name, cu.store_id AS store_id,s.name AS store_name,cu.active_datetime AS active_datetime, "
                    + " cu.expiry_datetime AS expiry_datetime, cu.reg_datetime AS reg_datetime, cu.status AS status \n" 
                    + " FROM `customer` AS cu\n" 
                    + " LEFT JOIN `company` AS c ON cu.company_id = c.company_id \n" 
                    + " LEFT JOIN `store` AS s ON cu.store_id = s.store_id \n" 
                    + " WHERE cu.company_id = ? AND cu.name LIKE ?");
            
            pStmt.setInt(1, company_id);
            pStmt.setString(2, "%" + customer_name + "%");
            ResultSet rs = pStmt.executeQuery();
            
            if (!rs.next()) {
                resp = Response.status(Response.Status.NOT_FOUND).build();
            } else {
                // back to first
                rs.previous();
     
                JsonObject jsonResult = new JsonObject();
                JsonElement jsonProduct = JsonUtil.toJsonArray(rs);
                jsonResult.add("customer", jsonProduct);
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
    
    public static Response insertCustomer(String jsonString) {

        Connection conn = null;
        Response resp;
        boolean ret = true;

        long customer_id = -1;
        JsonObject jsonResult = new JsonObject();

        String sql = null;
        try {
            conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);
            conn.setAutoCommit(false);

            Map<?, ?> map = JsonUtil.toMap(jsonString);
            Map<String, Object> mapCustomer = (Map<String, Object>) map.get("customer");

            sql = SQLUtil.genInsertSQLString("`customer`", mapCustomer.keySet());
            PreparedStatement stmtCustomer = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            int count = 1;
            for (String key : mapCustomer.keySet()) {
                Object value = mapCustomer.get(key);
                stmtCustomer.setObject(count, value);
                count++;
            }

            if (stmtCustomer.executeUpdate() > 0) {
                // execute success
                // get product_id
                ResultSet rs = stmtCustomer.getGeneratedKeys();
                if (rs.next()) {
                    customer_id = rs.getLong(1);
                }
                
                PreparedStatement stmtCustomerDetail = null;
                Map<String, Object> mapCustomerDetail = (Map<String, Object>) map.get("customer_detail");
                // add product_id
                mapCustomerDetail.put("customer_id", customer_id);
                // prepare sql and statement
                if (stmtCustomerDetail == null) {
                    sql = SQLUtil.genInsertSQLString("`customer_detail`", mapCustomerDetail.keySet());
                    stmtCustomerDetail = conn.prepareStatement(sql);
                }
                count = 1;
                for (String key : mapCustomerDetail.keySet()) {
                    Object value = mapCustomerDetail.get(key);
                    stmtCustomerDetail.setObject(count, value);
                    count++;
                }
                if (stmtCustomerDetail.executeUpdate() < 0) {
                    ret = false;
                }
                // gen result
                jsonResult.addProperty("customer_id", customer_id);
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
    
    public static Response deleteCustomer(int company_id,int customer_id) {
        
        Response resp;
        Connection conn = null;
        
        try {
            conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);
            PreparedStatement stmtOrder = conn.prepareStatement("UPDATE `customer` SET  status = ? WHERE company_id = ? AND customer_id = ?");
            stmtOrder.setLong(1, Status.Deleted.getValue());
            stmtOrder.setLong(2, company_id);
            stmtOrder.setLong(3, customer_id);
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
