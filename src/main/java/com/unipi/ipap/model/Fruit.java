package com.unipi.ipap.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(name = "Fruit", description = "Fruit representation") // Open API
public record Fruit(
        @Schema(required = true) // Open API
        String name,
        String description
) {
}
