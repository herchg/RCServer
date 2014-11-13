/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wi.server.rc.api;


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
//@Consumes(MediaType.MULTIPART_FORM_DATA)
public class ImageResource {
    //    @Context
    private UriInfo context;

    @GET
    @Path("/company/{company_id}/employee/{employee_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getImageFile(@PathParam("company_id")int company_id,@PathParam("employee_id")int employee_id) {

        return ImageDataOpr.getImageFile(company_id,employee_id);
    }
    
    @POST
    @Path("/")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadImageFile(@Context HttpServletRequest request) {

        return ImageDataOpr.uploadImage(request);
    }

    
}

