/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wi.core.db;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author hermeschang
 */
public class DSConn {

        private static final Logger logger = LoggerFactory.getLogger(DSConn.class);

    /**
     * 
     * @param name
     * @return 
     */
    public static Connection getConnection(String name) {

        try {
            Context initContext = new InitialContext();
            Context envContext  = (Context)initContext.lookup("java:/comp/env");
            DataSource ds = (DataSource)envContext.lookup(name);
            
            Connection conn = ds.getConnection();
            
            return ds.getConnection();
        } catch (NamingException | SQLException ex) {
            logger.error("getConnection() {}", ex);
            return null;
        }
    }

}
