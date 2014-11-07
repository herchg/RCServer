/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wi.core.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author hermeschang
 */
public class DBOperation {

    public final static void close(Connection conn) {
        closeConnection(conn);
    }
    
    public final static void close(Statement stmt, ResultSet rs, Connection conn) {
        closeResultSet(rs);
        closeStatement(stmt);
        closeConnection(conn);        
    }

    public final static void close(Statement stmt, Connection conn) {
        closeStatement(stmt);
        closeConnection(conn);        
    }
    
    public final static void closeStatement(Statement stmt) {

        try {
            if (stmt != null) {
                stmt.close();
            }
            stmt = null;
        } catch (Exception ex) { }
    }

    public final static void closeResultSet(ResultSet rs) {

        try {
            if (rs != null) {
                rs.close();
            }
            rs = null;
        } catch (Exception ex) { }
    }

    public final static void closeConnection(Connection conn) {

        try {
            if (conn != null) {
                conn.close();
            }
            conn = null;
        } catch (Exception ex) { }
    }

}
