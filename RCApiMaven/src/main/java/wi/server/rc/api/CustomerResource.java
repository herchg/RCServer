/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wi.server.rc.api;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import wi.rc.server.opr.CustomerDataOpr;

/**
 * REST Web Service
 *
 * @author 10307905
 */
@Path("customer")
@Api(value = "customer", description = "Operations about customer")
public class CustomerResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of CustomerResource
     */
    public CustomerResource() {
    }

    @GET
    @Path("/company/{company_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Get customers",
            notes = "Customers",
            response = Response.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Got results"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal server error"),
        @ApiResponse(code = 400, message = "Bad request")
    })
    public Response getCustomers(
            @ApiParam(value = "company id", required = true) @PathParam("company_id")int company_id,
            @ApiParam(value = "expand", required = false) @QueryParam("expand") String expand,
            @ApiParam(value = "customer_id", required = false) @HeaderParam("customer_id") String customer_id,
            @ApiParam(value = "store_id", required = false) @HeaderParam("store_id") String store_id,
            @ApiParam(value = "customer_name", required = false) @HeaderParam("customer_name") String customer_name
            ) {
      
        return CustomerDataOpr.selectAllCustomer(company_id, customer_id, store_id, customer_name, expand);
    }
  
    
    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Create a customer",
            notes = "Create a customer",
            response = Response.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Created"),
        @ApiResponse(code = 500, message = "Internal server error"),
        @ApiResponse(code = 400, message = "Bad request")
    })
    public Response createCustomer(String jsonCustomerSet) {
        
        return CustomerDataOpr.insertCustomer(jsonCustomerSet);
    }
    
    @PUT
    @Path("/{customer_id}/company/{company_id}")
    @Produces(MediaType.APPLICATION_JSON)
     @ApiOperation(
            value = "Update a customer",
            notes = "Update a customer",
            response = Response.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal server error"),
        @ApiResponse(code = 400, message = "Bad request")
    })
    public Response updateCustomer(
            @ApiParam(value = "company id", required = true) @PathParam("company_id") int company_id,
            @ApiParam(value = "customer_id", required = false) @HeaderParam("customer_id") int customer_id, 
            String jsonCustomerSet) {
        
        return CustomerDataOpr.updateCustomer(company_id, customer_id, jsonCustomerSet);
    }
    
    @DELETE
    @Path("/{customer_id}/company/{company_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "delete a customer",
            notes = "delete a customer",
            response = Response.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal server error"),
        @ApiResponse(code = 400, message = "Bad request")
    })
    public Response deleteCustomer(
            @ApiParam(value = "company id", required = true) @PathParam("company_id") int company_id,
            @ApiParam(value = "customer_id", required = false) @HeaderParam("customer_id") int customer_id) {
        
        return CustomerDataOpr.deleteCustomer(company_id,customer_id);
    }
}
