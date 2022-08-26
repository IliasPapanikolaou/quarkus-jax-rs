package com.unipi.ipap;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.ws.rs.core.Application;

/**
 * This class extents javax.ws.rs.core.Application and is for OpenAPI only
 */
@OpenAPIDefinition(
        info = @Info(
                title = "Fruit APIs",
                description = "Fruit Application",
                version = "1.0.0",
                license = @License(
                        name = "MIT",
                        url = "http://localhost:8080"
                )
        ),
        tags = {
                @Tag(name = "fruits", description = "Fruits")
        }
)
public class FruitApplication extends Application {
}
