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
public class PaymentDataOpr {
    
    private static String generateSqlQueryString() {

        String sql = "SELECT payment_id, name, status FROM `payment` ";
        
        return sql;
    }
    
    public static Response selectAllPayments() {

        Connection conn = null;
        Response resp;
        
        String sqlString;
        int sqlCount = 1;
        
        try {
            conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);
            
            sqlString = generateSqlQueryString();
            
            //create statement and use 
            PreparedStatement pStmt = conn.prepareStatement(sqlString);
            
            ResultSet rs = pStmt.executeQuery();

            if (!rs.next()) {
                resp = Response.status(Response.Status.NOT_FOUND).build();
            } else {
                // back to first
                rs.previous();
                
                JsonObject jsonResult = new JsonObject();
                JsonElement jsonPayment = JsonUtil.toJsonArray(rs);
                jsonResult.add("payment", jsonPayment);
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

}
