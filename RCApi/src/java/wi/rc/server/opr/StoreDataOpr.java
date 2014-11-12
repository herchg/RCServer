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
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.ws.rs.core.Response;
import wi.core.db.DBOperation;
import wi.core.db.DSConn;
import wi.core.util.json.JsonUtil;
import wi.core.util.sql.SQLUtil;
import wi.rc.server.Status;
/**
 *
 * @author samuelatwistron
 */
public class StoreDataOpr {
    
    private static String generateSqlQueryString() {

        String sql = "SELECT s.store_id AS store_id, s.name AS name, s.contact AS contact, s.tel AS tel, s.mobile AS mobile,  s.email AS email,"
                    + " s.address AS address, s.business_id AS business_id,s.memo AS memo, s.status AS status, s.company_id AS company_id, "
                    + "c.name AS company_name,s.manager_employee_id AS manager_employee_id ,e.name AS manager_name\n" 
                    + " FROM `store` AS s \n" 
                    + " LEFT JOIN `company` AS c ON s.company_id = c.company_id \n" 
                    + " LEFT JOIN `employee` AS e ON s.manager_employee_id = e.employee_id ";
        
        return sql;
    }
    
    public static Response selectAllStore(int company_id,String store_id,String store_name,String employee_id,String employee_name) {
        
        Connection conn = null;
        Response resp;
        
        String sqlString;
        int sqlCount = 1;
        try {
            conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);
            
            sqlString = generateSqlQueryString() + " WHERE s.company_id = ? ";

            if(store_id != null){ sqlString += " AND s.store_id = ? ";}
            if(store_name != null){ sqlString += " AND s.name LIKE ? ";}
            if(employee_id != null){ sqlString += " AND s.employee_id = ? ";}
            if(employee_name != null){ sqlString += " AND e.name LIKE ? ";}
            
            PreparedStatement pStmt = conn.prepareStatement(sqlString);

            pStmt.setInt(sqlCount, company_id);
            sqlCount ++;
            
            if(store_id != null){ 
                pStmt.setInt(sqlCount, Integer.parseInt(store_id));
                sqlCount ++;
            }
            
            if(store_name != null){ 
                pStmt.setString(sqlCount, "%" + URLDecoder.decode(store_name, "UTF-8") + "%" );
                sqlCount ++;
            }
            
            if(employee_id != null){ 
                pStmt.setInt(sqlCount, Integer.parseInt(employee_id));
                sqlCount ++;
            }
            
            if(employee_name != null){ 
                pStmt.setString(sqlCount, "%" + URLDecoder.decode(employee_name, "UTF-8") + "%" );
                sqlCount ++;
            }

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
            DBOperation.close(pStmt, rs);
        } catch (JsonSyntaxException | NullPointerException ex) {
            resp = Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            resp = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        } finally {
            DBOperation.close(conn);
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
            DBOperation.close(stmtStore);
        } catch (JsonSyntaxException | NullPointerException ex) {
            resp = Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            resp = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        } finally {
            DBOperation.close(conn);
        }

        return resp;
    }
    
    public static Response updateStore(int companyId, int storeId, String jsonString){
        
        Response resp;

        Connection conn = null;
        boolean ret = true;

        JsonObject jsonResult = new JsonObject();
        String sql = null;

        try {
            conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);

            Map<?, ?> map = JsonUtil.toMap(jsonString);
            Map<String, Object> mapStoreSet = (Map<String, Object>) map.get("store");
            Map<String, Object> mapStoreWhere = new LinkedHashMap<String, Object>();

            mapStoreWhere.put("store_id", storeId);
            mapStoreWhere.put("company_id", companyId);
            
            mapStoreSet.remove("store_id");
            mapStoreSet.remove("company_id");

            sql = SQLUtil.genUpdateSQLString("`store`", mapStoreSet, mapStoreWhere);
            PreparedStatement stmtStore = conn.prepareStatement(sql);

            if (stmtStore.executeUpdate() < 0) {
                ret = false;
            }

            jsonResult.addProperty("store_id", storeId);

            // check ret and commit or rollback
            if (ret) {
                resp = Response.status(Response.Status.OK).entity(jsonResult.toString()).build();
            } else {
                resp = Response.status(Response.Status.BAD_REQUEST).entity(sql).build();
            }
            DBOperation.close(stmtStore);
        } catch (JsonSyntaxException | NullPointerException ex) {
            resp = Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            resp = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        } finally {
            DBOperation.close(conn);
        }

        return resp;
    }
    
    public static Response deleteStore(int company_id, int store_id) {
        
        Response resp;
        Connection conn = null;
        
        try {
            conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);
            PreparedStatement stmtStore = conn.prepareStatement("UPDATE `store` SET  status = ? WHERE company_id = ? AND store_id = ?");
            stmtStore.setString(1, Status.Deleted.getValue().toString());
            stmtStore.setLong(2, company_id);
            stmtStore.setLong(3, store_id);
            if (stmtStore.executeUpdate() > 0) {
                resp = Response.status(Response.Status.OK).build();
            } else {
                resp = Response.status(Response.Status.NOT_FOUND).build();
            }
            DBOperation.close(stmtStore);
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
