/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wi.rc.server.opr;

import com.google.gson.JsonSyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import javax.ws.rs.core.Response;
import wi.core.db.DSConn;
import wi.rc.data.order.Order;
import wi.rc.data.order.OrderDetail;
import wi.rc.data.order.OrderSet;

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

            public final static String SQL_ORDER_SELECT_ALL
                    = "SELECT order_id, customer_id, company_id, store_id, pos_id, employee_id, ncode, total_amount, order_datetime, log_datetime, status, pos_order_id, memo FROM rc.order";
            public final static String SQL_ORDER_SELECT
                    = "SELECT order_id, customer_id, company_id, store_id, pos_id, employee_id, ncode, total_amount, order_datetime, log_datetime, status, pos_order_id, memo FROM rc.order WHERE order_id = ?";
            public final static String SQL_ORDER_INSERT
                    = "INSERT INTO rc.order (customer_id, company_id, store_id, pos_id, employee_id, ncode, total_amount, order_datetime, log_datetime, status, pos_order_id, memo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, now(), ?, ?, ?)";
        //  public final static String SQL_ORDER_UPDATE
        //          = "UPDATE rc.order SET";
            public final static String SQL_ORDER_DELETE
                    = "DELETE FROM rc.order WHERE order_id = ?";
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
        //  public final static String SQL_ORDER_DETAIL_UPDATE
        //          = "INSERT INTO rc.order_detail (order_id, product_id, price, amount, total_amount) VALUES (?, ?, ?, ?, ?);";
            public final static String SQL_ORDER_DETAIL_DELETE
                    = "DELETE FROM rc.order_detail WHERE order_id = ?";
        }
    }

    public static Response selectOrder(int orderId, String expand) {

        Connection conn = null;
        Response resp;

        try {
            conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);
            PreparedStatement stmtOrder = conn.prepareStatement(OrderDataOpr.DBInfo.OrderInfo.SQL_ORDER_SELECT);
            // "SELECT customer_id, company_id, store_id, pos_id, employee_id, ncode, total_amount, order_datetime, log_datetime, status, pos_order_id, memo FROM rc.order WHERE order_id = ?";
            stmtOrder.setLong(1, orderId);

            ResultSet rs = stmtOrder.executeQuery();
            if (rs.next()) {
                // new an OrderSet
                OrderSet orderSet = new OrderSet();
                // new an Order
                orderSet.mOrder = new Order();
                orderSet.mOrder.mOrderId = rs.getLong(OrderDataOpr.DBInfo.OrderInfo.FIELD_NAME_ORDER_ID);
                orderSet.mOrder.mCustomerId = rs.getInt(OrderDataOpr.DBInfo.OrderInfo.FIELD_NAME_CUSTOMER_ID);
                orderSet.mOrder.mCompanyId = rs.getInt(OrderDataOpr.DBInfo.OrderInfo.FIELD_NAME_COMPANY_ID);
                orderSet.mOrder.mStoreId = rs.getInt(OrderDataOpr.DBInfo.OrderInfo.FIELD_NAME_STORE_ID);
                orderSet.mOrder.mEmployeeId = rs.getInt(OrderDataOpr.DBInfo.OrderInfo.FIELD_NAME_EMPLOYEE_ID);
                orderSet.mOrder.mNcode = rs.getString(OrderDataOpr.DBInfo.OrderInfo.FIELD_NAME_NCODE);
                orderSet.mOrder.mTotalAmount = rs.getInt(OrderDataOpr.DBInfo.OrderInfo.FIELD_NAME_TOTAL_AMOUNT);
                orderSet.mOrder.mOrderDatetime = rs.getDate(OrderDataOpr.DBInfo.OrderInfo.FIELD_NAME_ORDER_DATETIME);
                orderSet.mOrder.mStatus = rs.getString(OrderDataOpr.DBInfo.OrderInfo.FIELD_NAME_STATUS);
                orderSet.mOrder.mPosOrderId = rs.getString(OrderDataOpr.DBInfo.OrderInfo.FIELD_NAME_POS_ORDER_ID);
                orderSet.mOrder.mMemo = rs.getString(OrderDataOpr.DBInfo.OrderInfo.FIELD_NAME_MEMO);

                // new an Order Detail List
                orderSet.mOrderDetail = new ArrayList<OrderDetail>();

                //contain detail or not
                if (expand.equals("detail")) {
                    PreparedStatement stmtOrderDetail = conn.prepareStatement(OrderDataOpr.DBInfo.OrderDetailInfo.SQL_ORDER_DETAIL_SELECT);
                    stmtOrderDetail.setLong(1, orderId);
                    ResultSet rsOrderDetail = stmtOrderDetail.executeQuery();
                    while (rsOrderDetail.next()) {
                        OrderDetail orderDetail = new OrderDetail();
                        orderDetail.mOrderId = rsOrderDetail.getLong(OrderDataOpr.DBInfo.OrderDetailInfo.FIELD_NAME_ORDER_ID);
                        orderDetail.mProductId = rsOrderDetail.getInt(OrderDataOpr.DBInfo.OrderDetailInfo.FIELD_NAME_PRODUCT_ID);
                        orderDetail.mPrice = rsOrderDetail.getInt(OrderDataOpr.DBInfo.OrderDetailInfo.FIELD_NAME_PRICE);
                        orderDetail.mAmount = rsOrderDetail.getInt(OrderDataOpr.DBInfo.OrderDetailInfo.FIELD_NAME_AMOUNT);
                        orderDetail.mTotalAmount = rsOrderDetail.getInt(OrderDataOpr.DBInfo.OrderDetailInfo.FIELD_NAME_TOTAL_AMOUNT);
                        orderSet.mOrderDetail.add(orderDetail);
                    }
                }
                resp = Response.status(Response.Status.CREATED).entity(orderSet.toJson(OrderSet._VERSION)).build();

            } else {
                resp = Response.status(Response.Status.NOT_FOUND).build();
            }

            // check ret and commit or rollback
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

    public static Response insertOrder(String jsonOrderSet) {

        OrderSet orderSet;

        Connection conn = null;
        Response resp;
        boolean ret = true;
        try {
            orderSet = OrderSet.fromJson(jsonOrderSet);

            conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);
            conn.setAutoCommit(false);

            PreparedStatement stmtOrder = conn.prepareStatement(OrderDataOpr.DBInfo.OrderInfo.SQL_ORDER_INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
            stmtOrder.setInt(1, orderSet.mOrder.mCustomerId);
            stmtOrder.setInt(2, orderSet.mOrder.mCompanyId);
            stmtOrder.setInt(3, orderSet.mOrder.mStoreId);
            stmtOrder.setInt(4, orderSet.mOrder.mPosId);
            stmtOrder.setInt(5, orderSet.mOrder.mEmployeeId);
            if (orderSet.mOrder.mNcode == null) {
                stmtOrder.setNull(6, Types.VARCHAR);
            } else {
                stmtOrder.setString(6, orderSet.mOrder.mNcode);
            }
            stmtOrder.setInt(7, orderSet.mOrder.mTotalAmount);
            if (orderSet.mOrder.mOrderDatetime == null) {
                stmtOrder.setNull(8, Types.DATE);
            } else {
                stmtOrder.setDate(8, orderSet.mOrder.mOrderDatetime);
            }
            if (orderSet.mOrder.mStatus == null) {
                stmtOrder.setNull(9, Types.VARCHAR);
            } else {
                stmtOrder.setString(9, orderSet.mOrder.mStatus);
            }
            if (orderSet.mOrder.mPosOrderId == null) {
                stmtOrder.setNull(10, Types.VARCHAR);
            } else {
                stmtOrder.setString(10, orderSet.mOrder.mPosOrderId);
            }
            if (orderSet.mOrder.mMemo == null) {
                stmtOrder.setNull(11, Types.VARCHAR);
            } else {
                stmtOrder.setString(11, orderSet.mOrder.mMemo);
            }

            if (stmtOrder.executeUpdate() > 0) {
                // execute success
                ResultSet rs = stmtOrder.getGeneratedKeys();
                if (rs.next()) {
                    orderSet.mOrder.mOrderId = rs.getLong(1);
                }

                PreparedStatement stmtOrderDetail = conn.prepareStatement(OrderDataOpr.DBInfo.OrderDetailInfo.SQL_ORDER_DETAIL_INSERT);
                for (OrderDetail orderDetail : orderSet.mOrderDetail) {
                    // set order_id
                    orderDetail.mOrderId = orderSet.mOrder.mOrderId;

                    // execute order_detail
                    stmtOrderDetail.setLong(1, orderDetail.mOrderId);
                    stmtOrderDetail.setInt(2, orderDetail.mProductId);
                    stmtOrderDetail.setInt(3, orderDetail.mPrice);
                    stmtOrderDetail.setInt(4, orderDetail.mAmount);
                    stmtOrderDetail.setInt(5, orderDetail.mTotalAmount);
                    if (stmtOrderDetail.executeUpdate() < 0) {
                        ret = false;
                        break;
                    }
                }
            } else {
                // execute failure
                ret = false;
            }

            // check ret and commit or rollback
            if (ret) {
                conn.commit();
                resp = Response.status(Response.Status.CREATED).entity(orderSet.toJson(OrderSet._VERSION)).build();
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
    
    public static Response updateOrder(String jsonOrderSet) {
        
        OrderSet orderSet;

        Connection conn = null;
        Response resp;
        boolean ret = true;
        String sql = "UPDATE rc.order SET";
        try {
            orderSet = OrderSet.fromJson(jsonOrderSet);

            conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);
            conn.setAutoCommit(false);
            
            //if dont need to update the value will be -1
            
            if (orderSet.mOrder.mCustomerId != -1)
                sql += " customer_id = " + orderSet.mOrder.mCustomerId;
            if (orderSet.mOrder.mCompanyId != -1)
                sql += " company_id = " + orderSet.mOrder.mCompanyId;
            if (orderSet.mOrder.mStoreId != -1)
                sql += " store_id = " + orderSet.mOrder.mStoreId;
            if (orderSet.mOrder.mPosId != -1)
                sql += " pos_id = " + orderSet.mOrder.mPosId;
            if (orderSet.mOrder.mEmployeeId != -1)
                sql += " employee_id = " + orderSet.mOrder.mEmployeeId;
            if (orderSet.mOrder.mNcode != null) 
                sql += " ncode = " + orderSet.mOrder.mNcode;
            if (orderSet.mOrder.mOrderDatetime != null) 
                sql += " order_datetime = " + orderSet.mOrder.mOrderDatetime;
            if (orderSet.mOrder.mStatus != null) 
                sql += " status = " + orderSet.mOrder.mStatus;
            if (orderSet.mOrder.mPosOrderId == null) 
                sql += " pos_order_id = " + orderSet.mOrder.mPosOrderId;
            if (orderSet.mOrder.mMemo == null) 
                sql += " memo = " + orderSet.mOrder.mMemo;
            
            PreparedStatement stmtOrder = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            //
            // update order detail , unfinished
            //
            if (stmtOrder.executeUpdate() > 0) {
                // execute success
                ResultSet rs = stmtOrder.getGeneratedKeys();
                if (rs.next()) {
                    orderSet.mOrder.mOrderId = rs.getLong(1);
                }

                PreparedStatement stmtOrderDetail = conn.prepareStatement(OrderDataOpr.DBInfo.OrderDetailInfo.SQL_ORDER_DETAIL_INSERT);
                for (OrderDetail orderDetail : orderSet.mOrderDetail) {
                    // set order_id
                    orderDetail.mOrderId = orderSet.mOrder.mOrderId;

                    // execute order_detail
                    stmtOrderDetail.setLong(1, orderDetail.mOrderId);
                    stmtOrderDetail.setInt(2, orderDetail.mProductId);
                    stmtOrderDetail.setInt(3, orderDetail.mPrice);
                    stmtOrderDetail.setInt(4, orderDetail.mAmount);
                    stmtOrderDetail.setInt(5, orderDetail.mTotalAmount);
                    if (stmtOrderDetail.executeUpdate() < 0) {
                        ret = false;
                        break;
                    }
                }
            } else {
                // execute failure
                ret = false;
            }

            // check ret and commit or rollback
            if (ret) {
                conn.commit();
                resp = Response.status(Response.Status.CREATED).entity(orderSet.toJson(OrderSet._VERSION)).build();
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
    
    public static Response deleteOrder(int orderId) {

        Connection conn = null;
        Response resp;
        
        try {
            conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);
            PreparedStatement stmtOrder = conn.prepareStatement(OrderDataOpr.DBInfo.OrderInfo.SQL_ORDER_DELETE);
            // "DELETE FROM rc.order WHERE order_id = ?";
            stmtOrder.setLong(1, orderId);
            stmtOrder.execute();
            PreparedStatement stmtOrderDetail = conn.prepareStatement(OrderDataOpr.DBInfo.OrderDetailInfo.SQL_ORDER_DETAIL_DELETE);
            // "DELETE FROM rc.order_detail WHERE order_id = ?"
            stmtOrderDetail.setLong(1, orderId);
            stmtOrderDetail.execute();
            resp = Response.status(Response.Status.CREATED).entity(null).build();
            
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
}
