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
@Api(value = "pos", description = "Operations about pos")
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
    @ApiOperation(
            value = "Get pos",
            notes = "Pos",
            response = Response.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Got results"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal server error"),
        @ApiResponse(code = 400, message = "Bad request")
    })
    public Response getPos(
            @ApiParam(value = "company id", required = true) @PathParam("company_id")int company_id,
            @ApiParam(value = "pos id", required = false) @HeaderParam("pos_id") String pos_id,
            @ApiParam(value = "store id", required = false) @HeaderParam("store_id") String store_id,
            @ApiParam(value = "pos name", required = false) @HeaderParam("pos_name") String pos_name
            ) {
      
        return PosDataOpr.selectAllPos(company_id,pos_id,store_id,pos_name);
    }

    
    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Create a pos",
            notes = "Create a pos",
            response = Response.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Created"),
        @ApiResponse(code = 500, message = "Internal server error"),
        @ApiResponse(code = 400, message = "Bad request")
    })
    public Response createPos(String jsonPosSet) {
        
        return PosDataOpr.insertPos(jsonPosSet);
    }
    
    @PUT
    @Path("/{pos_id}/company/{company_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Update a pos",
            notes = "Update a pos",
            response = Response.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal server error"),
        @ApiResponse(code = 400, message = "Bad request")
    })
    public Response updatePos(
            @ApiParam(value = "company id", required = true) @PathParam("company_id")int company_id,
            @ApiParam(value = "pos id", required = true) @HeaderParam("pos_id") int pos_id,
            String jsonPosSet) {
        
        return PosDataOpr.updatePos(company_id, pos_id, jsonPosSet);
    }
    
    @DELETE
    @Path("/{pos_id}/company/{company_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "delete a pos",
            notes = "delete a pos",
            response = Response.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal server error"),
        @ApiResponse(code = 400, message = "Bad request")
    })
    public Response deletePos(
            @ApiParam(value = "company id", required = true) @PathParam("company_id")int company_id,
            @ApiParam(value = "pos id", required = true) @HeaderParam("pos_id") int pos_id) {
        
        return PosDataOpr.deletePos(company_id, pos_id);
    }
}
