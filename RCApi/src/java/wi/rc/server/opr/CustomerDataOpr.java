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
public class CustomerDataOpr {
    
    private static String generateSqlQueryString() {

        String sql = "SELECT cu.customer_id AS customer_id, cu.name AS name, cu.login_id AS login_id, cu.login_password AS login_password, "
                    + " cu.point AS point, cu.company_id AS company_id, c.name AS company_name, cu.store_id AS store_id,s.name AS store_name,cu.active_datetime AS active_datetime, "
                    + " cu.expiry_datetime AS expiry_datetime, cu.reg_datetime AS reg_datetime, cu.status AS status \n" 
                    + " FROM `customer` AS cu\n" 
                    + " LEFT JOIN `company` AS c ON cu.company_id = c.company_id \n" 
                    + " LEFT JOIN `store` AS s ON cu.store_id = s.store_id ";
        
        return sql;
    }
    
    private static String generateSqlQueryDetailString() {

        String sql = "SELECT address, contact, tel, mobile, email, birthday, sex FROM `customer_detail` \n"
                    + " WHERE customer_id = ?";
        
        return sql;
    }
    
    public static Response selectAllCustomer(int company_id,String customer_id,String store_id,String customer_name,String expand) {
        
        Connection conn = null;
        Response resp;
        
        String sqlString;
        int sqlCount = 1;
        try {
            conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);
            
            sqlString = generateSqlQueryString() + " WHERE cu.company_id = ? ";

            if(customer_id != null){ sqlString += " AND cu.customer_id = ? ";}
            if(store_id != null){ sqlString += " AND cu.store_id = ? ";}
            if(customer_name != null){ sqlString += " AND cu.name LIKE ? ";}
            
            PreparedStatement pStmt = conn.prepareStatement(sqlString);
            
            pStmt.setInt(sqlCount, company_id);
            sqlCount ++;
            
            if(customer_id != null){ 
                pStmt.setInt(sqlCount, Integer.parseInt(customer_id));
                sqlCount ++;
            }
            
            if(store_id != null){ 
                pStmt.setInt(sqlCount, Integer.parseInt(store_id));
                sqlCount ++;
            }
            
            if(customer_name != null){ 
                pStmt.setString(sqlCount, "%" + customer_name + "%" );
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
                    int customerId = rs.getInt("customer_id");

                    // back because next
                    rs.previous();

                    JsonObject jsonRow = new JsonObject();
                    JsonElement jsonCustomer = JsonUtil.toJsonElement(rs);
                    jsonRow.add("customer", jsonCustomer);

                    if (expand != null && expand.equals("detail")) {
                        PreparedStatement stmtCustomerDetail = conn.prepareStatement(generateSqlQueryDetailString());
                        stmtCustomerDetail.setInt(1, customerId);
                        ResultSet rsCustomerDetail = stmtCustomerDetail.executeQuery();
                        JsonElement elementCustomerDetail = JsonUtil.toJsonArray(rsCustomerDetail);
                        jsonRow.add("customer_detail", elementCustomerDetail);
                        
                        DBOperation.close(stmtCustomerDetail,rsCustomerDetail);
                    }
                    jsonResult.add(jsonRow);
                }
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
                DBOperation.close(stmtCustomer, rs);
                
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
                DBOperation.close(stmtCustomerDetail);
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
            DBOperation.close(stmtCustomer);
        } catch (JsonSyntaxException | NullPointerException ex) {
            resp = Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            resp = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        } finally {
            DBOperation.close(conn);
        }

        return resp;
    }
    
    public static Response updateCustomer(int companyId, int customerId, String jsonString) {
        
        Response resp;

        Connection conn = null;
        boolean ret = true;

        JsonObject jsonResult = new JsonObject();
        String sql = null;

        try {
            conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);

            Map<?, ?> map = JsonUtil.toMap(jsonString);
            Map<String, Object> mapCustomerSet = (Map<String, Object>) map.get("customer");
            Map<String, Object> mapCustomerWhere = new LinkedHashMap<String, Object>();

            mapCustomerWhere.put("customer_id", customerId);
            mapCustomerWhere.put("company_id", companyId);

            mapCustomerSet.remove("customer_id");
            mapCustomerSet.remove("company_id");

            sql = SQLUtil.genUpdateSQLString("`customer`", mapCustomerSet, mapCustomerWhere);
            PreparedStatement stmtCustomer = conn.prepareStatement(sql);

            if (stmtCustomer.executeUpdate() > 0) {

                Map<String, Object> mapCustomerDetailSet = (Map<String, Object>) map.get("customer_detail");
                Map<String, Object> mapCustomerDetailWhere = new LinkedHashMap<String, Object>();

                mapCustomerDetailWhere.put("customer_id", customerId);

                mapCustomerDetailSet.remove("customer_id");

                sql = SQLUtil.genUpdateSQLString("`customer_detail`", mapCustomerDetailSet, mapCustomerDetailWhere);
                PreparedStatement stmtEmployeeExt = conn.prepareStatement(sql);
                if (stmtEmployeeExt.executeUpdate() < 0) {
                    ret = false;
                }
                DBOperation.close(stmtEmployeeExt);

            } else {
                ret = false;
            }

            jsonResult.addProperty("customer_id", customerId);
            DBOperation.close(stmtCustomer);
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
    
    public static Response deleteCustomer(int company_id, int customer_id) {
        
        Response resp;
        Connection conn = null;
        
        try {
            conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);
            PreparedStatement stmtOrder = conn.prepareStatement("UPDATE `customer` SET  status = ? WHERE company_id = ? AND customer_id = ?");
            stmtOrder.setString(1, Status.Deleted.getValue().toString());
            stmtOrder.setLong(2, company_id);
            stmtOrder.setLong(3, customer_id);
            if (stmtOrder.executeUpdate() > 0) {
                resp = Response.status(Response.Status.OK).build();
            } else {
                resp = Response.status(Response.Status.NOT_FOUND).build();
            }
            DBOperation.close(stmtOrder);
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
