/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wi.server.rc.api;

import java.util.ArrayList;
import java.util.Calendar;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import wi.rc.data.order.Order;
import wi.rc.data.order.OrderDetail;
import wi.rc.data.order.OrderResult;

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
    @Produces("application/json")
    public String getOrder(@PathParam("orderId")int orderId) {
        // TODO get order info
//        try {
//            
//        } catch (Exception ex) {
//            
//        }
        OrderResult orderResult = new OrderResult();
        orderResult.mOrder = new Order();
        orderResult.mOrder.mOrderId     = (long)1;
        orderResult.mOrder.mCustomerId  = 3;
        orderResult.mOrder.mCompanyId   = 4;
        orderResult.mOrder.mStoreId     = 5;
        orderResult.mOrder.mEmployeeId  = 6;
        orderResult.mOrder.mNcode       = "901";
        orderResult.mOrder.mTotalAmount = 7;
        orderResult.mOrder.mOrderDatetime = Calendar.getInstance().getTime();
        orderResult.mOrder.mLogDatetime = Calendar.getInstance().getTime();
        orderResult.mOrder.mStatus      = 'A';
        orderResult.mOrder.mPosOrderId  = 99;
        
        orderResult.mOrderDetail = new ArrayList<OrderDetail>();
        for (int i = 0; i < 10; i++) {
            OrderDetail od = new OrderDetail();
            od.mOrderId = orderResult.mOrder.mOrderId;
            od.mProductId = i*100 + 11;
            od.mPrice = i*100 + 12;
            od.mAmount = i*100 + 13;
            od.mTotalAmount = i*100 + 14;
            orderResult.mOrderDetail.add(od);
        }
        return orderResult.toJson();
    }
    
    @POST
    @Path("/post")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String createOrder(OrderResult orderResult) {
        
        // TODO
        // 

        return String.format("{ %s}", orderResult.toJson());
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
}
