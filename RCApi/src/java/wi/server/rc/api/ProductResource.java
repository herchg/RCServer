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
import wi.rc.data.product.Product;

/**
 * REST Web Service
 *
 * @author hermeschang
 */
@Path("product")
public class ProductResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ProductResource
     */
    public ProductResource() {
    }

    @Path("/product/{productId}")
    @GET
    @Produces("application/json")
    public Product getProduct(int productId) {
        //TODO return proper representation object
        return new Product();
    }

    @Path("/product/{productId}")
    @PUT
    @Consumes("application/json")
    public void updateProduct(Product product) {
        
    }
}
