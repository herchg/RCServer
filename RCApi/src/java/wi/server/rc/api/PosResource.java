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
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPoss(@QueryParam("ext") int ext) {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
    
    @GET
    @Path("/{pos_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPos(@PathParam("pos_id")int pos_id, @QueryParam("ext") int ext) {
        
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
