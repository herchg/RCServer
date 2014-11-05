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
public class StoreDataOpr {
    
    public static Response selectAllStore(int company_id) {
        
        Connection conn = null;
        Response resp;
        try {
            conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);
            PreparedStatement pStmt = conn.prepareStatement("SELECT s.store_id AS store_id, s.name AS name, s.contact AS contact, "
                    + " s.tel AS tel, s.mobile AS mobile,  s.email AS email, s.memo AS memo, s.status AS status, s.company_id AS company_id, "
                    + "c.name AS company_name,s.manager_employee_id AS manager_employee_id ,e.name AS manager_name\n" 
                    + " FROM `store` AS s \n" 
                    + " LEFT JOIN `company` AS c ON s.company_id = c.company_id \n" 
                    + " LEFT JOIN `employee` AS e ON s.manager_employee_id = e.employee_id \n"
                    + " WHERE s.company_id = ?");
            
            pStmt.setInt(1, company_id);
            ResultSet rs = pStmt.executeQuery();
            
            if (!rs.next()) {
                resp = Response.status(Response.Status.NOT_FOUND).build();
            } else {
                // back to first
                rs.previous();
     
                JsonObject jsonResult = new JsonObject();
                JsonElement jsonProduct = JsonUtil.toJsonArray(rs);
                jsonResult.add("store", jsonProduct);
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
    
    public static Response selectStoreById(int company_id,int store_id) {
        
        Connection conn = null;
        Response resp;
        try {
            conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);
            PreparedStatement pStmt = conn.prepareStatement("SELECT s.store_id AS store_id, s.name AS name, s.contact AS contact, "
                    + " s.tel AS tel, s.mobile AS mobile,  s.email AS email, s.memo AS memo, s.status AS status, s.company_id AS company_id, "
                    + "c.name AS company_name,s.manager_employee_id AS manager_employee_id ,e.name AS manager_name\n" 
                    + " FROM `store` AS s \n" 
                    + " LEFT JOIN `company` AS c ON s.company_id = c.company_id \n" 
                    + " LEFT JOIN `employee` AS e ON s.manager_employee_id = e.employee_id \n"
                    + " WHERE s.company_id = ? AND s.store_id = ?");
            
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
                jsonResult.add("store", jsonProduct);
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
    
    public static Response selectStoreByName(int company_id,String store_name) {
        
        Connection conn = null;
        Response resp;
        try {
            conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);
            PreparedStatement pStmt = conn.prepareStatement("SELECT s.store_id AS store_id, s.name AS name, s.contact AS contact, "
                    + " s.tel AS tel, s.mobile AS mobile,  s.email AS email, s.memo AS memo, s.status AS status, s.company_id AS company_id, "
                    + "c.name AS company_name,s.manager_employee_id AS manager_employee_id ,e.name AS manager_name\n" 
                    + " FROM `store` AS s \n" 
                    + " LEFT JOIN `company` AS c ON s.company_id = c.company_id \n" 
                    + " LEFT JOIN `employee` AS e ON s.manager_employee_id = e.employee_id \n"
                    + " WHERE s.company_id = ? AND s.name LIKE ?");
            
            pStmt.setInt(1, company_id);
            pStmt.setString(2, "%" + store_name + "%");
            ResultSet rs = pStmt.executeQuery();
            
            if (!rs.next()) {
                resp = Response.status(Response.Status.NOT_FOUND).build();
            } else {
                // back to first
                rs.previous();
     
                JsonObject jsonResult = new JsonObject();
                JsonElement jsonProduct = JsonUtil.toJsonArray(rs);
                jsonResult.add("store", jsonProduct);
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
    
    public static Response selectStoreByEmployee(int company_id,int employee_id) {
        
        Connection conn = null;
        Response resp;
        try {
            conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);
            PreparedStatement pStmt = conn.prepareStatement("SELECT s.store_id AS store_id, s.name AS name, s.contact AS contact, "
                    + " s.tel AS tel, s.mobile AS mobile,  s.email AS email, s.memo AS memo, s.status AS status, s.company_id AS company_id, "
                    + "c.name AS company_name,s.manager_employee_id AS manager_employee_id ,e.name AS manager_name\n" 
                    + " FROM `store` AS s \n" 
                    + " LEFT JOIN `company` AS c ON s.company_id = c.company_id \n" 
                    + " LEFT JOIN `employee` AS e ON s.manager_employee_id = e.employee_id \n"
                    + " WHERE s.company_id = ? AND s.manager_employee_id = ?");
            
            pStmt.setInt(1, company_id);
            pStmt.setInt(2, employee_id);
            ResultSet rs = pStmt.executeQuery();
            
            if (!rs.next()) {
                resp = Response.status(Response.Status.NOT_FOUND).build();
            } else {
                // back to first
                rs.previous();
     
                JsonObject jsonResult = new JsonObject();
                JsonElement jsonProduct = JsonUtil.toJsonArray(rs);
                jsonResult.add("store", jsonProduct);
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
    
    public static Response insertStore(String jsonString) {

        Connection conn = null;
        Response resp;
        boolean ret = true;

        long store_id = -1;
        JsonObject jsonResult = new JsonObject();

        String sql = null;
        try {
            conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);
            conn.setAutoCommit(false);

            Map<?, ?> map = JsonUtil.toMap(jsonString);
            Map<String, Object> mapStore = (Map<String, Object>) map.get("store");

            sql = SQLUtil.genInsertSQLString("`store`", mapStore.keySet());
            PreparedStatement stmtStore = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            int count = 1;
            for (String key : mapStore.keySet()) {
                Object value = mapStore.get(key);
                stmtStore.setObject(count, value);
                count++;
            }

            if (stmtStore.executeUpdate() > 0) {
                // execute success
                // get product_id
                ResultSet rs = stmtStore.getGeneratedKeys();
                if (rs.next()) {
                    store_id = rs.getLong(1);
                }
      
                // gen result
                jsonResult.addProperty("store_id", store_id);
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
    
    public static Response deleteStore(int company_id,int store_id) {
        
        Response resp;
        Connection conn = null;
        
        try {
            conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);
            PreparedStatement stmtOrder = conn.prepareStatement("UPDATE `store` SET  status = ? WHERE company_id = ? AND store_id = ?");
            stmtOrder.setLong(1, Status.Deleted.getValue());
            stmtOrder.setLong(2, company_id);
            stmtOrder.setLong(3, store_id);
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
