/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wi.server.rc.api;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;

/**
 * REST Web Service
 *
 * @author 10307905
 */
@Path("order")
public class OrderResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of OrderResource
     */
    public OrderResource() {
    }

    /**
     * Retrieves representation of an instance of wi.server.rc.api.OrderResource
     * @return an instance of java.lang.String
     */
    @GET
    @Path("/get")
    @Produces("application/json")
    public String getXml() {
        //TODO return proper representation object
        return "order get";
    }

    /**
     * PUT method for updating or creating an instance of OrderResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/xml")
    public void putXml(String content) {
    }
}
