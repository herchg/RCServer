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

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

@Path("order")
@Api(value="order", description="Operations about order and order details")
public class OrderResource {

    @Context
    private UriInfo context;

    public OrderResource() {
    }

    @GET
    @Path("/company/{company_id}")
    @Produces("application/json")
    @ApiOperation(
            value = "Get orders", 
            notes = "Orders and details",
            response = Response.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Got results"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal server error"),
        @ApiResponse(code = 400, message = "Bad request")
    })
    public Response getOrders(
            @ApiParam(value = "company id", required = true) @PathParam("company_id")int company_id,
            @ApiParam(value = "expand (ex: detail)", required = false) @QueryParam("expand") String expand,
            @ApiParam(value = "order_id", required = false) @HeaderParam("order_id") String order_id,
            @ApiParam(value = "store_id", required = false) @HeaderParam("store_id") String store_id,
            @ApiParam(value = "pos_id", required = false) @HeaderParam("pos_id") String pos_id,
            @ApiParam(value = "employee_id", required = false) @HeaderParam("employee_id") String employee_id,
            @ApiParam(value = "status (ex: 0, 1, 2)", required = false) @HeaderParam("status") String status,
            @ApiParam(value = "payment_id", required = false) @HeaderParam("payment_id") String payment_id,
            @ApiParam(value = "total_amount", required = false) @HeaderParam("total_amount") String total_amount,
            @ApiParam(value = "start_date", required = false) @HeaderParam("start_date") String start_date,
            @ApiParam(value = "end_date", required = false) @HeaderParam("end_date") String end_date) {
      
        return OrderDataOpr.selectAllOrders(company_id,order_id,store_id,pos_id,employee_id,payment_id,total_amount,status,start_date,end_date,expand);
    }

    @POST
    @Path("/")
    @Produces("application/json")
    @ApiOperation(
            value = "Create an order and details", 
            notes = "Create an order and details",
            response = Response.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Created"),
        @ApiResponse(code = 500, message = "Internal server error"),
        @ApiResponse(code = 400, message = "Bad request")
    })
    public Response createOrder(String jsonOrderSet) {
        return OrderDataOpr.insertOrder(jsonOrderSet);
    }

    @PUT
    @Path("/{order_id}/company/{company_id}")
    @Produces("application/json")
    @ApiOperation(
            value = "update an order and details",
            notes = "Update an order and details",
            response = Response.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal server error"),
        @ApiResponse(code = 400, message = "Bad request")
    })
    public Response updateOrder(
            @ApiParam(value = "company id", required = true) @PathParam("company_id")int company_id, 
            @ApiParam(value = "order id", required = true) @PathParam("order_id") int orderId, 
            String jsonOrderSet) {

        return OrderDataOpr.updateOrder(company_id,orderId, jsonOrderSet);
    }
    
    @DELETE
    @Path("/{order_id}/company/{company_id}")
    @Produces("application/json")
    @ApiOperation(
            value = "delete an order", 
            notes = "delete an order",
            response = Response.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal server error"),
        @ApiResponse(code = 400, message = "Bad request")
    })
    public Response deleteOrder(
            @ApiParam(value = "company id", required = true) @PathParam("company_id")int company_id,
            @ApiParam(value = "order id", required = true) @PathParam("order_id") long orderId) {
        
       return OrderDataOpr.deleteOrder(company_id,orderId);
    }
}
