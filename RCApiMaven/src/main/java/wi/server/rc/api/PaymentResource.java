/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wi.server.rc.api;

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
    public Response getPayments() {
      
        return PaymentDataOpr.selectAllPayments();
    }
    
    private void Exception(String FORMAT_ERROR) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

