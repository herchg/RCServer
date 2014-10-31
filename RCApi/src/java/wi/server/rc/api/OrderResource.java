package wi.server.rc.api;

import com.google.gson.JsonSyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import wi.core.db.DSConn;
import wi.rc.data.order.Order;
import wi.rc.data.order.OrderDetail;
import wi.rc.data.order.OrderSet;
import wi.rc.server.opr.OrderDataOpr;

@Path("order")
public class OrderResource {

    @Context
    private UriInfo context;

    public OrderResource() {
    }

    /**
     * Get all orders 
     */
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrders(@QueryParam("expand") String expand) {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
    
    @GET
    @Path("/{orderId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrder(@PathParam("orderId")int orderId, @QueryParam("expand") String expand) {
        
        if(expand == null){
            expand = "";
        }
        return OrderDataOpr.selectOrder(orderId, expand);
    }
    
    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createOrder(String jsonOrderSet) {

        return OrderDataOpr.insertOrder(jsonOrderSet);
    }

    @PUT
    @Path("/{orderId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateOrder(@PathParam("orderId") int orderId, String jsonOrderSet) {
       
       Response resp;
       resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
       return resp; 
    }
    
    @DELETE
    @Path("/{orderId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteOrder(@PathParam("orderId") int orderId) {
        
       return OrderDataOpr.deleteOrder(orderId);
    }

    private void Exception(String FORMAT_ERROR) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
