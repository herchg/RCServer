/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wi.server.rc.api;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author hermeschang
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        resources.add(com.wordnik.swagger.jaxrs.listing.ApiListingResource.class);
        resources.add(com.wordnik.swagger.jaxrs.listing.ApiDeclarationProvider.class);
        resources.add(com.wordnik.swagger.jaxrs.listing.ApiListingResourceJSON.class);
        resources.add(com.wordnik.swagger.jaxrs.listing.ResourceListingProvider.class);
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider.class);
        resources.add(com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider.class);
        resources.add(com.fasterxml.jackson.jaxrs.json.JsonMappingExceptionMapper.class);
        resources.add(com.fasterxml.jackson.jaxrs.json.JsonParseExceptionMapper.class);
        resources.add(wi.server.rc.api.CategoryResource.class);
        resources.add(wi.server.rc.api.CombinationResource.class);
        resources.add(wi.server.rc.api.CompanyResource.class);
        resources.add(wi.server.rc.api.CustomerResource.class);
        resources.add(wi.server.rc.api.EmployeeResource.class);
        resources.add(wi.server.rc.api.ImageResource.class);
        resources.add(wi.server.rc.api.OrderResource.class);
        resources.add(wi.server.rc.api.PaymentResource.class);
        resources.add(wi.server.rc.api.PosResource.class);
        resources.add(wi.server.rc.api.ProductResource.class);
        resources.add(wi.server.rc.api.RoleResource.class);
        resources.add(wi.server.rc.api.StoreResource.class);
        resources.add(wi.server.rc.api.SystemResource.class);
    }
    
}
