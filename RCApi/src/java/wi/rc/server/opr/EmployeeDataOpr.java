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
public class EmployeeDataOpr {

    private static String generateSqlQueryString() {

        String sql = "SELECT e.employee_id AS employee_id, e.company_id AS company_id, e.store_id AS store_id, e.employee_code AS employee_code, e.name AS employee_name, "
                + " e.status AS status, IFNULL('','e.photo') AS photo, e.login_account AS login_account,e.role_id AS role_id,r.name AS role_name,e.on_board_date AS on_board_date,IFNULL('','e.leave_date') AS leave_date \n"
                + " FROM `employee` AS e \n"
                + " LEFT JOIN `role` AS r ON e.role_id = r.role_id ";

        return sql;
    }

    private static String generateSqlQueryExtString() {

        String sql = "SELECT IFNULL('','official_id') AS official_id,IFNULL('','gender') AS gender,IFNULL('','address') AS address,IFNULL('','contact') AS contact, \n"
                + " IFNULL('','tel') AS tel, IFNULL('','mobile') AS mobile, IFNULL('','email') AS email \n"
                + " FROM `employee_ext` \n"
                + " WHERE employee_id = ?";
        return sql;
    }

    public static Response selectAllEmployee(int company_id,String employee_id,String role_id,String store_id,String employee_name,String status,String expand) {

        Response resp;
        Connection conn = null;
        
        String sqlString;
        int sqlCount = 1;
        try {
            conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);
            
            sqlString = generateSqlQueryString() + " WHERE e.company_id = ? ";

            if(employee_id != null){ sqlString += " AND e.employee_id = ? ";}
            if(role_id != null){ sqlString += " AND e.role_id = ? ";}
            if(store_id != null){ sqlString += " AND e.store_id = ? ";}
            if(employee_name != null){ sqlString += " AND e.name LIKE ? ";}
            if(status != null){ sqlString += " AND e.status = ? ";}
            
            PreparedStatement pStmt = conn.prepareStatement(sqlString);
            
            pStmt.setInt(sqlCount, company_id);
            sqlCount ++;
            
            if(employee_id != null){ 
                pStmt.setInt(sqlCount, Integer.parseInt(employee_id));
                sqlCount ++;
            }
            
            if(role_id != null){ 
                pStmt.setInt(sqlCount, Integer.parseInt(role_id));
                sqlCount ++;
            }
            
            if(store_id != null){ 
                pStmt.setInt(sqlCount, Integer.parseInt(store_id));
                sqlCount ++;
            }
            
            if(employee_name != null){ 
                pStmt.setString(sqlCount, "%" + URLDecoder.decode(employee_name, "UTF-8") + "%" );
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
                JsonArray jsonResult = new JsonArray();
                while (rs.next()) {
                    int employeeId = rs.getInt("employee_id");

                    // back because next
                    rs.previous();

                    JsonObject jsonRow = new JsonObject();
                    JsonElement jsonEmployee = JsonUtil.toJsonElement(rs);
                    jsonRow.add("employee", jsonEmployee);

                    if (expand != null && expand.equals("ext")) {
                        PreparedStatement stmtEmployeeDetail = conn.prepareStatement(generateSqlQueryExtString());
                        stmtEmployeeDetail.setInt(1, employeeId);
                        ResultSet rsEmployeeDetail = stmtEmployeeDetail.executeQuery();
                        JsonElement elementEmployeeDetail = JsonUtil.toJsonArray(rsEmployeeDetail);
                        jsonRow.add("employee_ext", elementEmployeeDetail);
                        
                        DBOperation.close(stmtEmployeeDetail,rsEmployeeDetail);
                    }
                    jsonResult.add(jsonRow);
                }
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
                DBOperation.close(rs);
                DBOperation.close(stmtEmployeeExt);
            } else {
                // execute failure
                ret = false;
            }
            DBOperation.close(stmtEmployee);    
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
            DBOperation.close(conn);
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

    public static Response updateEmployee(int companyId, int employeeId, String jsonString) {

        Response resp;

        Connection conn = null;
        boolean ret = true;

        JsonObject jsonResult = new JsonObject();
        String sql = null;

        try {
            conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);

            Map<?, ?> map = JsonUtil.toMap(jsonString);
            Map<String, Object> mapEmployeeSet = (Map<String, Object>) map.get("employee");
            Map<String, Object> mapEmployeeWhere = new LinkedHashMap<String, Object>();

            mapEmployeeWhere.put("employee_id", employeeId);
            mapEmployeeWhere.put("company_id", companyId);

            mapEmployeeSet.remove("employee_id");
            mapEmployeeSet.remove("company_id");

            sql = SQLUtil.genUpdateSQLString("`employee`", mapEmployeeSet, mapEmployeeWhere);
            PreparedStatement stmtEmployee = conn.prepareStatement(sql);

            if (stmtEmployee.executeUpdate() > 0) {

                Map<String, Object> mapEmployeeExtSet = (Map<String, Object>) map.get("employee_ext");
                Map<String, Object> mapEmployeeExtWhere = new LinkedHashMap<String, Object>();

                mapEmployeeExtWhere.put("employee_id", employeeId);

                mapEmployeeExtSet.remove("employee_id");

                sql = SQLUtil.genUpdateSQLString("`employee_ext`", mapEmployeeExtSet, mapEmployeeExtWhere);
                PreparedStatement stmtEmployeeExt = conn.prepareStatement(sql);
                if (stmtEmployeeExt.executeUpdate() < 0) {
                    ret = false;
                }
                DBOperation.close(stmtEmployeeExt);

            } else {
                ret = false;
            }

            jsonResult.addProperty("employee_id", employeeId);
            DBOperation.close(stmtEmployee);
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
            DBOperation.close(conn);
        }

        return resp;
    }

    public static Response deleteEmployee(int company_id, int employee_id) {

        Response resp;
        Connection conn = null;

        try {
            conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);
            String sql = "UPDATE `employee` SET  status = ? WHERE company_id = ? AND employee_id = ?";
            PreparedStatement stmtEmployee = conn.prepareStatement(sql);
            stmtEmployee.setString(1, Status.Deleted.getValue().toString());
            stmtEmployee.setLong(2, company_id);
            stmtEmployee.setLong(3, employee_id);
            if (stmtEmployee.executeUpdate() > 0) {
                resp = Response.status(Response.Status.OK).build();
            } else {
                resp = Response.status(Response.Status.NOT_FOUND).build();
            }
           
            DBOperation.close(stmtEmployee);
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
