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
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.FormParam;
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
import wi.rc.server.opr.EmployeeDataOpr;
/**
 * REST Web Service
 *
 * @author 10307905
 */
@Path("employee")
@Api(value = "employee", description = "Operations about employee")
public class EmployeeResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of EmployeeResource
     */
    public EmployeeResource() {
    }

    @GET
    @Path("/company/{company_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Get employee",
            notes = "Employee",
            response = Response.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Got results"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal server error"),
        @ApiResponse(code = 400, message = "Bad request")
    })
    public Response getEmployees(
            @ApiParam(value = "company id", required = true) @PathParam("company_id")int company_id,
            @ApiParam(value = "expand", required = false) @QueryParam("expand") String expand,
            @ApiParam(value = "employee id", required = false) @HeaderParam("employee_id") String employee_id,
            @ApiParam(value = "role id", required = false) @HeaderParam("role_id") String role_id,
            @ApiParam(value = "store id", required = false) @HeaderParam("store_id") String store_id,
            @ApiParam(value = "employee name", required = false) @HeaderParam("employee_name") String employee_name,
            @ApiParam(value = "status", required = false) @HeaderParam("status") String status
            ) {
      
        return EmployeeDataOpr.selectAllEmployee(company_id,employee_id,role_id,store_id,employee_name,status,expand);
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
    @ApiOperation(
            value = "Create a employee",
            notes = "Create a employee",
            response = Response.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Created"),
        @ApiResponse(code = 500, message = "Internal server error"),
        @ApiResponse(code = 400, message = "Bad request")
    })
    public Response loginEmployee(String jsonEmployeeSet) {
        
        return EmployeeDataOpr.loginEmployee(jsonEmployeeSet);
    }
    /*
    @POST
    @Path("/upload")
    @Consumes(MediaType.TEXT_HTML)
    public Response test(@FormParam("image")String image) {
        
        String res = image;
        
        return Response.ok(res).build();
    }
    */
    @PUT
    @Path("/{employee_id}/company/{company_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Update a employee",
            notes = "Update a employee",
            response = Response.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal server error"),
        @ApiResponse(code = 400, message = "Bad request")
    })
    public Response updateEmployee(
            @ApiParam(value = "company id", required = true) @PathParam("company_id")int company_id, 
            @ApiParam(value = "employee id", required = true) @PathParam("employee_id")int employee_id, 
            String jsonEmployeeSet) {
         
        return EmployeeDataOpr.updateEmployee(company_id, employee_id, jsonEmployeeSet);
    } 
    
    @DELETE
    @Path("/{employee_id}/company/{company_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "delete a employee",
            notes = "delete a employee",
            response = Response.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal server error"),
        @ApiResponse(code = 400, message = "Bad request")
    })
    public Response deleteEmployee(
            @ApiParam(value = "company id", required = true) @PathParam("company_id")int company_id, 
            @ApiParam(value = "employee id", required = true) @PathParam("employee_id")int employee_id) {
        
        return EmployeeDataOpr.deleteEmployee(company_id, employee_id);
    }
}
