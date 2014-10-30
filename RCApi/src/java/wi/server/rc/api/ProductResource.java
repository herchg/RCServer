package wi.server.rc.api;

import javax.ws.rs.DELETE;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author hermeschang
 */
@Path("product")
public class ProductResource {

//    @Context
    private UriInfo context;

    public ProductResource() {
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProducts(@QueryParam("category") int category, @QueryParam("price") int price) {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
    
    @GET
    @Path("/{product_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProduct(@PathParam("product_id")int product_id, @QueryParam("category") int category, @QueryParam("price") int price) {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
    
    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createProduct(String jsonProductSet) {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
    
    @PUT
    @Path("/{product_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateProduct(@PathParam("product_id")int product_id, String jsonProductSet) {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
    
    @DELETE
    @Path("/{product_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteProduct(@PathParam("product_id")int product_id) {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
}
