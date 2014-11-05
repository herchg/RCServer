package wi.server.rc.api;

import javax.ws.rs.DELETE;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
    @Path("/{company_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductByCompany(@PathParam("company_id")int company_id) {
        
        return ProductDataOpr.selectProductByCompany(company_id);
    }
    
    @GET
    @Path("/{company_id}/{product_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProduct(@PathParam("company_id")int company_id,@PathParam("product_id")int product_id) {
        
        return ProductDataOpr.selectProductById(company_id,product_id);
    }
    
    @GET
    @Path("/{company_id}/category/{category_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductByCategory(@PathParam("company_id")int company_id,@PathParam("category_id")int category_id) {
        
        return ProductDataOpr.selectProductByCategory(company_id,category_id);
    }
    
    @GET
    @Path("/{company_id}/name/{product_name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductByName(@PathParam("company_id")int company_id,@PathParam("product_name")String product_name) {
        
        return ProductDataOpr.selectProductByName(company_id,product_name);
    }
    
    
    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createProduct(String jsonProductSet) {
        
        return ProductDataOpr.insertProduct(jsonProductSet);
    }
    
    /*
    @PUT
    @Path("/{product_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateProduct(@PathParam("product_id")int product_id, String jsonProductSet) {
        
        Response resp;
        resp = Response.status(Response.Status.NOT_IMPLEMENTED).build();
        return resp;
    }
    */
    
    @DELETE
    @Path("/{company_id}/{product_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteProduct(@PathParam("company_id")int company_id,@PathParam("product_id")int product_id) {
        
        return ProductDataOpr.deleteProduct(company_id,product_id);
    }
}
