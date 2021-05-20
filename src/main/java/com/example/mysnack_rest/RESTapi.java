package com.example.mysnack_rest;

import com.example.mysnack_rest.model.Product;
import com.example.mysnack_rest.service.ProductService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/product")
public class RESTapi {
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProduct(@PathParam("id") String id){
        Product product = ProductService.getProductById(Integer.parseInt(id));
        if(product == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(product, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProduct(){
        ArrayList<Product> products = ProductService.getAllProducts();

        return Response.ok(products, MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addProduct(Product product){
        if(ProductService.addProduct(product)){
            return Response.ok().entity("Product added successfully.").build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteProduct(@PathParam("id") String id){
        if(ProductService.deleteProduct(Integer.parseInt(id))){
            return Response.ok().entity("Product with product id: " + id + " was deleted.").build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
