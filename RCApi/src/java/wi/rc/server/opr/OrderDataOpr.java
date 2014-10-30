/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wi.rc.server.opr;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import wi.core.db.DSConn;
import wi.rc.data.order.OrderDetail;

/**
 *
 * @author hermeschang
 */
public class OrderDataOpr {

    public class DBInfo {

        public class OrderInfo {

            public final static String FIELD_NAME_ORDER_ID = "order_id";
            public final static String FIELD_NAME_CUSTOMER_ID = "customer_id";
            public final static String FIELD_NAME_COMPANY_ID = "company_id";
            public final static String FIELD_NAME_STORE_ID = "store_id";
            public final static String FIELD_NAME_POS_ID = "pos_id";
            public final static String FIELD_NAME_EMPLOYEE_ID = "employee_id";
            public final static String FIELD_NAME_NCODE = "ncode";
            public final static String FIELD_NAME_TOTAL_AMOUNT = "total_amount";
            public final static String FIELD_NAME_ORDER_DATETIME = "order_datetime";
            public final static String FIELD_NAME_LOG_DATETIME = "log_datetime";
            public final static String FIELD_NAME_STATUS = "status";
            public final static String FIELD_NAME_POS_ORDER_ID = "pos_order_id";
            public final static String FIELD_NAME_MEMO = "memo";
            public final static String SQL_ORDER_SELECT
                    = "SELECT order_id, customer_id, company_id, store_id, pos_id, employee_id, ncode, total_amount, order_datetime, log_datetime, status, pos_order_id, memo FROM rc.order WHERE order_id = ?";
            public final static String SQL_ORDER_INSERT
                    = "INSERT INTO rc.order (customer_id, company_id, store_id, pos_id, employee_id, ncode, total_amount, order_datetime, log_datetime, status, pos_order_id, memo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, now(), ?, ?, ?)";
        }

        public class OrderDetailInfo {

            public final static String FIELD_NAME_ORDER_ID = "order_id";
            public final static String FIELD_NAME_PRODUCT_ID = "product_id";
            public final static String FIELD_NAME_PRICE = "price";
            public final static String FIELD_NAME_AMOUNT = "amount";
            public final static String FIELD_NAME_TOTAL_AMOUNT = "total_amount";

            public final static String SQL_ORDER_DETAIL_SELECT
                    = "SELECT order_id, product_id, price, amount, total_amount FROM rc.order_detail WHERE order_id = ? ORDER BY product_id";
            public final static String SQL_ORDER_DETAIL_SELECT_2
                    = "SELECT order_id, product_id, price, amount, total_amount FROM rc.order_detail WHERE order_id = ? AND product_id = ?";
            public final static String SQL_ORDER_DETAIL_INSERT
                    = "INSERT INTO rc.order_detail (order_id, product_id, price, amount, total_amount) VALUES (?, ?, ?, ?, ?);";

        }
        
        // TODO operation here

    }
}
