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

/**
 * REST Web Service
 *
 * @author 10307905
 */
@Path("combination")
public class CombinationResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of CombinationResource
     */
    public CombinationResource() {
    }

    	@GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCombinations(@QueryParam("price") int price, @QueryParam("detail") int detail) {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
    
    @GET
    @Path("/{combination_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCombination(@PathParam("combination_id")int combination_id, @QueryParam("price") int price, @QueryParam("detail") int detail) {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
    
    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCombination(String jsonCombinationSet) {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
    
    @PUT
    @Path("/{combination_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCombination(@PathParam("combination_id")int combination_id, String jsonCombinationSet) {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
    
    @DELETE
    @Path("/{combination_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCombination(@PathParam("combination_id")int combination_id) {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
}
