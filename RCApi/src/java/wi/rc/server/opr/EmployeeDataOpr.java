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
public class EmployeeDataOpr {
    
    public static Response selectAllEmployee(int company_id) {
        
        Response resp;
        Connection conn = null;
        try {
            conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);
            PreparedStatement pStmt = conn.prepareStatement("SELECT employee_id , company_id , store_id, employee_code, name, status, login_account \n"
                                + " FROM `employee` \n" 
                                + " WHERE company_id = ?");
            pStmt.setInt(1, company_id);
            ResultSet rs = pStmt.executeQuery();
            
            if (!rs.next()) {
                resp = Response.status(Response.Status.NOT_FOUND).build();
            } else {
                // back to first
                rs.previous();
     
                JsonObject jsonResult = new JsonObject();
                JsonElement jsonEmployee = JsonUtil.toJsonArray(rs);
                jsonResult.add("employee", jsonEmployee);
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
    
    public static Response selectEmployeeById(int company_id ,int employee_id,String expand) {
        
        Connection conn = null;
        Response resp;
        try {
            conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);

            PreparedStatement pStmt = conn.prepareStatement("SELECT employee_id , company_id , store_id, employee_code, name, status, login_account \n"
                    + " FROM `employee` \n" 
                    + " WHERE company_id = ? AND employee_id = ?");
            pStmt.setInt(1, company_id);
            pStmt.setInt(2, employee_id);
            ResultSet rs = pStmt.executeQuery();
            
            if (!rs.next()) {
                resp = Response.status(Response.Status.NOT_FOUND).build();
            } else {
                // back to first
                rs.previous();
     
                JsonObject jsonResult = new JsonObject();
                JsonElement jsonEmployee = JsonUtil.toJsonArray(rs);
                jsonResult.add("employee", jsonEmployee);
                
                if (expand != null && expand.equals("ext")) {
                    PreparedStatement stmtEmployeeExt = conn.prepareStatement("SELECT address, contact, tel, mobile, email \n"
                            + " FROM `employee_ext` \n"
                            + " WHERE employee_id = ?");
                    stmtEmployeeExt.setLong(1, employee_id);
                    ResultSet rsEmployeeExt = stmtEmployeeExt.executeQuery();
                    JsonElement jsonEmployeeExt = JsonUtil.toJsonArray(rsEmployeeExt);
                    jsonResult.add("employee_ext", jsonEmployeeExt);
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
    
    
    public static Response selectEmployeeByStoreId(int company_id,int store_id) {
        
        Connection conn = null;
        Response resp;
        try {
            conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);

            PreparedStatement pStmt = conn.prepareStatement("SELECT employee_id , company_id , store_id, employee_code, name, status, login_account \n"
                    + " FROM `employee` \n" 
                    + " WHERE company_id = ? AND store_id = ?");
            pStmt.setInt(1, company_id);
            pStmt.setInt(2, store_id);
            ResultSet rs = pStmt.executeQuery();
            
            if (!rs.next()) {
                resp = Response.status(Response.Status.NOT_FOUND).build();
            } else {
                // back to first
                rs.previous();
     
                JsonObject jsonResult = new JsonObject();
                JsonElement jsonEmployee = JsonUtil.toJsonArray(rs);
                jsonResult.add("employee", jsonEmployee);
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
    

    public static Response selectEmployeeByName(int company_id,String employee_name) {
        
        Connection conn = null;
        Response resp;
        try {
            conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);

            PreparedStatement pStmt = conn.prepareStatement("SELECT employee_id , company_id , store_id, employee_code, name, status, login_account \n"
                    + " FROM `employee` \n" 
                    + " WHERE company_id = ? AND name LIKE ?");
            pStmt.setInt(1, company_id);
            pStmt.setString(2, "%" + employee_name + "%");
            ResultSet rs = pStmt.executeQuery();
            
            if (!rs.next()) {
                resp = Response.status(Response.Status.NOT_FOUND).build();
            } else {
                // back to first
                rs.previous();
     
                JsonObject jsonResult = new JsonObject();
                JsonElement jsonEmployee = JsonUtil.toJsonArray(rs);
                jsonResult.add("employee", jsonEmployee);
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
    
    public static Response insertEmployee(String jsonString) {

        Connection conn = null;
        Response resp;
        boolean ret = true;

        long employee_id = -1;
        JsonObject jsonResult = new JsonObject();

        String sql = null;
        try {
            conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);
            conn.setAutoCommit(false);

            Map<?, ?> map = JsonUtil.toMap(jsonString);
            Map<String, Object> mapEmployee = (Map<String, Object>) map.get("employee");

            sql = SQLUtil.genInsertSQLString("`employee`", mapEmployee.keySet());
            PreparedStatement stmtEmployee = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            int count = 1;
            for (String key : mapEmployee.keySet()) {
                Object value = mapEmployee.get(key);
                stmtEmployee.setObject(count, value);
                count++;
            }

            if (stmtEmployee.executeUpdate() > 0) {
                // execute success
                // get product_id
                ResultSet rs = stmtEmployee.getGeneratedKeys();
                if (rs.next()) {
                    employee_id = rs.getLong(1);
                }
                
                PreparedStatement stmtEmployeeExt = null;
                Map<String, Object> mapEmployeeExt = (Map<String, Object>) map.get("employee_ext");
                // add product_id
                mapEmployeeExt.put("employee_id", employee_id);
                // prepare sql and statement
                if (stmtEmployeeExt == null) {
                    sql = SQLUtil.genInsertSQLString("`employee_ext`", mapEmployeeExt.keySet());
                    stmtEmployeeExt = conn.prepareStatement(sql);
                }
                count = 1;
                for (String key : mapEmployeeExt.keySet()) {
                    Object value = mapEmployeeExt.get(key);
                    stmtEmployeeExt.setObject(count, value);
                    count++;
                }
                if (stmtEmployeeExt.executeUpdate() < 0) {
                    ret = false;
                }
                // gen result
                jsonResult.addProperty("employee_id", employee_id);
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
    
    public static Response loginEmployee(String jsonString) {
        
        Connection conn = null;
        Response resp;
        boolean ret = false;
        /*
        try {
            Map<?, ?> map = JsonUtil.toMap(jsonString);
            Map<String, Object> mapEmployee = (Map<String, Object>) map.get("employee");
            
            int company_id = ((Double)mapEmployee.get("company_id")).intValue();
            int employee_id = ((Double)mapEmployee.get("employee_id")).intValue();
            String login_account = (String)mapEmployee.get("login_account");
            String login_password = (String)mapEmployee.get("login_password");
            
            //check input
            if(company_id > 0 && employee_id > 0 && login_account != null && login_password != null){
                
                conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);
                PreparedStatement pStmt = conn.prepareStatement("SELECT login_password ,status \n"
                        + " FROM `employee` WHERE company_id = ? AND employee_id = ? AND login_account =? ");
                
                pStmt.setInt(1, company_id);
                pStmt.setInt(2, employee_id);
                pStmt.setString(3, login_account);
        
                ResultSet rs = pStmt.executeQuery();

                if (rs.next()) {
                    //check password and status
                    String password = rs.getString("login_password");
                    int status = rs.getInt("status");
        
                    //password ok and status = 1
                    if(login_password.equals(password) && status == 1 ){
                        ret = true;
                    }
                }
            }
            
            
            if (ret) {
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
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (Exception ex) {

                }
            }
        }
        */
        resp = Response.status(Response.Status.OK).build();
        
        return resp;
    } 
    
    
    public static Response updateEmployee(int employee_id, String jsonOrderSet) {
        
        return null;
    }
    
    
    public static Response deleteEmployee(int company_id,int employee_id) {
        
        Response resp;
        Connection conn = null;
        
        try {
            conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);
            PreparedStatement stmtEmployee = conn.prepareStatement("UPDATE `employee` SET  status = ? WHERE company_id = ? AND employee_id = ?");
            stmtEmployee.setLong(1, Status.Deleted.getValue());
            stmtEmployee.setLong(2, company_id);
            stmtEmployee.setLong(3, employee_id);
            if (stmtEmployee.executeUpdate() > 0) {
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
