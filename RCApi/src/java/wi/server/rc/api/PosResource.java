package wi.server.rc.api;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.Response;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author 10307905
 */
@Path("pos")
public class PosResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of PosResource
     */
    public PosResource() {
    }
    
    @GET
    @Path("/{company_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPos(@PathParam("company_id")int company_id) {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
    
    @GET
    @Path("/{company_id}/{pos_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPosById(@PathParam("company_id")int company_id,@PathParam("pos_id")int pos_id) {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
    
    @GET
    @Path("/{company_id}/store/{store_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPosByStore(@PathParam("company_id")int company_id,@PathParam("store_id")int store_id) {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
    
    @GET
    @Path("/{company_id}/name/{store_name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPosById(@PathParam("company_id")int company_id,@PathParam("store_name")String store_name) {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
    
    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPos(String jsonPosSet) {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
    
    @PUT
    @Path("/{pos_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePos(@PathParam("pos_id")int pos_id, String jsonPosSet) {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
    
    @DELETE
    @Path("/{pos_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePos(@PathParam("pos_id")int pos_id) {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
}
