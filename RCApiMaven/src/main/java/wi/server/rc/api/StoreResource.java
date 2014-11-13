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
import wi.rc.server.opr.StoreDataOpr;
/**
 * REST Web Service
 *
 * @author 10307905
 */
@Path("store")
@Api(value = "store", description = "Operations about store")
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
    @ApiOperation(
            value = "Get store",
            notes = "Store",
            response = Response.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Got results"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal server error"),
        @ApiResponse(code = 400, message = "Bad request")
    })
    public Response getStores(
            @ApiParam(value = "company id", required = true) @PathParam("company_id")int company_id,
            @ApiParam(value = "store id", required = false) @HeaderParam("store_id") String store_id,
            @ApiParam(value = "employee id", required = false) @HeaderParam("employee_id") String employee_id,
            @ApiParam(value = "store name", required = false) @HeaderParam("store_name") String store_name
            ) {
      
        return StoreDataOpr.selectAllStore(company_id,store_id,store_name,employee_id,null);
    }
    
    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Create a store",
            notes = "Create a store",
            response = Response.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Created"),
        @ApiResponse(code = 500, message = "Internal server error"),
        @ApiResponse(code = 400, message = "Bad request")
    })
    public Response createStore(String jsonStoreSet) {
        
        return StoreDataOpr.insertStore(jsonStoreSet);
    }
    
    @PUT
    @Path("{store_id}/company/{company_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Update a store",
            notes = "Update a store",
            response = Response.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal server error"),
        @ApiResponse(code = 400, message = "Bad request")
    })
    public Response updateStore(
            @ApiParam(value = "company id", required = true) @PathParam("company_id")int company_id,
            @ApiParam(value = "store id", required = true) @HeaderParam("store_id") int store_id,
            String jsonStoreSet) {
        
        return StoreDataOpr.updateStore(company_id, store_id, jsonStoreSet);
    }
    
    @DELETE
    @Path("{store_id}/company/{company_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "delete a store",
            notes = "delete a store",
            response = Response.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal server error"),
        @ApiResponse(code = 400, message = "Bad request")
    })
    public Response deleteStore(
            @ApiParam(value = "company id", required = true) @PathParam("company_id")int company_id,
            @ApiParam(value = "store id", required = true) @HeaderParam("store_id") int store_id) {
        
        return StoreDataOpr.deleteStore(company_id, store_id);
    }
}
