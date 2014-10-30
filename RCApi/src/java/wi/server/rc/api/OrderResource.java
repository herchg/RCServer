/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wi.server.rc.api;

import com.google.gson.JsonSyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import wi.core.db.DSConn;
import wi.rc.data.order.Order;
import wi.rc.data.order.OrderDetail;
import wi.rc.data.order.OrderSet;
import wi.server.rc.api.order.OrderDBInfo;

@Path("order")
public class OrderResource {

    @Context
    private UriInfo context;

    public OrderResource() {
    }

//    /**
//     * Get order by orderId
//     */
//    @GET
//    @Path("/")
//    @Produces("application/json")
//    public String getOrder(@QueryParam("detail") int detail) {
//        // TODO get order info
//        return "Get all orders";
//    }
    
    @GET
    @Path("/{orderId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrder(@PathParam("orderId")int orderId) {

        Connection conn = null;
        Response resp;
        
        try {
            conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);
            PreparedStatement stmtOrder = conn.prepareStatement(OrderDBInfo.OrderInfo.SQL_ORDER_SELECT);
            // "SELECT customer_id, company_id, store_id, pos_id, employee_id, ncode, total_amount, order_datetime, log_datetime, status, pos_order_id, memo FROM rc.order WHERE order_id = ?";
            stmtOrder.setLong(1, orderId);

            ResultSet rs = stmtOrder.executeQuery();
            if (rs.next()) {
                // new an OrderSet
                OrderSet orderSet = new OrderSet();
                // new an Order
                orderSet.mOrder = new Order();
                orderSet.mOrder.mOrderId = rs.getLong(OrderDBInfo.OrderInfo.FIELD_NAME_ORDER_ID);
                orderSet.mOrder.mCustomerId = rs.getInt(OrderDBInfo.OrderInfo.FIELD_NAME_ORDER_ID);
                orderSet.mOrder.mCompanyId = rs.getInt(OrderDBInfo.OrderInfo.FIELD_NAME_COMPANY_ID);
                orderSet.mOrder.mStoreId = rs.getInt(OrderDBInfo.OrderInfo.FIELD_NAME_STORE_ID);
                orderSet.mOrder.mEmployeeId = rs.getInt(OrderDBInfo.OrderInfo.FIELD_NAME_EMPLOYEE_ID);
                orderSet.mOrder.mNcode = rs.getString(OrderDBInfo.OrderInfo.FIELD_NAME_NCODE);
                orderSet.mOrder.mTotalAmount = rs.getInt(OrderDBInfo.OrderInfo.FIELD_NAME_TOTAL_AMOUNT);
                orderSet.mOrder.mOrderDatetime = rs.getDate(OrderDBInfo.OrderInfo.FIELD_NAME_ORDER_DATETIME);
                orderSet.mOrder.mStatus = rs.getString(OrderDBInfo.OrderInfo.FIELD_NAME_STATUS);
                orderSet.mOrder.mPosOrderId = rs.getString(OrderDBInfo.OrderInfo.FIELD_NAME_POS_ORDER_ID);
                orderSet.mOrder.mMemo = rs.getString(OrderDBInfo.OrderInfo.FIELD_NAME_MEMO);

                // new an Order Detail List
                orderSet.mOrderDetail = new ArrayList<OrderDetail>();
                PreparedStatement stmtOrderDetail = conn.prepareStatement(OrderDBInfo.OrderDetailInfo.SQL_ORDER_DETAIL_SELECT);
                stmtOrderDetail.setLong(1, orderId);
                ResultSet rsOrderDetail = stmtOrderDetail.executeQuery();
                while(rsOrderDetail.next()) {
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.mOrderId = rsOrderDetail.getLong(OrderDBInfo.OrderDetailInfo.FIELD_NAME_ORDER_ID);
                    orderDetail.mProductId = rsOrderDetail.getInt(OrderDBInfo.OrderDetailInfo.FIELD_NAME_PRODUCT_ID);
                    orderDetail.mPrice = rsOrderDetail.getInt(OrderDBInfo.OrderDetailInfo.FIELD_NAME_PRICE);
                    orderDetail.mAmount = rsOrderDetail.getInt(OrderDBInfo.OrderDetailInfo.FIELD_NAME_AMOUNT);
                    orderDetail.mTotalAmount = rsOrderDetail.getInt(OrderDBInfo.OrderDetailInfo.FIELD_NAME_TOTAL_AMOUNT);
                    orderSet.mOrderDetail.add(orderDetail);
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
        /*
        Response resp;
        
        OrderSet orderSet = new OrderSet();
        orderSet.mOrder = new Order();
        orderSet.mOrder.mOrderId        = (long)1;
        orderSet.mOrder.mCustomerId     = 3;
        orderSet.mOrder.mCompanyId      = 4;
        orderSet.mOrder.mStoreId        = 5;
        orderSet.mOrder.mPosId          = 5;
        orderSet.mOrder.mEmployeeId     = 6;
        orderSet.mOrder.mNcode          = "901";
        orderSet.mOrder.mTotalAmount    = 7;
        orderSet.mOrder.mOrderDatetime  = Calendar.getInstance().getTime();
        orderSet.mOrder.mStatus         = 'A';
        orderSet.mOrder.mPosOrderId     = "20141029105613001";
        orderSet.mOrder.mMemo           = "Memo";
        
        orderSet.mOrderDetail = new ArrayList<OrderDetail>();
        for (int i = 0; i < 2; i++) {
            OrderDetail od = new OrderDetail();
            od.mOrderId = orderSet.mOrder.mOrderId;
            od.mProductId = i*100 + 11;
            od.mPrice = i*100 + 12;
            od.mAmount = i*100 + 13;
            od.mTotalAmount = i*100 + 14;
            orderSet.mOrderDetail.add(od);
        }
        //resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        
        return Response.status(Response.Status.OK).entity(orderSet.toJson()).build(); */
    }
    
    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createOrder(String jsonOrderSet) {

        OrderSet orderSet;

        Connection conn = null;
        Response resp;
        boolean ret = true;
        try {
            orderSet = OrderSet.fromJson(jsonOrderSet);

            conn = DSConn.getConnection(wi.rc.server.Properties.DS_RC);
            conn.setAutoCommit(false);
            
            PreparedStatement stmtOrder = conn.prepareStatement(OrderDBInfo.OrderInfo.SQL_ORDER_INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
            stmtOrder.setInt(1, orderSet.mOrder.mCustomerId);
            stmtOrder.setInt(2, orderSet.mOrder.mCompanyId);
            stmtOrder.setInt(3, orderSet.mOrder.mStoreId);
            stmtOrder.setInt(4, orderSet.mOrder.mPosId);
            stmtOrder.setInt(5, orderSet.mOrder.mEmployeeId);
            if (orderSet.mOrder.mNcode == null)
                stmtOrder.setNull(6, Types.VARCHAR);
            else 
                stmtOrder.setString(6, orderSet.mOrder.mNcode);
            stmtOrder.setInt(7, orderSet.mOrder.mTotalAmount);
            if (orderSet.mOrder.mOrderDatetime == null)
                stmtOrder.setNull(8, Types.DATE);
            else 
                stmtOrder.setDate(8, orderSet.mOrder.mOrderDatetime);
            if (orderSet.mOrder.mStatus == null)
                stmtOrder.setNull(9, Types.VARCHAR);
            else 
                stmtOrder.setString(9, orderSet.mOrder.mStatus);
            if (orderSet.mOrder.mPosOrderId == null)
                stmtOrder.setNull(10, Types.VARCHAR);
            else 
                stmtOrder.setString(10, orderSet.mOrder.mPosOrderId);
            if (orderSet.mOrder.mMemo == null)
                stmtOrder.setNull(11, Types.VARCHAR);
            else 
                stmtOrder.setString(11, orderSet.mOrder.mMemo);
            
            if (stmtOrder.executeUpdate() > 0) {
                // execute success
                ResultSet rs = stmtOrder.getGeneratedKeys();
                if (rs.next())
                    orderSet.mOrder.mOrderId = rs.getLong(1);
                
                PreparedStatement stmtOrderDetail = conn.prepareStatement(OrderDBInfo.OrderDetailInfo.SQL_ORDER_DETAIL_INSERT);
                for (OrderDetail orderDetail: orderSet.mOrderDetail) {
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

    @POST
    @Path("/create2")
//    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createOrder2(String content) {
        OrderSet orderSet = OrderSet.fromJson(content);
        
        Response r = Response.status(Response.Status.OK).entity(orderSet.toJson(OrderSet._VERSION)).build();

        return r;
    }
//    @PUT
//    @Path("/{orderId}")
//    @Consumes("application/json")
//    public String updateOrder(@PathParam("orderId") int orderId) {
//        // TODO update order info
//        return "Update " + orderId;
//    }
//    
//    @DELETE
//    @Path("/{orderId}")
//    @Consumes("application/json")
//    public String deleteOrder(@PathParam("orderId") int orderId) {
//        // TODO update order info
//        return "Delete " + orderId;
//    }

    private void Exception(String FORMAT_ERROR) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
