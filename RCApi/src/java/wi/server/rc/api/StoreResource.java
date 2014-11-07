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
import wi.rc.server.opr.StoreDataOpr;
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
    @Path("/company/{company_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStores(@PathParam("company_id")int company_id) {
        
        return StoreDataOpr.selectAllStore(company_id);
    }
    
    @GET
    @Path("/company/{company_id}/store/{store_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStoresById(@PathParam("company_id")int company_id,@PathParam("store_id")int store_id) {
        
        return StoreDataOpr.selectStoreById(company_id,store_id);
    }
    
    @GET
    @Path("/company/{company_id}/name/{store_name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStoresByName(@PathParam("company_id")int company_id,@PathParam("store_name")String store_name) {
        
        return StoreDataOpr.selectStoreByName(company_id,store_name);
    }
    
    @GET
    @Path("/company/{company_id}/employee/{employee_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStoresByEmployee(@PathParam("company_id")int company_id,@PathParam("employee_id")int employee_id) {
        
        return StoreDataOpr.selectStoreByEmployee(company_id,employee_id);
    }
    
    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createStore(String jsonStoreSet) {
        
        return StoreDataOpr.insertStore(jsonStoreSet);
    }
    
    @PUT
    @Path("/company/{company_id}/store/{store_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateStore(@PathParam("company_id")int company_id, @PathParam("store_id")int store_id, String jsonStoreSet) {
        
        return StoreDataOpr.updateStore(company_id, store_id, jsonStoreSet);
    }
    
    @DELETE
    @Path("/company/{company_id}/store/{store_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteStore(@PathParam("company_id")int company_id,@PathParam("store_id")int store_id) {
        
        return StoreDataOpr.deleteStore(company_id,store_id);
    }
}
