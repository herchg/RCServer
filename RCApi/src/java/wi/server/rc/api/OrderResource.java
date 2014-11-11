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
            @HeaderParam("employee_id") String employee_id,
            @HeaderParam("status") String status,
            @HeaderParam("payment_id") String payment_id,
            @HeaderParam("total_amount") String total_amount,
            @HeaderParam("start_date") String start_date,
            @HeaderParam("end_date") String end_date) {
      
        return OrderDataOpr.selectAllOrders(company_id,order_id,store_id,pos_id,employee_id,payment_id,total_amount,status,start_date,end_date,expand);
    }
    

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createOrder(String jsonOrderSet) {
        return OrderDataOpr.insertOrder(jsonOrderSet);
    }

    @PUT
    @Path("/{orderId}/company/{company_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateOrder(@PathParam("company_id") int company_id,@PathParam("orderId") int orderId, String jsonOrderSet) {

        return OrderDataOpr.updateOrder(company_id,orderId, jsonOrderSet);
    }
    
    @DELETE
    @Path("/{orderId}/company/{company_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteOrder(@PathParam("company_id")int company_id,@PathParam("orderId") long orderId) {
        
       return OrderDataOpr.deleteOrder(company_id,orderId);
    }

    private void Exception(String FORMAT_ERROR) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
