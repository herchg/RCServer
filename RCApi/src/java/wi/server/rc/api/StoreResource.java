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
@Path("store")
public class StoreResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of StoreResource
     */
    public StoreResource() {
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStores(@QueryParam("price") int price, @QueryParam("ext") int ext) {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
    
    @GET
    @Path("/{store_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStore(@PathParam("store_id")int store_id, @QueryParam("price") int price, @QueryParam("ext") int ext) {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
    
    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createStore(String jsonStoreSet) {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
    
    @PUT
    @Path("/{store_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateStore(@PathParam("store_id")int store_id, String jsonStoreSet) {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
    
    @DELETE
    @Path("/{store_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteStore(@PathParam("store_id")int store_id) {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
}
