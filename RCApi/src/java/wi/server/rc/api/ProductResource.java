package wi.server.rc.api;

import java.net.URLDecoder;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URLEncoder;
import wi.rc.server.opr.ProductDataOpr;
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

    /*
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProducts() {
        
        return ProductDataOpr.selectAllProduct();
    }
    */
    @GET
    @Path("/company/{company_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProducts(@PathParam("company_id")int company_id,
            @HeaderParam("product_name") String product_name,
            @HeaderParam("product_id") String product_id,
            @HeaderParam("category_id") String category_id,
            @HeaderParam("status") String status
            ) {

        return ProductDataOpr.selectAllProduct(company_id,product_id,category_id,product_name,status);
    }
    
    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createProduct(String jsonProductSet) {
        
        return ProductDataOpr.insertProduct(jsonProductSet);
    }
    
    
    @PUT
    @Path("/{product_id}/company/{company_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateProduct(@PathParam("company_id") int company_id,@PathParam("product_id") int product_id, String jsonProductSet) {
        
        return ProductDataOpr.updateProduct(company_id,product_id,jsonProductSet);
    }
    
    
    @DELETE
    @Path("/{product_id}/company/{company_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteProduct(@PathParam("company_id")int company_id,@PathParam("product_id")int product_id) {
        
        return ProductDataOpr.deleteProduct(company_id,product_id);
    }
}
