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
public class PosDataOpr {
    
    public static Response selectAllPos(int company_id) {
        
        Connection conn = null;
        Response resp;
        try {
            conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);
            PreparedStatement pStmt = conn.prepareStatement("SELECT p.pos_id AS pos_id, p.name AS name, p.company_id AS company_id,c.name AS company_name, "
                    + " p.store_id AS store_id,s.name AS store_name, p.memo AS memo, p.status AS status \n" 
                    + " FROM `pos`  AS p \n" 
                    + " LEFT JOIN `company` AS c ON p.company_id = c.company_id \n" 
                    + " LEFT JOIN `store` AS s ON p.store_id = s.store_id \n" 
                    + " WHERE p.company_id = ?");
            
            pStmt.setInt(1, company_id);
            ResultSet rs = pStmt.executeQuery();
            
            if (!rs.next()) {
                resp = Response.status(Response.Status.NOT_FOUND).build();
            } else {
                // back to first
                rs.previous();
     
                JsonObject jsonResult = new JsonObject();
                JsonElement jsonProduct = JsonUtil.toJsonArray(rs);
                jsonResult.add("pos", jsonProduct);
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
    
    public static Response selectPosById(int company_id , int pos_id) {
        
        Connection conn = null;
        Response resp;
        try {
            conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);
            PreparedStatement pStmt = conn.prepareStatement("SELECT p.pos_id AS pos_id, p.name AS name, p.company_id AS company_id,c.name AS company_name, "
                    + " p.store_id AS store_id,s.name AS store_name, p.memo AS memo, p.status AS status \n" 
                    + " FROM `pos`  AS p \n" 
                    + " LEFT JOIN `company` AS c ON p.company_id = c.company_id \n" 
                    + " LEFT JOIN `store` AS s ON p.store_id = s.store_id \n" 
                    + " WHERE p.company_id = ? AND p.pos_id = ?");
            
            pStmt.setInt(1, company_id);
            pStmt.setInt(2, pos_id);
            ResultSet rs = pStmt.executeQuery();
            
            if (!rs.next()) {
                resp = Response.status(Response.Status.NOT_FOUND).build();
            } else {
                // back to first
                rs.previous();
     
                JsonObject jsonResult = new JsonObject();
                JsonElement jsonProduct = JsonUtil.toJsonArray(rs);
                jsonResult.add("pos", jsonProduct);
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
    
    public static Response selectPosByStore(int company_id , int store_id) {
        
        Connection conn = null;
        Response resp;
        try {
            conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);
            PreparedStatement pStmt = conn.prepareStatement("SELECT p.pos_id AS pos_id, p.name AS name, p.company_id AS company_id,c.name AS company_name, "
                    + " p.store_id AS store_id,s.name AS store_name, p.memo AS memo, p.status AS status \n" 
                    + " FROM `pos`  AS p \n" 
                    + " LEFT JOIN `company` AS c ON p.company_id = c.company_id \n" 
                    + " LEFT JOIN `store` AS s ON p.store_id = s.store_id \n" 
                    + " WHERE p.company_id = ? AND p.store_id = ?");
            
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
                jsonResult.add("pos", jsonProduct);
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
    
    public static Response selectPosByName(int company_id , String store_name) {
        
        Connection conn = null;
        Response resp;
        try {
            conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);
            PreparedStatement pStmt = conn.prepareStatement("SELECT p.pos_id AS pos_id, p.name AS name, p.company_id AS company_id,c.name AS company_name, "
                    + " p.store_id AS store_id,s.name AS store_name, p.memo AS memo, p.status AS status \n" 
                    + " FROM `pos`  AS p \n" 
                    + " LEFT JOIN `company` AS c ON p.company_id = c.company_id \n" 
                    + " LEFT JOIN `store` AS s ON p.store_id = s.store_id \n" 
                    + " WHERE p.company_id = ? AND p.name LIKE ?");
            
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
                jsonResult.add("pos", jsonProduct);
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
    
    public static Response insertPos(String jsonString) {

        Connection conn = null;
        Response resp;
        boolean ret = true;

        long pos_id = -1;
        JsonObject jsonResult = new JsonObject();

        String sql = null;
        try {
            conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);
            conn.setAutoCommit(false);

            Map<?, ?> map = JsonUtil.toMap(jsonString);
            Map<String, Object> mapPos = (Map<String, Object>) map.get("pos");

            sql = SQLUtil.genInsertSQLString("`pos`", mapPos.keySet());
            PreparedStatement stmtPos = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            int count = 1;
            for (String key : mapPos.keySet()) {
                Object value = mapPos.get(key);
                stmtPos.setObject(count, value);
                count++;
            }

            if (stmtPos.executeUpdate() > 0) {
                // execute success
                // get product_id
                ResultSet rs = stmtPos.getGeneratedKeys();
                if (rs.next()) {
                    pos_id = rs.getLong(1);
                }
      
                // gen result
                jsonResult.addProperty("pos_id", pos_id);
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
    
    public static Response deletePos(int company_id,int pos_id) {
        
        Response resp;
        Connection conn = null;
        
        try {
            conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);
            PreparedStatement stmtOrder = conn.prepareStatement("UPDATE `pos` SET  status = ? WHERE company_id = ? AND pos_id = ?");
            stmtOrder.setLong(1, Status.Deleted.getValue());
            stmtOrder.setLong(2, company_id);
            stmtOrder.setLong(3, pos_id);
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