package com.unipi.ipap.controller;

import com.unipi.ipap.model.Fruit;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.stream.Collectors;

@Path("/api")
@Tag(name = "Fruit Resource", description = "Fruit REST APIs")
public class FruitResource {

    private Set<Fruit> fruits = Collections.newSetFromMap(Collections.synchronizedMap(new LinkedHashMap<>()));

    public FruitResource() {
        fruits.add(new Fruit("Apple", "Winter fruit"));
        fruits.add(new Fruit("Pineapple", "Tropical fruit"));
    }

    @GET
    @Path("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello from RESTEasy Reactive";
    }

    // GET Open API
    @Operation(
            operationId = "getFruits",
            summary = "Get fruits",
            description = "description"
    )
    @APIResponse(
            responseCode = "200",
            description = "Operation completed",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @GET
    @Path("/fruits")
    public Set<Fruit> getFruits() {
        return fruits;
    }

    // GET Open API
    @Operation(
            operationId = "getFruit",
            summary = "Get fruit by name",
            description = "description"
    )
    @APIResponse(
            responseCode = "200",
            description = "Operation completed",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @GET
    @Path("/fruits/{name}")
    // When using Jackson dependency, the MediaType is by default APPLICATION_JSON
    @Produces({MediaType.APPLICATION_JSON})
    public Response getFruitByName(
            @Parameter(
                    description = "Fruit name",
                    required = true,
                    example = "Apple"
            )
            @PathParam("name") String name) {
        return Response.ok(fruits.stream()
                .filter(fruit -> fruit.name().equals(name)).findFirst())
                .build();
    }

    // POST Open API
    @Operation(
            operationId = "createFruit",
            summary = "Create a new fruit",
            description = "Create a new fruit to add in the list of fruits"
    )
    @APIResponse(
            responseCode = "201",
            description = "Fruit created",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @POST
    @Path("/fruits")
    // When using Jackson dependency, the MediaType is by default APPLICATION_JSON
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Set<Fruit> createFruit(@RequestBody(
            description = "Movie to create",
            required = true,
            content = @Content(schema = @Schema(implementation = Fruit.class))
    ) Fruit fruit) {
        fruits.add(fruit);
        return fruits;
    }

    // PUT Open API
    @Operation(
            operationId = "updateFruit",
            summary = "Update an existing fruit",
            description = "Update an existing fruit from list of fruits"
    )
    @APIResponse(
            responseCode = "200",
            description = "Fruit updated",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @PUT
    @Path("/fruits")
    // When using Jackson dependency, the MediaType is by default APPLICATION_JSON
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Set<Fruit> updateFruit(
            @RequestBody(
                    description = "Movie to create",
                    required = true,
                    content = @Content(schema = @Schema(implementation = Fruit.class)))
            Fruit updatedFruit) {
        fruits = fruits.stream()
                .map(fruit -> fruit.name().equals(updatedFruit.name()) ? updatedFruit : fruit)
                .collect(Collectors.toSet());
        return fruits;
    }

    // DELETE Open API
    @Operation(
            operationId = "deleteFruit",
            summary = "Delete an existing Fruit",
            description = "Delete an existing fruit from list of fruits"
    )
    @APIResponse(
            responseCode = "204",
            description = "Fruit deleted",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @APIResponse(
            responseCode = "400",
            description = "Fruit not valid",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @DELETE
    @Path("/fruits")
    // When using Jackson dependency, the MediaType is by default APPLICATION_JSON
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Set<Fruit> deleteFruit(Fruit fruit) {
        fruits.removeIf(existingFruit -> existingFruit.name().contentEquals(fruit.name()));
        return fruits;
    }

}