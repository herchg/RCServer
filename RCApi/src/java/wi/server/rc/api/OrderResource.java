package wi.server.rc.api;

import javax.ws.rs.DELETE;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
    @Path("/company/{company_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrders(@PathParam("company_id")int company_id,
            @QueryParam("expand") String expand,
            @HeaderParam("order_id") String order_id,
            @HeaderParam("store_id") String store_id,
            @HeaderParam("pos_id") String pos_id,
            @HeaderParam("status") String status,
            @HeaderParam("start_date") String start_date,
            @HeaderParam("end_date") String end_date) {
      
        return OrderDataOpr.selectAllOrders(company_id,order_id,store_id,pos_id,status,start_date,end_date,expand);
    }
    
    @GET
    @Path("/company/{company_id}/order/{orderId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrderById(@PathParam("company_id")int company_id,@PathParam("orderId")long orderId, @QueryParam("expand") String expand) {
        return OrderDataOpr.selectOrderByID(company_id,orderId, expand);
    }

    @GET
    @Path("/company/{company_id}/pos/{posId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrderByPosId(@PathParam("company_id")int company_id,@PathParam("posId")int posId, @QueryParam("expand") String expand) {
        return OrderDataOpr.selectOrdersByPosId(company_id,posId, expand);
    }
    
    @GET
    @Path("/company/{company_id}/status/{status_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrderByStatus(@PathParam("company_id")int company_id,@PathParam("status_id")long status_id, @QueryParam("expand") String expand) {
        return OrderDataOpr.selectOrderByStatus(company_id,status_id, expand);
    }
    
    @GET
    @Path("/company/{company_id}/start_date/{start_date}/end_date/{end_date}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrderByOrderDate(@PathParam("company_id")int company_id,@PathParam("start_date")String start_date,@PathParam("end_date")String end_date, @QueryParam("expand") String expand) {
        return OrderDataOpr.selectOrderByDate(company_id,start_date,end_date, expand);
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

        return OrderDataOpr.updateOrder(orderId, jsonOrderSet);
    }
    
    @DELETE
    @Path("/company/{company_id}/order/{orderId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteOrder(@PathParam("company_id")int company_id,@PathParam("orderId") long orderId) {
        
       return OrderDataOpr.deleteOrder(company_id,orderId);
    }

    private void Exception(String FORMAT_ERROR) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
