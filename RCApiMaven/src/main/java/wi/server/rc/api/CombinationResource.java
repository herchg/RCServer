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

/**
 * REST Web Service
 *
 * @author 10307905
 */
@Path("combination")
@Api(value = "combination", description = "Operations about combination")
public class CombinationResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of CombinationResource
     */
    public CombinationResource() {
    }

    @GET
    @Path("/company/{company_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Get all combinations",
            notes = "Combination",
            response = Response.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Got results"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal server error"),
        @ApiResponse(code = 400, message = "Bad request")
    })
    
    public Response getAllCombinations(
            @ApiParam(value = "company id", required = true) @PathParam("company_id") int company_id,
            @ApiParam(value = "price", required = false) @QueryParam("price") int price, 
            @ApiParam(value = "detail", required = false) @QueryParam("detail") int detail) {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
    
    @GET
    @Path("/{combination_id}/company/{company_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Get combination",
            notes = "Combination",
            response = Response.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Got results"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal server error"),
        @ApiResponse(code = 400, message = "Bad request")
    })
    
    public Response getCombination(
            @ApiParam(value = "company id", required = true) @PathParam("company_id") int company_id,
            @ApiParam(value = "combination id", required = true) @PathParam("combination_id") int combination_id,
            @ApiParam(value = "price", required = false) @QueryParam("price") int price, 
            @ApiParam(value = "detail", required = false) @QueryParam("detail") int detail) {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
    
    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Create combination",
            notes = "Combination",
            response = Response.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Created"),
        @ApiResponse(code = 500, message = "Internal server error"),
        @ApiResponse(code = 400, message = "Bad request")
    })
    
    public Response createCombination(String jsonCombinationSet) {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
    
    @PUT
    @Path("/{combination_id}/company/{company_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Update a combination",
            notes = "Update a combination",
            response = Response.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal server error"),
        @ApiResponse(code = 400, message = "Bad request")
    })
    public Response updateCombination(
            @ApiParam(value = "company id", required = true) @PathParam("company_id") int company_id,
            @ApiParam(value = "combination id", required = true) @PathParam("combination_id") int combination_id,
            String jsonCombinationSet) {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
    
    @DELETE
    @Path("/{combination_id}/company/{company_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "delete a combination",
            notes = "delete a combination",
            response = Response.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal server error"),
        @ApiResponse(code = 400, message = "Bad request")
    })
    public Response deleteCombination(
            @ApiParam(value = "company id", required = true) @PathParam("company_id") int company_id,
            @ApiParam(value = "category id", required = true) @PathParam("category_id") int category_id) {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
}
