package wi.server.rc.api;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import wi.rc.server.opr.ProductDataOpr;
/**
 * REST Web Service
 *
 * @author hermeschang
 */
@Path("product")
@Api(value="product", description="Operations about product and details")
public class ProductResource {

//    @Context
    private UriInfo context;

    public ProductResource() {
    }

    @GET
    @Path("/company/{company_id}")
    @Produces("application/json")
    @ApiOperation(
            value = "Get products", 
            notes = "Product and details",
            response = Response.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Got results"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal server error"),
        @ApiResponse(code = 400, message = "Bad request")
    })
    public Response getProducts(
            @ApiParam(value = "company id", required = true) @PathParam("company_id")int company_id,
            @ApiParam(value = "product name", required = false) @HeaderParam("product_name") String product_name,
            @ApiParam(value = "product id", required = false) @HeaderParam("product_id") String product_id,
            @ApiParam(value = "category id", required = false) @HeaderParam("category_id") String category_id,
            @ApiParam(value = "status", required = false) @HeaderParam("status") String status) {

        return ProductDataOpr.selectAllProduct(company_id,product_id,category_id,product_name,status);
    }
    
    @POST
    @Path("/")
    @Produces("application/json")
    @ApiOperation(
            value = "Create an product and details", 
            notes = "Create an product and details",
            response = Response.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Created"),
        @ApiResponse(code = 500, message = "Internal server error"),
        @ApiResponse(code = 400, message = "Bad request")
    })
    public Response createProduct(String jsonProductSet) {
        return ProductDataOpr.insertProduct(jsonProductSet);
    }
    
    @PUT
    @Path("/{product_id}/company/{company_id}")
    @Produces("application/json")
    @ApiOperation(
            value = "update an prouct and details",
            notes = "Update an prouct and details",
            response = Response.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal server error"),
        @ApiResponse(code = 400, message = "Bad request")
    })
    public Response updateProduct(
            @ApiParam(value = "company id", required = true) @PathParam("company_id") int company_id,
            @ApiParam(value = "product id", required = true) @PathParam("product_id") int product_id, 
            String jsonProductSet) {
        return ProductDataOpr.updateProduct(company_id,product_id,jsonProductSet);
    }
    
    @DELETE
    @Path("/{product_id}/company/{company_id}")
    @Produces("application/json")
    @ApiOperation(
            value = "delete an product", 
            notes = "delete an product",
            response = Response.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal server error"),
        @ApiResponse(code = 400, message = "Bad request")
    })
    public Response deleteProduct(
            @ApiParam(value = "company id", required = true) @PathParam("company_id")int company_id,
            @ApiParam(value = "product id", required = true) @PathParam("product_id")int product_id) {
        return ProductDataOpr.deleteProduct(company_id,product_id);
    }
}
