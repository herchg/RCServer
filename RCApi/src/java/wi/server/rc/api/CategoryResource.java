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
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import wi.rc.server.opr.CategoryDataOpr;
/**
 *
 * @author samuelatwistron
 */
@Path("category")
public class CategoryResource {
    
    @Context
    private UriInfo context;
    
    /**
     * Creates a new instance of CategoryResource
     */
    public CategoryResource() {
    }
    
    /**
     * Retrieves representation of an instance of wi.server.rc.api.EmployeeResource
     * @return an instance of java.lang.String
     */
    @GET
    @Path("/{company_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCategorys(@PathParam("company_id") int company_id) {
        
        return CategoryDataOpr.selectAllCategory(company_id);
    }
    
    @GET
    @Path("/{company_id}/{category_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCategoryById(@PathParam("company_id") int company_id,@PathParam("category_id") int category_id) {
        
        return null;
    }   

    @GET
    @Path("/{company_id}/name/{category_name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCategoryByName(@PathParam("company_id") int company_id,@PathParam("category_name") String category_name) {
        
        return null;
    }  
    
    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCategory(String jsonEmployeeSet) {
        
        return null;
    }

    /*
    @PUT
    @Path("/{category_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateEmployee(@PathParam("employee_id")int employee_id, String jsonEmployeeSet) {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
    */
    @DELETE
    @Path("/{company_id}/{category_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteEmployee(@PathParam("company_id")int company_id,@PathParam("category_id")int category_id) {
        
        return null;
    }
}
