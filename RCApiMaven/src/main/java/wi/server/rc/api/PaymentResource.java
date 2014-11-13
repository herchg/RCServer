/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wi.server.rc.api;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import wi.rc.server.opr.PaymentDataOpr;

@Path("payment")
@Api(value = "payment", description = "Operations about payment")
public class PaymentResource {

    @Context
    private UriInfo context;

    public PaymentResource() {
    }

    /**
     * Get all payment 
     */
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Get payment",
            notes = "Payment",
            response = Response.class
    )
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Got results"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal server error"),
        @ApiResponse(code = 400, message = "Bad request")
    })
    public Response getPayments() {
      
        return PaymentDataOpr.selectAllPayments();
    }
    
    private void Exception(String FORMAT_ERROR) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

