/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wi.server.rc.api;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import wi.rc.data.order.Order;

@Path("orders")
public class OrdersResource {

    @Context
    private UriInfo context;

    public OrdersResource() {
    }

    @GET
    @Path("/")
    @Produces("application/json")
    public String getOrders(@QueryParam("detail") int detail) {
        // TODO get order info
        return "Get all orders";
    }
    
    @GET
    @Path("/{orderId}")
    @Produces("application/json")
    public String getOrder(@PathParam("orderId") int orderId
                         , @QueryParam("detail") int detail) {
        // TODO get order info
        return "Get " + orderId + " detail " + detail;
    }
    
    @POST
    @Path("/{orderId}")
    @Consumes("application/json")
    public String createOrder(@PathParam("orderId") int orderId) {
        // TODO update order info
        return "Create " + orderId;
    }
    
    @PUT
    @Path("/{orderId}")
    @Consumes("application/json")
    public String updateOrder(@PathParam("orderId") int orderId) {
        // TODO update order info
        return "Update " + orderId;
    }
    
    @DELETE
    @Path("/{orderId}")
    @Consumes("application/json")
    public String deleteOrder(@PathParam("orderId") int orderId) {
        // TODO update order info
        return "Delete " + orderId;
    }
}
