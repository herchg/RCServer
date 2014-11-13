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
import java.net.URLDecoder;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URLEncoder;
import wi.rc.server.opr.RoleDataOpr;


@Path("role")
@Api(value = "role", description = "Operations about role")
public class RoleResource {

//    @Context
    private UriInfo context;

    public RoleResource() {
    }
    
    @GET
    @Path("/company/{company_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Get role",
            notes = "Role",
            response = Response.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Got results"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal server error"),
        @ApiResponse(code = 400, message = "Bad request")
    })
    public Response getRoles(
            @ApiParam(value = "company id", required = true) @PathParam("company_id")int company_id,
            @ApiParam(value = "role id", required = false) @HeaderParam("role_id") String role_id,
            @ApiParam(value = "role name", required = false) @HeaderParam("role_name") String role_name,
            @ApiParam(value = "status", required = false) @HeaderParam("status") String status
            ) {

        return RoleDataOpr.selectAllRole(company_id,role_id,role_name,status);
    }
    
    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Create a role",
            notes = "Create a role",
            response = Response.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Created"),
        @ApiResponse(code = 500, message = "Internal server error"),
        @ApiResponse(code = 400, message = "Bad request")
    })
    public Response createRole(String jsonProductSet) {
        
        return RoleDataOpr.insertRole(jsonProductSet);
    }
    
    
    @PUT
    @Path("/{role_id}/company/{company_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Update a role",
            notes = "Update a role",
            response = Response.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal server error"),
        @ApiResponse(code = 400, message = "Bad request")
    })
    public Response updateRole(
            @ApiParam(value = "company id", required = true) @PathParam("company_id")int company_id,
            @ApiParam(value = "role id", required = false) @HeaderParam("role_id") int role_id,
            String jsonProductSet) {
        
        return RoleDataOpr.updateRole(company_id,role_id,jsonProductSet);
    }
    
    
    @DELETE
    @Path("/{role_id}/company/{company_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "delete a role",
            notes = "delete a role",
            response = Response.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal server error"),
        @ApiResponse(code = 400, message = "Bad request")
    })
    public Response deleteRole(
    @ApiParam(value = "company id", required = true) @PathParam("company_id")int company_id,
            @ApiParam(value = "role id", required = false) @HeaderParam("role_id") int role_id) {
        
        return RoleDataOpr.deleteRole(company_id, role_id);
    }
}
