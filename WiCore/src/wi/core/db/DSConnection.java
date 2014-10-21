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
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author hermeschang
 */
public class DSConnection implements IDSConnection {

    /**
     * 
     * @param name
     * @return 
     */
    @Override
    public Connection getConnection(String name) {

        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup(name);
            DataSource ds = (DataSource) envContext.lookup("jdbc/myoracle");
            return ds.getConnection();
        } catch (NamingException | SQLException ex) {
            
            return null;

        }
    }

}
