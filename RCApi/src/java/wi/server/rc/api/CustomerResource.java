/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wi.server.rc.api;

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
    public Response getCustomers(@PathParam("company_id")int company_id,
            @QueryParam("expand") String expand,
            @HeaderParam("customer_id") String customer_id,
            @HeaderParam("store_id") String store_id
            ) {
      
        return CustomerDataOpr.selectAllCustomer(company_id,customer_id,store_id,null,expand);
    }
    
    @GET
    @Path("/company/{company_id}/name/{customer_name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomersByName(@PathParam("company_id")int company_id,@PathParam("customer_name") String customer_name,@QueryParam("expand") String expand) {
      
        return CustomerDataOpr.selectAllCustomer(company_id,null,null,customer_name,expand);
    }    
    
    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCustomer(String jsonCustomerSet) {
        
        return CustomerDataOpr.insertCustomer(jsonCustomerSet);
    }
    
    @PUT
    @Path("/{customer_id}/company/{company_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCustomer(@PathParam("company_id")int company_id, @PathParam("customer_id")int customer_id, String jsonCustomerSet) {
        
        return CustomerDataOpr.updateCustomer(company_id, customer_id, jsonCustomerSet);
    }
    
    @DELETE
    @Path("/{customer_id}/company/{company_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCustomer(@PathParam("company_id")int company_id,@PathParam("customer_id")int customer_id) {
        
        return CustomerDataOpr.deleteCustomer(company_id,customer_id);
    }
}
