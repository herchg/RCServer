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
import javax.ws.rs.core.Response;

import wi.rc.server.opr.CategoryDataOpr;

/**
 *
 * @author samuelatwistron
 */
@Path("category")
@Api(value = "category", description = "Operations about category")
public class CategoryResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of CategoryResource
     */
    public CategoryResource() {
    }

    @GET
    @Path("/company/{company_id}")
    @Produces("application/json")
    @ApiOperation(
            value = "Get categories",
            notes = "Categories",
            response = Response.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Got results"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal server error"),
        @ApiResponse(code = 400, message = "Bad request")
    })
    public Response getAllCategories(
            @ApiParam(value = "company id", required = true) @PathParam("company_id") int company_id,
            @ApiParam(value = "category id", required = false) @HeaderParam("category_id") String category_id,
            @ApiParam(value = "category name", required = false) @HeaderParam("category_name") String category_name,
            @ApiParam(value = "status", required = false) @HeaderParam("status") String status) {
        return CategoryDataOpr.selectAllCategory(company_id, category_id, category_name, status);
    }

    @POST
    @Path("/")
    @Produces("application/json")
    @ApiOperation(
            value = "Create a category",
            notes = "Create a category",
            response = Response.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Created"),
        @ApiResponse(code = 500, message = "Internal server error"),
        @ApiResponse(code = 400, message = "Bad request")
    })
    public Response createCategory(String json) {
        return CategoryDataOpr.insertCategory(json);
    }

    @PUT
    @Path("/{category_id}/company/{company_id}")
    @Produces("application/json")
    @ApiOperation(
            value = "Update a category",
            notes = "Update a category",
            response = Response.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal server error"),
        @ApiResponse(code = 400, message = "Bad request")
    })
    public Response updateCategory(
            @ApiParam(value = "company id", required = true) @PathParam("company_id") int company_id,
            @ApiParam(value = "category id", required = true) @PathParam("category_id") int category_id,
            String json) {
        return CategoryDataOpr.updateCategory(company_id, category_id, json);
    }

    @DELETE
    @Path("/{category_id}/company/{company_id}")
    @Produces("application/json")
    @ApiOperation(
            value = "delete a category",
            notes = "delete a category",
            response = Response.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal server error"),
        @ApiResponse(code = 400, message = "Bad request")
    })
    public Response deleteCategory(
            @ApiParam(value = "company id", required = true) @PathParam("company_id") int company_id,
            @ApiParam(value = "category id", required = true) @PathParam("category_id") int category_id) {
        return CategoryDataOpr.deleteCategory(company_id, category_id);
    }
}
