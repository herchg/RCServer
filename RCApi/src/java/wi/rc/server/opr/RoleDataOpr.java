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
import java.net.URLDecoder;
import javax.ws.rs.core.Response;
import wi.core.db.DBOperation;
import wi.core.db.DSConn;
import wi.core.util.DateTimeUtil;
import wi.core.util.json.JsonUtil;
import wi.core.util.sql.SQLUtil;
import wi.rc.server.Status;
/**
 *
 * @author samuelatwistron
 */
public class RoleDataOpr {
    
    private static String generateSqlQueryString() {

        String sql = "SELECT r.role_id AS role_id, r.company_id AS company_id, c.name AS company_name, r.name AS role_name, r.description AS description, r.status AS status \n"
                + " FROM `role` AS r \n"
                + " LEFT JOIN `company` AS c ON r.company_id = c.company_id ";
        
        return sql;
    }
    
    public static Response selectAllRole(int company_id,String role_id,String role_name, String status) {
        
        Connection conn = null;
        Response resp;
        
        String sqlString;
        int sqlCount = 1;
        
        try {
            conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);
            
            sqlString = generateSqlQueryString() + " AND r.company_id = ? ";
            
            //check input to add sql query string
            if(role_id != null){ sqlString += " AND r.role_id = ? ";}
            if(role_name != null){ sqlString += " AND r.name LIKE ? ";}
            if(status != null){ sqlString += " AND r.status = ? ";}
            
            //create statement and use 
            PreparedStatement pStmt = conn.prepareStatement(sqlString);
            
            pStmt.setInt(sqlCount, company_id);
            sqlCount ++;
            
            if(role_id != null){ 
                pStmt.setInt(sqlCount, Integer.parseInt(role_id));
                sqlCount ++;
            }
            
            if(role_name != null){ 
                pStmt.setString(sqlCount, "%" + URLDecoder.decode(role_name, "UTF-8") + "%");
                sqlCount ++;
            }
            
            if(status != null){ 
                pStmt.setString(sqlCount, status);
                sqlCount ++;
            }
            
            ResultSet rs = pStmt.executeQuery();

            if (!rs.next()) {
                resp = Response.status(Response.Status.NOT_FOUND).build();
            } else {
                // back to first
                rs.previous();
                
                JsonObject jsonResult = new JsonObject();
                JsonElement jsonRole = JsonUtil.toJsonArray(rs);
                jsonResult.add("role", jsonRole);
                resp = Response.status(Response.Status.OK).entity(jsonResult.toString()).build();
            }
            DBOperation.close(pStmt,rs);
            

        } catch (JsonSyntaxException | NullPointerException ex) {
            resp = Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            resp = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        } finally {
            DBOperation.close(conn);
        }

        return resp;
    }
    
    public static Response insertRole(String jsonString) {
        Connection conn = null;
        Response resp;
        boolean ret = true;

        long role_id = -1;
        JsonObject jsonResult = new JsonObject();

        String sql = null;
        try {
            conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);
            conn.setAutoCommit(false);

            Map<?, ?> map = JsonUtil.toMap(jsonString);
            Map<String, Object> mapPos = (Map<String, Object>) map.get("role");

            sql = SQLUtil.genInsertSQLString("`role`", mapPos.keySet());
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
                    role_id = rs.getLong(1);
                }
      
                // gen result
                jsonResult.addProperty("role_id", role_id);
                DBOperation.close(rs);
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
            DBOperation.close(stmtPos);
            
        } catch (JsonSyntaxException | NullPointerException ex) {
            resp = Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            resp = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        } finally {
            DBOperation.close(conn);
        }

        return resp;
    }
    
    public static Response updateRole(int company_id,int role_id,String jsonString) {
        Response resp;

        Connection conn = null;
        boolean ret = true;

        JsonObject jsonResult = new JsonObject();
        String sql = null;

        try {
            conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);

            Map<?, ?> map = JsonUtil.toMap(jsonString);
            Map<String, Object> mapRoleSet = (Map<String, Object>) map.get("role");
            Map<String, Object> mapRoleWhere = new LinkedHashMap<String, Object>();

            mapRoleWhere.put("role_id", role_id);
            mapRoleWhere.put("company_id", company_id);

            mapRoleSet.remove("role_id");
            mapRoleSet.remove("company_id");
            
            sql = SQLUtil.genUpdateSQLString("`role`", mapRoleSet, mapRoleWhere);
            PreparedStatement stmtPos = conn.prepareStatement(sql);

            if (stmtPos.executeUpdate() < 0) {
                ret = false;
            }
            jsonResult.addProperty("role_id", role_id);
            // check ret and commit or rollback
            if (ret) {
                resp = Response.status(Response.Status.OK).entity(jsonResult.toString()).build();
            } else {
                resp = Response.status(Response.Status.BAD_REQUEST).entity(sql).build();
            }
            DBOperation.close(stmtPos);
        } catch (JsonSyntaxException | NullPointerException ex) {
            resp = Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            resp = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        } finally {
            DBOperation.close(conn);
        }

        return resp; 
    }
    
    public static Response deleteRole(int company_id,int role_id) {
        Response resp;
        Connection conn = null;
        
        try {
            conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);
            PreparedStatement stmtRole = conn.prepareStatement("UPDATE `role` SET  status = ? WHERE company_id = ? AND role_id = ?");
            stmtRole.setString(1, Status.Deleted.getValue().toString());
            stmtRole.setLong(2, company_id);
            stmtRole.setLong(3, role_id);
            if (stmtRole.executeUpdate() > 0) {
                resp = Response.status(Response.Status.OK).build();
            } else {
                resp = Response.status(Response.Status.NOT_FOUND).build();
            }
            DBOperation.close(stmtRole);
        } catch (JsonSyntaxException | NullPointerException ex) {
            resp = Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            resp = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        } finally {
            DBOperation.close(conn);
        }

        return resp;
    }
}
