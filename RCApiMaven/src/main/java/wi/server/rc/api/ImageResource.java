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
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import wi.rc.server.opr.ImageDataOpr;

        
@Path("image")
@MultipartConfig
@Api(value = "image", description = "Operations about image")
//@Consumes(MediaType.MULTIPART_FORM_DATA)
public class ImageResource {
    //    @Context
    private UriInfo context;

    @GET
    @Path("/company/{company_id}/employee/{employee_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Get image",
            notes = "Image",
            response = Response.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Got results"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal server error"),
        @ApiResponse(code = 400, message = "Bad request")
    })
    public Response getImageFile(
            @ApiParam(value = "company id", required = true) @PathParam("company_id")int company_id,
            @ApiParam(value = "employee id", required = true) @PathParam("employee_id")int employee_id) {

        return ImageDataOpr.getImageFile(company_id,employee_id);
    }
    
    @POST
    @Path("/")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
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
    public Response uploadImageFile(
            @ApiParam(value = "request", required = true) @Context HttpServletRequest request) {

        return ImageDataOpr.uploadImage(request);
    }

    
}

