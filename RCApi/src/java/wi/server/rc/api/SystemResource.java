/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wi.server.rc.api;

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
import wi.rc.server.opr.CategoryDataOpr;

/**
 * REST Web Service
 *
 * @author 10307905
 */
@Path("system")
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
    public Response getAuthorities() {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
    
    @GET
    @Path("/{system_id}/authority/{authority_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAuthority(@PathParam("authority_id")int authority_id) {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
    
    @POST
    @Path("/{system_id}/authority")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAuthority(String jsonAuthoritySet) {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
    
    @PUT
    @Path("/{system_id}/authority/{authority_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateAuthority(@PathParam("authority_id")int authority_id, String jsonAuthoritySet) {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
    
    @DELETE
    @Path("/{system_id}/authority/{authority_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteAuthority(@PathParam("authority_id")int authority_id) {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
    
    //
    //Category Resource
    //
    
    @GET
    @Path("/{system_id}/category")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCategories() {
        
        return null;//CategoryDataOpr.selectCategory();
    }
    
//  @GET
//  @Path("/{system_id}/category/{category_id}")
//  @Produces(MediaType.APPLICATION_JSON)
//  public Response getCategory(@PathParam("category_id")int category_id) {
//      
//      Response resp;
//      resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
//      return resp;
//  }
    
    @POST
    @Path("/{system_id}/category")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCategory(String jsonCategorySet) {
        
        return CategoryDataOpr.insertCategory(jsonCategorySet);
    }
    
    @PUT
    @Path("/{system_id}/category/{category_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCategory(@PathParam("category_id")int category_id, String jsonCategorySet) {
        
        return CategoryDataOpr.updateCategory(category_id, jsonCategorySet);
    }
    
    @DELETE
    @Path("/{system_id}/category/{category_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCategory(@PathParam("category_id")int category_id) {
        
        return null;//CategoryDataOpr.deleteCategory(category_id);
    }
    
    //
    //Event Resource
    //
    
    @GET
    @Path("/{system_id}/event")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEvents() {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
    
    @GET
    @Path("/{system_id}/event/{event_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEvent(@PathParam("event_id")int event_id) {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
    
    @POST
    @Path("/{system_id}/event")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createEvent(String jsonEventSet) {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
    
    @PUT
    @Path("/{system_id}/event/{event_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatEevent(@PathParam("event_id")int event_id, String jsonEventSet) {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
    
    @DELETE
    @Path("/{system_id}/event/{event_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteEvent(@PathParam("event_id")int event_id) {
        
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
    public Response getEventLogs() {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
    
    @GET
    @Path("/{system_id}/eventLog/{event_log_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEventLog(@PathParam("event_log_id")int event_log_id) {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
    
    @POST
    @Path("/{system_id}/eventLog")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createEventLog(String jsonEventLogSet) {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
    
    @PUT
    @Path("/{system_id}/eventLog/{event_log_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateEventLog(@PathParam("event_log_id")int event_log_id, String jsonEventLogSet) {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
    
    @DELETE
    @Path("/{system_id}/eventLog/{event_log_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteEventLog(@PathParam("event_log_id")int event_log_id) {
        
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
    public Response getExtTypes() {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
    
    @GET
    @Path("/{system_id}/extType/{ext_type_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getExtType(@PathParam("ext_type_id")int ext_type_id) {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
    
    @POST
    @Path("/{system_id}/extType")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createExtType(String jsonExtTypeSet) {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
    
    @PUT
    @Path("/{system_id}/extType/{ext_type_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateExtType(@PathParam("ext_type_id")int ext_type_id, String jsonExtTypeSet) {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
    
    @DELETE
    @Path("/{system_id}/extType/{ext_type_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteExtType(@PathParam("ext_type_id")int ext_type_id) {
        
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
    public Response getCurrencys() {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
    
    @GET
    @Path("/{system_id}/currency/{currency_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCurrency(@PathParam("currency_id")int currency_id) {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
    
    @POST
    @Path("/{system_id}/currency")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCurrency(String jsonCurrencySet) {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
    
    @PUT
    @Path("/{system_id}/currency/{currency_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCurrency(@PathParam("currency_id")int currency_id, String jsonCurrencySet) {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
    
    @DELETE
    @Path("/{system_id}/currency/{currency_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCurrency(@PathParam("currency_id")int currency_id) {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
}
