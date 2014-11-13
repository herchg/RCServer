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
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author 10307905
 */
@Path("system")
@Api(value = "system", description = "Operations about system")
public class SystemResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of SystemResource
     */
    public SystemResource() {
    }

    //
    //Authority Resource
    //
    
    @GET
    @Path("/{system_id}/authority")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Get authority",
            notes = "Authority",
            response = Response.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Got results"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal server error"),
        @ApiResponse(code = 400, message = "Bad request")
    })
    public Response getAuthorities() {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
    
    @GET
    @Path("/{system_id}/authority/{authority_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Get authority",
            notes = "Authority",
            response = Response.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Got results"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal server error"),
        @ApiResponse(code = 400, message = "Bad request")
    })
    public Response getAuthority(
            @ApiParam(value = "authority id", required = true) @PathParam("authority_id")int authority_id) {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
    
    @POST
    @Path("/{system_id}/authority")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Create a authority",
            notes = "Create a authority",
            response = Response.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Created"),
        @ApiResponse(code = 500, message = "Internal server error"),
        @ApiResponse(code = 400, message = "Bad request")
    })
    public Response createAuthority(String jsonAuthoritySet) {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
    
    @PUT
    @Path("/{system_id}/authority/{authority_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Update a authority",
            notes = "Update a authority",
            response = Response.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal server error"),
        @ApiResponse(code = 400, message = "Bad request")
    })
    public Response updateAuthority(
            @ApiParam(value = "authority id", required = true) @PathParam("authority_id")int authority_id, 
            String jsonAuthoritySet) {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
    
    @DELETE
    @Path("/{system_id}/authority/{authority_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "delete a authority",
            notes = "delete a authority",
            response = Response.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal server error"),
        @ApiResponse(code = 400, message = "Bad request")
    })
    public Response deleteAuthority(
            @ApiParam(value = "authority id", required = true) @PathParam("authority_id")int authority_id) {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
    
    @GET
    @Path("/{system_id}/event")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Get event",
            notes = "Event",
            response = Response.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Got results"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal server error"),
        @ApiResponse(code = 400, message = "Bad request")
    })
    public Response getEvents() {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
    
    @GET
    @Path("/{system_id}/event/{event_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Get event",
            notes = "Event",
            response = Response.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Got results"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal server error"),
        @ApiResponse(code = 400, message = "Bad request")
    })
    public Response getEvent(
            @ApiParam(value = "event id", required = true) @PathParam("event_id")int event_id) {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
    
    @POST
    @Path("/{system_id}/event")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Create a event",
            notes = "Create a event",
            response = Response.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Created"),
        @ApiResponse(code = 500, message = "Internal server error"),
        @ApiResponse(code = 400, message = "Bad request")
    })
    public Response createEvent(String jsonEventSet) {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
    
    @PUT
    @Path("/{system_id}/event/{event_id}")
    @Produces(MediaType.APPLICATION_JSON)
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
    public Response updatEevent(
            @ApiParam(value = "event id", required = true) @PathParam("event_id")int event_id,
            String jsonEventSet) {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
    
    @DELETE
    @Path("/{system_id}/event/{event_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "delete a event",
            notes = "delete a event",
            response = Response.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal server error"),
        @ApiResponse(code = 400, message = "Bad request")
    })
    public Response deleteEvent(
             @ApiParam(value = "event id", required = true) @PathParam("event_id")int event_id) {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
    
    //
    //EventLog Resource
    //
    
    @GET
    @Path("/{system_id}/eventLog")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Get eventLog",
            notes = "Event Log",
            response = Response.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Got results"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal server error"),
        @ApiResponse(code = 400, message = "Bad request")
    })
    public Response getEventLogs() {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
    
    @GET
    @Path("/{system_id}/eventLog/{event_log_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Get eventLog",
            notes = "Event Log",
            response = Response.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Got results"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal server error"),
        @ApiResponse(code = 400, message = "Bad request")
    })
    public Response getEventLog(
            @ApiParam(value = "event log id", required = true) @PathParam("event_log_id")int event_log_id) {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
    
    @POST
    @Path("/{system_id}/eventLog")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Create a eventLog",
            notes = "Create a eventLog",
            response = Response.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Created"),
        @ApiResponse(code = 500, message = "Internal server error"),
        @ApiResponse(code = 400, message = "Bad request")
    })
    public Response createEventLog(String jsonEventLogSet) {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
    
    @PUT
    @Path("/{system_id}/eventLog/{event_log_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Update a eventLog",
            notes = "Update a eventLog",
            response = Response.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal server error"),
        @ApiResponse(code = 400, message = "Bad request")
    })
    public Response updateEventLog(
            @ApiParam(value = "event log id", required = true) @PathParam("event_log_id")int event_log_id, 
            String jsonEventLogSet) {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
    
    @DELETE
    @Path("/{system_id}/eventLog/{event_log_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "delete a eventLog",
            notes = "delete a eventLog",
            response = Response.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal server error"),
        @ApiResponse(code = 400, message = "Bad request")
    })
    public Response deleteEventLog(
            @ApiParam(value = "event log id", required = true) @PathParam("event_log_id")int event_log_id) {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
    
    //
    //ExtType Resource
    //
    
    @GET
    @Path("/{system_id}/extType")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Get extType",
            notes = "ExtType",
            response = Response.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Got results"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal server error"),
        @ApiResponse(code = 400, message = "Bad request")
    })
    public Response getExtTypes() {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
    
    @GET
    @Path("/{system_id}/extType/{ext_type_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Get extType",
            notes = "ExtType",
            response = Response.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Got results"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal server error"),
        @ApiResponse(code = 400, message = "Bad request")
    })
    public Response getExtType(
            @ApiParam(value = "ext type id", required = true) @PathParam("ext_type_id")int ext_type_id) {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
    
    @POST
    @Path("/{system_id}/extType")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Create a extType",
            notes = "Create a extType",
            response = Response.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Created"),
        @ApiResponse(code = 500, message = "Internal server error"),
        @ApiResponse(code = 400, message = "Bad request")
    })
    public Response createExtType(String jsonExtTypeSet) {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
    
    @PUT
    @Path("/{system_id}/extType/{ext_type_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Update a extType",
            notes = "Update a extType",
            response = Response.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal server error"),
        @ApiResponse(code = 400, message = "Bad request")
    })
    public Response updateExtType(
            @ApiParam(value = "ext type id", required = true) @PathParam("ext_type_id")int ext_type_id, 
            String jsonExtTypeSet) {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
    
    @DELETE
    @Path("/{system_id}/extType/{ext_type_id}")
    @Produces(MediaType.APPLICATION_JSON)
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
    public Response deleteExtType(
            @ApiParam(value = "ext type id", required = true) @PathParam("ext_type_id")int ext_type_id) {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
    
    //
    //Currency Resource
    //
    
    @GET
    @Path("/{system_id}/currency")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Get currency",
            notes = "Currency",
            response = Response.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Got results"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal server error"),
        @ApiResponse(code = 400, message = "Bad request")
    })
    public Response getCurrencys() {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
    
    @GET
    @Path("/{system_id}/currency/{currency_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Get currency",
            notes = "Currency",
            response = Response.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Got results"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal server error"),
        @ApiResponse(code = 400, message = "Bad request")
    })
    public Response getCurrency(
            @ApiParam(value = "currency id", required = true) @PathParam("currency_id")int currency_id) {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
    
    @POST
    @Path("/{system_id}/currency")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Create a currency",
            notes = "Create a currency",
            response = Response.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Created"),
        @ApiResponse(code = 500, message = "Internal server error"),
        @ApiResponse(code = 400, message = "Bad request")
    })
    public Response createCurrency(String jsonCurrencySet) {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
    
    @PUT
    @Path("/{system_id}/currency/{currency_id}")
    @Produces(MediaType.APPLICATION_JSON)
     @ApiOperation(
            value = "Update a currency",
            notes = "Update a currency",
            response = Response.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal server error"),
        @ApiResponse(code = 400, message = "Bad request")
    })
    public Response updateCurrency(
            @ApiParam(value = "currency id", required = true) @PathParam("currency_id")int currency_id, 
            String jsonCurrencySet) {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
    
    @DELETE
    @Path("/{system_id}/currency/{currency_id}")
    @Produces(MediaType.APPLICATION_JSON)
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
    public Response deleteCurrency(
            @ApiParam(value = "currency id", required = true) @PathParam("currency_id")int currency_id) {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
}
