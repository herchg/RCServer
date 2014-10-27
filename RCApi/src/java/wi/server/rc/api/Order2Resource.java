/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wi.server.rc.api;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import wi.rc.data.order.Order;

/**
 * REST Web Service
 *
 * @author 10307905
 */
@Path("order")
public class OrderResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of OrderResource
     */
    public OrderResource() {
    }

    @Path("/order/{orderId}")
    @GET
    @Produces("application/json")
    public Order getOrder(int orderId) {
        // TODO get order info
        return null;
    }

    /**
     * PUT method for updating or creating an instance of OrderResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @Path("/order/{orderId}")
    @PUT
    @Consumes("application/json")
    public void updateOrder(int orderId) {
        // TODO update order info
    }
    
    
}
