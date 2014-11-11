package wi.server.rc.api;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.Response;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import wi.rc.server.opr.PosDataOpr;
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
    @Path("/company/{company_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPos(@PathParam("company_id")int company_id,
            @HeaderParam("pos_id") String pos_id,
            @HeaderParam("store_id") String store_id
            ) {
      
        return PosDataOpr.selectAllPos(company_id,pos_id,store_id,null);
    }
    
    @GET
    @Path("/company/{company_id}/name/{pos_name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPosByName(@PathParam("company_id")int company_id,@PathParam("pos_name")String pos_name) {
      
        return PosDataOpr.selectAllPos(company_id,null,null,pos_name);
    }

    
    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPos(String jsonPosSet) {
        
        return PosDataOpr.insertPos(jsonPosSet);
    }
    
    @PUT
    @Path("/{pos_id}/company/{company_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePos(@PathParam("company_id")int company_id, @PathParam("pos_id")int pos_id, String jsonPosSet) {
        
        return PosDataOpr.updatePos(company_id, pos_id, jsonPosSet);
    }
    
    @DELETE
    @Path("/{pos_id}/company/{company_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePos(@PathParam("company_id")int company_id,@PathParam("pos_id")int pos_id) {
        
        return PosDataOpr.deletePos(company_id, pos_id);
    }
}
