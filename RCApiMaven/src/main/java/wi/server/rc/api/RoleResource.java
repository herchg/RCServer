/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wi.server.rc.api;

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
public class RoleResource {

//    @Context
    private UriInfo context;

    public RoleResource() {
    }


    @GET
    @Path("/company/{company_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRoles(@PathParam("company_id")int company_id,
            @HeaderParam("role_id") String role_id,
            @HeaderParam("role_name") String role_name,
            @HeaderParam("status") String status
            ) {

        return RoleDataOpr.selectAllRole(company_id,role_id,role_name,status);
    }
    
    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createRole(String jsonProductSet) {
        
        return RoleDataOpr.insertRole(jsonProductSet);
    }
    
    
    @PUT
    @Path("/{role_id}/company/{company_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateRole(@PathParam("company_id") int company_id,@PathParam("role_id") int role_id, String jsonProductSet) {
        
        return RoleDataOpr.updateRole(company_id,role_id,jsonProductSet);
    }
    
    
    @DELETE
    @Path("/{role_id}/company/{company_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteRole(@PathParam("company_id")int company_id,@PathParam("role_id")int role_id) {
        
        return RoleDataOpr.deleteRole(company_id,role_id);
    }
}
