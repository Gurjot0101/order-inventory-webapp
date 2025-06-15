package com.app.dto;

import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data // Lombok annotation to automatically generate getters, setters, toString(), equals(), and hashCode() methods
public class ShipmentStatusCountDTO {

    @NotNull(message = "Shipment status cannot be null.") // Validation constraint to ensure shipmentStatus is not null
    private String shipmentStatus; // Represents the status of the shipment

    @Positive(message = "Total products sold must be positive.") // Validation constraint to ensure totalProductsSold is positive
    private long totalProductsSold; // Represents the total number of products sold with the given shipment status
}

