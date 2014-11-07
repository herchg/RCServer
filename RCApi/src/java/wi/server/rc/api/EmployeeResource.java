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
import wi.rc.server.opr.EmployeeDataOpr;
/**
 * REST Web Service
 *
 * @author 10307905
 */
@Path("employee")
public class EmployeeResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of EmployeeResource
     */
    public EmployeeResource() {
    }

    /**
     * Retrieves representation of an instance of wi.server.rc.api.EmployeeResource
     * @return an instance of java.lang.String
     */
    @GET
    @Path("/company/{company_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployees(@PathParam("company_id") int company_id) {
        
        return EmployeeDataOpr.selectAllEmployee(company_id);
    }
    
    @GET
    @Path("/company/{company_id}/employee/{employee_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployeeById(@PathParam("company_id") int company_id,@PathParam("employee_id") int employee_id,@QueryParam("expand") String expand) {
        
        return EmployeeDataOpr.selectEmployeeById(company_id,employee_id,expand);
    }   
    
    @GET
    @Path("/company/{company_id}/store/{store_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployeeByStoreId(@PathParam("company_id") int company_id, @PathParam("store_id") int store_id) {
        
        return EmployeeDataOpr.selectEmployeeByStoreId(company_id, store_id);
    }  
    
    @GET
    @Path("/company/{company_id}/name/{employee_name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployeeByName(@PathParam("company_id") int company_id, @PathParam("employee_name") String employee_name) {
        
        return EmployeeDataOpr.selectEmployeeByName(company_id, employee_name);
    }  
    
    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createEmployee(String jsonEmployeeSet) {
        
        return EmployeeDataOpr.insertEmployee(jsonEmployeeSet);
    }
    
    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginEmployee(String jsonEmployeeSet) {
        
        return EmployeeDataOpr.loginEmployee(jsonEmployeeSet);
    }
     
    @PUT
    @Path("/{employee_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateEmployee(@PathParam("employee_id")int employee_id, String jsonEmployeeSet) {
         
        return EmployeeDataOpr.updateEmployee(employee_id, jsonEmployeeSet);
    }
    
    @DELETE
    @Path("/company/{company_id}/employee/{employee_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteEmployee(@PathParam("company_id")int company_id, @PathParam("employee_id")int employee_id) {
        
        return EmployeeDataOpr.deleteEmployee(company_id, employee_id);
    }
}
