package wi.server.rc.api;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author 10307905
 */
@Path("company")
@Api(value = "company", description = "Operations about company")
public class CompanyResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of CompanyResource
     */
    public CompanyResource() {
    }

    @GET
    @Path("/")
    @Produces("application/json")
    @ApiOperation(
            value = "Get companies",
            notes = "Get companies",
            response = Response.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Got results"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal server error"),
        @ApiResponse(code = 400, message = "Bad request")
    })
    public Response getCompanies(
            @ApiParam(value = "ext", required = true) @QueryParam("ext") int ext) {
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }

    @GET
    @Path("/{company_id}")
    @Produces("application/json")
    @ApiOperation(
            value = "Get a company",
            notes = "Get a company",
            response = Response.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Got results"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal server error"),
        @ApiResponse(code = 400, message = "Bad request")
    })
    public Response getCompany(
            @ApiParam(value = "company id", required = true) @PathParam("company_id") int company_id,
            @ApiParam(value = "ext", required = true) @QueryParam("ext") int ext) {
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }

    @POST
    @Path("/")
    @Produces("application/json")
    @ApiOperation(
            value = "Create a company",
            notes = "Create a company",
            response = Response.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Created"),
        @ApiResponse(code = 500, message = "Internal server error"),
        @ApiResponse(code = 400, message = "Bad request")
    })
    public Response createCompany(String json) {
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }

    @PUT
    @Path("/{company_id}")
    @Produces("application/json")
    @ApiOperation(
            value = "Update a company",
            notes = "Update a company",
            response = Response.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal server error"),
        @ApiResponse(code = 400, message = "Bad request")
    })
    public Response updateCompany(
            @ApiParam(value = "company id", required = true) @PathParam("company_id") int company_id,
            String json) {
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }

    @DELETE
    @Path("/{company_id}")
    @Produces("application/json")
    @ApiOperation(
            value = "delete a company",
            notes = "delete a company",
            response = Response.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal server error"),
        @ApiResponse(code = 400, message = "Bad request")
    })
    public Response deleteCompany(
            @ApiParam(value = "company id", required = true) @PathParam("company_id") int company_id) {
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
}
